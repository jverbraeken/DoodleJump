package progression;


import logging.ILogger;
import objects.powerups.Powerups;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

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
     * The amount of coins the player has.
     */
    private int coins;
    /**
     * Contains the current missions of the player. Note that this is a list instead of a set, because we don't
     * want the missios to be drawn in another order every time the screen refreshes.
     */
    private final List<Mission> missions = new ArrayList<>();

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
        loadData();
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
    public void addObserver(final ProgressionObservers type, final ISpringUsedObserver observer) {
        type.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertObservers(final ProgressionObservers type) {
        type.alertObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertObservers(final ProgressionObservers type, final double amount) {
        type.alertObservers(amount);
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
        missions.remove(mission);
        createNewMission();
    }

    /**
     * Loads the progression of the player from the disk.
     */
    private void loadData() {
        Object jsonObject = null;
        try {
            jsonObject = serviceLocator.getFileSystem().parseJson(serviceLocator.getConstants().getSaveFilePath(), SaveFile.class);
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
        powerupLevels.put(Powerups.SPRING, 1);

        coins = 0;

        highScores.clear();

        for (int i = 0; i < 3; i++) {
            createNewMission();
        }

        saveData();
    }

    /**
     * Sets the progression in the game from a json string.
     * @param json The json containing the progression
     */
    private void progressionFromJson(SaveFile json) {

        missions.add(0, serviceLocator.getMissionFactory().createMissionJumpOnSpring(1, () -> {
            logger.info("Mission succeeded!");
            return null;
        }));

        missions.add(1, serviceLocator.getMissionFactory().createMissionJumpOnSpring(1, () -> {
            logger.info("Mission succeeded!");
            return null;
        }));

        missions.add(2, serviceLocator.getMissionFactory().createMissionJumpOnSpring(1, () -> {
            logger.info("Mission succeeded!");
            return null;
        }));
        highScores.clear();
        for (SaveFileHighScoreEntry entry : json.getHighScores()) {
            highScores.add(new HighScore(entry.getName(), entry.getScore()));
            logger.info("A highscore is added: " + entry.getName() + " - " + entry.getScore());
        }
        updateHighScores();

        coins = json.getCoins();
        logger.info("Coins is set to: " + coins);

        powerupLevels.clear();
        for (Map.Entry<String, Integer> entry : json.getPowerupLevels().entrySet()) {
            switch (entry.getKey()) {
                case "JETPACK":
                    powerupLevels.put(Powerups.JETPACK, entry.getValue());
                    logger.info("Jetpack level is loaded from save file: " + entry.getValue());
                    break;
                case "PROPELLER":
                    powerupLevels.put(Powerups.PROPELLER, entry.getValue());
                    logger.info("Propeller level is loaded from save file: " + entry.getValue());
                    break;
                case "SIZEDOWN":
                    powerupLevels.put(Powerups.SIZEDOWN, entry.getValue());
                    logger.info("SizeDown level is loaded from save file: " + entry.getValue());
                    break;
                case "SIZEUP":
                    powerupLevels.put(Powerups.SIZEUP, entry.getValue());
                    logger.info("SizeUp level is loaded from save file: " + entry.getValue());
                    break;
                case "SPRING":
                    powerupLevels.put(Powerups.SPRING, entry.getValue());
                    logger.info("Spring level is loaded from save file: " + entry.getValue());
                    break;
                case "SPRINGSHOES":
                    powerupLevels.put(Powerups.SPRINGSHOES, entry.getValue());
                    logger.info("SpringShoes level is loaded from save file: " + entry.getValue());
                    break;
                case "TRAMPOLINE":
                    powerupLevels.put(Powerups.TRAMPOLINE, entry.getValue());
                    logger.info("Trampoline level is loaded from save file: " + entry.getValue());
                    break;
                default:
                    logger.warning("Unidentified powerup classifier found in savefile: \"" + entry.getKey() + "\"");
                    break;
            }
        }
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

    private void createNewMission() {
        missions.add(serviceLocator.getMissionFactory().createMissionJumpOnSpring(1, () -> {
            logger.info("Mission succeeded!");
            return null;
        }));
    }
}
