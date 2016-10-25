package progression;


import com.google.gson.reflect.TypeToken;
import logging.ILogger;
import objects.powerups.Powerups;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Standard implementation of the ProgressionManager. Used to contain all "global" variables that describe
 * the progression of the player, eg coins, highscores, unlocked/upgraded items.
 */
public final class ProgressionManager implements IProgressionManager {

    /**
     * The maximum amount of entries in a HighScoresList.
     */
    private static final int MAX_HIGHSCORE_ENTRIES = 10;
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger.
     */
    private final ILogger logger;
    /**
     * The level that the player has unlocked of each powerup. 0 = not available yet.
     */
    private final Map<Powerups, Integer> powerupLevels = new EnumMap<>(Powerups.class);
    /**
     * A list of high scores for the game.
     */
    private final List<HighScore> highScores = new ArrayList<>(MAX_HIGHSCORE_ENTRIES + 1);
    /**
     * Contains the current missions of the player. Note that this is a list instead of a set, because we don't
     * want the missios to be drawn in another order every time the screen refreshes.
     */
    private final List<Mission> missions = new ArrayList<>();
    /**
     * Used to prevent an {@link java.util.ConcurrentModificationException ConcurrentModificationException}
     * when deleting a mission while iterating over the missions.
     */
    private final Queue<Mission> finishedMissionsQueue = new LinkedList<>();
    /**
     * Used to prevent an {@link java.util.ConcurrentModificationException ConcurrentModificationException}
     * when deleting a mission while iterating over the missions.
     */
    private final Queue<FinishedProgressionObserverTuple> finishedProgressionObserversQueue = new LinkedList<>();
    /**
     * Contains the data used to create new missions.
     */
    private final MissionData[] missionsData = new MissionData[]{
            new MissionData(MissionType.jumpOnSpring, ProgressionObservers.spring, 1, 10),
            new MissionData(MissionType.jumpOnSpring, ProgressionObservers.spring, 2, 20),
            new MissionData(MissionType.jumpOnSpring, ProgressionObservers.spring, 3, 30),
            new MissionData(MissionType.jumpOnSpring, ProgressionObservers.spring, 4, 40)
    };
    /**
     * The amount of coins the player has.
     */
    private int coins;
    /**
     * Incremented by 1 after every mission; used to determine which mission should be created.
     */
    private int level = 0;

    /**
     * Prevents construction from outside the package.
     */
    private ProgressionManager() {
        logger = serviceLocator.getLoggerFactory().createLogger(ProgressionManager.class);
    }

    /**
     * Register the ProgressionManager into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        ProgressionManager.serviceLocator = sL;
        sL.provide(new ProgressionManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        if (powerupLevels.size() == 0) {
            loadData();
        }
    }

    /**
     * Add a score to the list of highscores.
     *
     * @param name  The name for the score.
     * @param score The actual score.
     */
    @Override
    public void addHighScore(final String name, final double score) {
        HighScore scoreEntry = new HighScore(name, score);
        highScores.add(scoreEntry);
        updateHighScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HighScore> getHighscores() {
        return highScores;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return coins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mission> getMissions() {
        return missions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertMissionFinished(Mission mission) {
        if (!missions.contains(mission)) {
            final String error = "The mission that's said to be finished is not an active mission";
            logger.warning(error);
            throw new InternalError(error);
        }
        logger.info("Mission succeeded!");
        finishedMissionsQueue.add(mission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        while (finishedMissionsQueue.size() > 0) {
            missions.remove(finishedMissionsQueue.remove());
            createNewMission();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPowerupLevel(final Powerups powerup) {
        if (powerup == null) {
            final String error = "The powerup cannot be null";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        return powerupLevels.get(powerup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseCoins(final int amount) {
        assert coins >= 0;

        if (amount < 0) {
            final String error = "The amount of coins to be subtracted must be more than 0";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        if (coins - amount < 0) {
            final String error = "Insufficient coins available: coins = " + coins + ", subtraction amount = " + amount;
            logger.error(error);
            throw new InsufficientCoinsException(error);
        }
        this.coins -= amount;

        saveData();

        assert coins >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increasePowerupLevel(final Powerups powerup) {
        if (powerup == null) {
            throw new IllegalArgumentException("The level of the null powerup cannot be increased");
        }
        assert this.powerupLevels.containsKey(powerup);

        final int currentLevel = this.powerupLevels.get(powerup);

        assert currentLevel + 1 < powerup.getMaxLevel();

        this.powerupLevels.replace(powerup, currentLevel + 1);

        saveData();
    }

    /**
     * Loads the progression of the player from the disk.
     */
    private void loadData() {
        Object jsonObject = null;
        try {
            jsonObject = serviceLocator.getFileSystem().parseJson(serviceLocator.getConstants().getSaveFilePath(), new TypeToken<SaveFile>() {
            }.getType());
        } catch (FileNotFoundException e) {
            logger.warning("Save file was not found -> default progression used.");
        }
        if (jsonObject == null) {
            progressionFromDefault();
        } else {
            progressionFromJson((SaveFile) jsonObject);
        }
    }

    /**
     * Saved the progression of the player to the disk.
     */
    private void saveData() {
        SaveFile image = new SaveFile();

        image.setCoins(this.coins);

        List<SaveFileHighScoreEntry> highScoreEntries = new ArrayList<>(MAX_HIGHSCORE_ENTRIES);
        for (HighScore highScore : highScores) {
            SaveFileHighScoreEntry entry = new SaveFileHighScoreEntry();
            entry.setName(highScore.getName());
            entry.setScore(highScore.getScore());
            highScoreEntries.add(entry);
        }
        image.setHighScores(highScoreEntries);

        Map<String, Integer> powerupLevelEntries = new HashMap<>();
        for (Map.Entry<Powerups, Integer> entry : powerupLevels.entrySet()) {
            powerupLevelEntries.put(entry.getKey().name(), entry.getValue());
        }
        image.setPowerupLevels(powerupLevelEntries);

        String json = serviceLocator.getFileSystem().serializeJson(image);
        try {
            serviceLocator.getFileSystem().writeProjectFile(serviceLocator.getConstants().getSaveFilePath(), json);
        } catch (FileNotFoundException e) {
            logger.info("Save file was not found -> a new file is made.");
        }
    }

    /**
     * Sets the progression to the default values: the values used when the game is started for the first time.
     */
    private void progressionFromDefault() {
        powerupLevels.clear();
        for (Powerups powerup : Powerups.values()) {
            powerupLevels.put(powerup, 0);
        }
        powerupLevels.replace(Powerups.spring, 1);

        coins = 0;

        highScores.clear();

        for (int i = 0; i < 3; i++) {
            createNewMission();
        }

        saveData();
    }

    /**
     * Sets the progression in the game from a json string.
     *
     * @param json The json containing the progression
     */
    private void progressionFromJson(SaveFile json) {

        for (int i = 0; i < 3; i++) {
            createNewMission();
        }

        highScores.clear();
        for (SaveFileHighScoreEntry entry : json.getHighScores()) {
            highScores.add(new HighScore(entry.getName(), entry.getScore()));
            logger.info("A highscore is added: " + entry.getName() + " - " + entry.getScore());
        }

        coins = json.getCoins();
        logger.info("Coins is set to: " + coins);

        powerupLevels.clear();
        for (Map.Entry<String, Integer> entry : json.getPowerupLevels().entrySet()) {
            switch (entry.getKey()) {
                case "jetpack":
                    powerupLevels.put(Powerups.jetpack, entry.getValue());
                    logger.info("Jetpack level is loaded from save file: " + entry.getValue());
                    break;
                case "propeller":
                    powerupLevels.put(Powerups.propeller, entry.getValue());
                    logger.info("Propeller level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeDown":
                    powerupLevels.put(Powerups.sizeDown, entry.getValue());
                    logger.info("SizeDown level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeUp":
                    powerupLevels.put(Powerups.sizeUp, entry.getValue());
                    logger.info("SizeUp level is loaded from save file: " + entry.getValue());
                    break;
                case "spring":
                    powerupLevels.put(Powerups.spring, entry.getValue());
                    logger.info("Spring level is loaded from save file: " + entry.getValue());
                    break;
                case "springShoes":
                    powerupLevels.put(Powerups.springShoes, entry.getValue());
                    logger.info("SpringShoes level is loaded from save file: " + entry.getValue());
                    break;
                case "trampoline":
                    powerupLevels.put(Powerups.trampoline, entry.getValue());
                    logger.info("Trampoline level is loaded from save file: " + entry.getValue());
                    break;
                default:
                    logger.warning("Unidentified powerup classifier found in savefile: \"" + entry.getKey() + "\"");
                    break;
            }
        }
        for (Powerups powerup : Powerups.values()) {
            if (!powerupLevels.containsKey(powerup)) {
                powerupLevels.put(powerup, 0);
            }
        }
        updateHighScores();
    }

    /**
     * Update the high scores for the game. Makes sure the
     * max amount of high scores is not exceeded and the high
     * scores are saved.
     */
    private void updateHighScores() {
        Collections.sort(highScores);
        for (int i = highScores.size(); i > MAX_HIGHSCORE_ENTRIES; i--) {
            highScores.remove(i - 1);
        }
        saveData();
    }

    /**
     * Create a new missio based on the {@link #level} of the doodle.
     */
    private void createNewMission() {
        assert missions.size() < 3;
        if (level < missionsData.length) {
            final int levelCopy = level;
            logger.info("New mission was created: level = " +
                    levelCopy +
                    ", mission = " +
                    missionsData[levelCopy].type.toString() +
                    ", amount = " +
                    missionsData[levelCopy].amount +
                    ", reward = " +
                    missionsData[levelCopy].reward);
            missions.add(serviceLocator.getMissionFactory().createMission(
                    missionsData[levelCopy].type,
                    missionsData[levelCopy].observerType,
                    missionsData[levelCopy].amount,
                    () -> {
                        coins += missionsData[levelCopy].reward;
                        return null;
                    }
            ));
        } else {
            logger.info("Maximum mission limit reached at level" + level + ". Last mission created again...");
            missions.add(serviceLocator.getMissionFactory().createMission(
                    missionsData[missionsData.length - 1].type,
                    missionsData[missionsData.length - 1].observerType,
                    missionsData[missionsData.length - 1].amount,
                    () -> {
                        coins += missionsData[missionsData.length - 1].reward;
                        return null;
                    }
            ));
        }

        level++;
    }

    private final class MissionData {
        private final MissionType type;
        private final ProgressionObservers observerType;
        private final int amount;
        private final int reward;

        private MissionData(final MissionType type, final ProgressionObservers observerType, final int amount, final int reward) {
            this.type = type;
            this.observerType = observerType;
            this.amount = amount;
            this.reward = reward;
        }
    }

    private final class FinishedProgressionObserverTuple {
        private final ProgressionObservers type;
        private final IProgressionObserver observer;

        private FinishedProgressionObserverTuple(final ProgressionObservers type, final IProgressionObserver observer) {
            this.type = type;
            this.observer = observer;
        }
    }

    private final class InsufficientCoinsException extends RuntimeException {
        private InsufficientCoinsException(final String message) {
            super(message);
        }
    }
}
