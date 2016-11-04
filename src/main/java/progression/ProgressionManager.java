package progression;

import com.google.gson.reflect.TypeToken;
import filesystem.IFileSystem;
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
 * the progression of the player, eg coins, high scores, unlocked/upgraded items.
 */
public final class ProgressionManager implements IProgressionManager {
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger.
     */
    private final ILogger logger;
    /**
     * The class responsible for managing the coins.
     */
    private final CoinManager coinManager;
    /**
     * The class responsible for managing the ranks.
     */
    private final RankManager rankManager;
    /**
     * The class responsible for managing the experience.
     */
    private final ExperienceManager experienceManager;
    /**
     * The class responsible for managing the levels.
     */
    private final LevelManager levelManager;
    /**
     * The class responsible for managing the highscores.
     */
    private final HighScoreManager highScoreManager;
    /**
     * The class responsible for managing the missions.
     */
    private final MissionManager missionManager;
    /**
     * The class responsible for managing the powerup levels.
     */
    private final PowerupLevelManager powerupLevelManager;

    /**
     * Prevents construction from outside the package.
     */
    private ProgressionManager() {
        this.logger = serviceLocator.getLoggerFactory().createLogger(ProgressionManager.class);
        this.coinManager = new CoinManager(this);
        this.rankManager = new RankManager(this);
        this.experienceManager = new ExperienceManager(this);
        this.levelManager = new LevelManager();
        this.highScoreManager = new HighScoreManager(this);
        this.missionManager = new MissionManager(this);
        this.powerupLevelManager = new PowerupLevelManager(this);
    }

    /**
     * Register the ProgressionManager into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        ProgressionManager.serviceLocator = sL;
        ProgressionManager.serviceLocator.provide(new ProgressionManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        if (this.powerupLevelManager.powerupLevels.isEmpty()) {
            this.loadData();
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
        this.highScoreManager.highScores.add(scoreEntry);
        this.highScoreManager.updateHighScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HighScore> getHighscores() {
        return this.highScoreManager.highScores;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return this.coinManager.coins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ranks getRank() {
        return this.rankManager.rank;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExperience() {
        return this.experienceManager.experience;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mission> getMissions() {
        return this.missionManager.missions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertMissionFinished(final Mission mission) {
        if (!this.missionManager.missions.contains(mission)) {
            final String error = "The mission that's said to be finished is not an active mission";
            this.logger.warning(error);
            throw new InternalError(error);
        }
        this.logger.info("Mission succeeded!");
        this.missionManager.finishedMissionsQueue.add(mission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        while (this.missionManager.finishedMissionsQueue.size() > 0) {
            this.missionManager.missions.remove(this.missionManager.finishedMissionsQueue.remove());
            this.missionManager.createNewMission();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPowerupLevel(final Powerups powerup) {
        return this.powerupLevelManager.getPowerupLevel(powerup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseCoins(final int amount) {
        this.coinManager.decreaseCoins(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExperience(final int amount) {
        this.experienceManager.addExperience(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increasePowerupLevel(final Powerups powerup) {
        this.powerupLevelManager.increasePowerupLevel(powerup);
    }

    /**
     * Loads the progression of the player from the disk.
     */
    private void loadData() {
        Object jsonObject = null;
        try {
            jsonObject = ProgressionManager.serviceLocator.getFileSystem().parseJson(
                    ProgressionManager.serviceLocator.getConstants().getSaveFilePath(), new TypeToken<SaveFile>() {
            }.getType());
        } catch (FileNotFoundException e) {
            this.logger.warning("Save file was not found -> default progression used.");
        }

        if (jsonObject == null) {
            this.progressionFromDefault();
        } else {
            this.progressionFromJson((SaveFile) jsonObject);
        }
    }

    /**
     * Saved the progression of the player to the disk.
     */
    private void saveData() {
        SaveFile image = new SaveFile();

        image.setCoins(this.coinManager.coins);
        image.setExperience(this.experienceManager.experience);

        List<SaveFileHighScoreEntry> highScoreEntries = new ArrayList<>(this.highScoreManager.MAX_HIGHSCORE_ENTRIES);
        for (HighScore highScore : this.highScoreManager.highScores) {
            SaveFileHighScoreEntry entry = new SaveFileHighScoreEntry();
            entry.setName(highScore.getName());
            entry.setScore(highScore.getScore());
            highScoreEntries.add(entry);
        }
        image.setHighScores(highScoreEntries);

        Map<String, Integer> powerupLevelEntries = new HashMap<>();
        for (Map.Entry<Powerups, Integer> entry : this.powerupLevelManager.powerupLevels.entrySet()) {
            powerupLevelEntries.put(entry.getKey().name(), entry.getValue());
        }
        image.setPowerupLevels(powerupLevelEntries);

        IFileSystem fileSystem = ProgressionManager.serviceLocator.getFileSystem();
        String json = fileSystem.serializeJson(image);
        try {
            fileSystem.writeProjectFile(ProgressionManager.serviceLocator.getConstants().getSaveFilePath(), json);
        } catch (FileNotFoundException e) {
            this.logger.info("Save file was not found -> a new file is made.");
        }
    }

    /**
     * Sets the progression to the default values: the values used when the game is started for the first time.
     */
    private void progressionFromDefault() {
        this.powerupLevelManager.powerupLevels.clear();
        for (Powerups powerup : Powerups.values()) {
            this.powerupLevelManager.powerupLevels.put(powerup, 0);
        }
        this.powerupLevelManager.powerupLevels.replace(Powerups.spring, 1);

        this.coinManager.coins = 0;
        this.experienceManager.experience = 0;
        this.rankManager.rank = Ranks.newbie;

        this.highScoreManager.highScores.clear();

        for (int i = 0; i < this.missionManager.MAX_MISSIONS; i++) {
            this.missionManager.createNewMission();
        }

        this.saveData();
    }

    /**
     * Sets the progression in the game from a json string.
     *
     * @param json The json containing the progression
     */
    private void progressionFromJson(final SaveFile json) {
        for (int i = 0; i < this.missionManager.MAX_MISSIONS; i++) {
            this.missionManager.createNewMission();
        }

        this.highScoreManager.highScores.clear();
        for (SaveFileHighScoreEntry entry : json.getHighScores()) {
            this.highScoreManager.highScores.add(new HighScore(entry.getName(), entry.getScore()));
            this.logger.info("A highscore is added: " + entry.getName() + " - " + entry.getScore());
        }

        this.coinManager.coins = json.getCoins();
        this.logger.info("Coins is set to: " + this.coinManager.coins);

        this.experienceManager.experience = json.getExperience();
        this.logger.info("Experience is set to: " + this.experienceManager.experience);
        this.rankManager.setRankAccordingExperience();

        this.powerupLevelManager.powerupLevels.clear();
        for (Map.Entry<String, Integer> entry : json.getPowerupLevels().entrySet()) {
            switch (entry.getKey()) {
                case "jetpack":
                    this.powerupLevelManager.powerupLevels.put(Powerups.jetpack, entry.getValue());
                    this.logger.info("Jetpack level is loaded from save file: " + entry.getValue());
                    break;
                case "propeller":
                    this.powerupLevelManager.powerupLevels.put(Powerups.propeller, entry.getValue());
                    this.logger.info("Propeller level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeDown":
                    this.powerupLevelManager.powerupLevels.put(Powerups.sizeDown, entry.getValue());
                    this.logger.info("SizeDown level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeUp":
                    this.powerupLevelManager.powerupLevels.put(Powerups.sizeUp, entry.getValue());
                    this.logger.info("SizeUp level is loaded from save file: " + entry.getValue());
                    break;
                case "spring":
                    this.powerupLevelManager.powerupLevels.put(Powerups.spring, entry.getValue());
                    this.logger.info("Spring level is loaded from save file: " + entry.getValue());
                    break;
                case "springShoes":
                    this.powerupLevelManager.powerupLevels.put(Powerups.springShoes, entry.getValue());
                    this.logger.info("SpringShoes level is loaded from save file: " + entry.getValue());
                    break;
                case "trampoline":
                    this.powerupLevelManager.powerupLevels.put(Powerups.trampoline, entry.getValue());
                    this.logger.info("Trampoline level is loaded from save file: " + entry.getValue());
                    break;
                default:
                    this.logger.warning("Unidentified powerup classifier found in save file: \""
                            + entry.getKey() + "\"");
                    break;
            }
        }

        for (Powerups powerup : Powerups.values()) {
            if (!this.powerupLevelManager.powerupLevels.containsKey(powerup)) {
                this.powerupLevelManager.powerupLevels.put(powerup, 0);
            }
        }

        this.highScoreManager.updateHighScores();
    }

    /**
     * A data container class for missions.
     */
    private static final class MissionData {

        /**
         * The type of the mission.
         */
        private final MissionType type;
        /**
         * The type of the observer of the mission.
         */
        private final ProgressionObservers observerType;
        /**
         * The amount of times the observer must be notified before the mission is considered finished.
         */
        private final int amount;
        /**
         * The reward in coins the player gets after finishing the mission.
         */
        private final int reward;

        /**
         * Constructs a new MissionData object.
         *
         * @param type         The type of the mission
         * @param observerType The type of the observer of the mission
         * @param amount       The amount of times the observer must be notified before the mission is considered finished
         * @param reward       The reward in coins the player gets after finishing the mission
         */
        private MissionData(final MissionType type, final ProgressionObservers observerType, final int amount, final int reward) {
            this.type = type;
            this.observerType = observerType;
            this.amount = amount;
            this.reward = reward;
        }

    }
    
    private final class CoinManager {
        /**
         * The amount of coins the player has.
         */
        private int coins;

        /**
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the coins.
         * @param progressionManager The ProgressionManager used in the game
         */
        private CoinManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }

        private void decreaseCoins(final int amount) {
            assert this.coins >= 0;

            if (amount < 0) {
                final String error = "The amount of coins to be subtracted must be more than 0";
                outer.logger.error(error);
                throw new IllegalArgumentException(error);
            }
            if (this.coins - amount < 0) {
                final String error = "Insufficient coins available: coins = " + this.coins
                        + ", subtraction amount = " + amount;
                outer.logger.error(error);
                throw new InsufficientCoinsException(error);
            }
            this.coins -= amount;

            outer.saveData();

            assert this.coins >= 0;
        }

        /**
         * Thrown when there are more coins requested to be subtracted from the budget than there are available.
         */
        private final class InsufficientCoinsException extends RuntimeException {

            /**
             * Construct a new InsufficientCoinsException with a certain message.
             *
             * @param message The message describing the exception
             */
            private InsufficientCoinsException(final String message) {
                super(message);
            }

        }
    }

    private final class RankManager {
        /**
         * The current rank of the player.
         */
        private Ranks rank;

        /**
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the rank.
         * @param progressionManager The ProgressionManager used in the game
         */
        private RankManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }

        /**
         * Will set the rank variable according to the experience the player has.
         */
        private void setRankAccordingExperience() {
            this.rank = Ranks.newbie;
            for (Ranks checkRank : Ranks.values()) {
                if (checkRank.getExperience() < this.outer.experienceManager.experience) {
                    this.rank = checkRank;
                }
            }
        }
    }
    
    private final class ExperienceManager {
        /**
         * The amount of experience the player has.
         */
        private int experience;

        /**
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the experience.
         * @param progressionManager The ProgressionManager used in the game
         */
        private ExperienceManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }

        /**
         * Adds {@code} experience to the experience of the player.
         * @param amount The amount of experience that should be added
         */
        private void addExperience(final int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Error: amount is negative.");
            }

            this.experience += amount;
            ProgressionManager.this.rankManager.setRankAccordingExperience();
            outer.saveData();
        }
    }
    
    private final class LevelManager {
        /**
         * Incremented by 1 after every mission; used to determine which mission should be created.
         */
        private int level = 0;
    }
    
    private final class HighScoreManager {
        /**
         * The maximum amount of entries in a HighScoresList.
         */
        private final int MAX_HIGHSCORE_ENTRIES = 10;

        /**
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the highscores.
         * @param progressionManager The ProgressionManager used in the game
         */
        private HighScoreManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }
        
        /**
         * A list of high scores for the game.
         */
        private final List<HighScore> highScores = new ArrayList<>(MAX_HIGHSCORE_ENTRIES + 1);

        /**
         * Update the high scores for the game. Makes sure the
         * max amount of high scores is not exceeded and the high
         * scores are saved.
         */
        private void updateHighScores() {
            Collections.sort(this.highScores);
            for (int i = this.highScores.size(); i > this.MAX_HIGHSCORE_ENTRIES; i--) {
                this.highScores.remove(i - 1);
            }

            outer.saveData();
        }
    }
    
    private final class MissionManager {
        /**
         * The maximum amount of missions active at the same time.
         */
        private final int MAX_MISSIONS = 3;
        /**
         * Contains the current missions of the player. Note that this is a list instead of a set, because we don't
         * want the missions to be drawn in another order every time the screen refreshes.
         */
        private final List<Mission> missions = new ArrayList<>();
        /**
         * Used to prevent an {@link java.util.ConcurrentModificationException ConcurrentModificationException}
         * when deleting a mission while iterating over the missions.
         */
        private final Queue<Mission> finishedMissionsQueue = new LinkedList<>();
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
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the missions.
         * @param progressionManager The ProgressionManager used in the game
         */
        private MissionManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }

        /**
         * Create a new mission based on the level of the doodle.
         */
        private void createNewMission() {
            assert this.missions.size() < this.MAX_MISSIONS;
            if (outer.levelManager.level < this.missionsData.length) {
                final int levelCopy = outer.levelManager.level;
                outer.logger.info("New mission was created: level = "
                        + levelCopy
                        + ", mission = "
                        + this.missionsData[levelCopy].type.toString()
                        + ", amount = "
                        + this.missionsData[levelCopy].amount
                        + ", reward = "
                        + this.missionsData[levelCopy].reward);
                outer.missionManager.missions.add(serviceLocator.getMissionFactory().createMission(
                        this.missionsData[levelCopy].type,
                        this.missionsData[levelCopy].observerType,
                        this.missionsData[levelCopy].amount,
                        () -> {
                            outer.coinManager.coins += this.missionsData[levelCopy].reward;
                            return null;
                        }
                ));
            } else {
                outer.logger.info("Maximum mission limit reached at level" + outer.levelManager.level + ". Last mission created again...");
                this.missions.add(serviceLocator.getMissionFactory().createMission(
                        this.missionsData[this.missionsData.length - 1].type,
                        this.missionsData[this.missionsData.length - 1].observerType,
                        this.missionsData[this.missionsData.length - 1].amount,
                        () -> {
                            outer.coinManager.coins += this.missionsData[this.missionsData.length - 1].reward;
                            return null;
                        }
                ));
            }
            outer.levelManager.level++;
        }
    }
    
    private final class PowerupLevelManager {
        /**
         * The level that the player has unlocked of each powerup. 0 = not available yet.
         */
        private final Map<Powerups, Integer> powerupLevels = new EnumMap<>(Powerups.class);

        /**
         * A refererence to the outer class
         */
        private ProgressionManager outer;

        /**
         * Construct a class responsible for managing the powerup levels.
         * @param progressionManager The ProgressionManager used in the game
         */
        private PowerupLevelManager(ProgressionManager progressionManager) {
            outer = progressionManager;
        }

        private int getPowerupLevel(final Powerups powerup) {
            if (powerup == null) {
                final String error = "The powerup cannot be null";
                outer.logger.error(error);
                throw new IllegalArgumentException(error);
            }

            if (this.powerupLevels.get(powerup) == null) {
                outer.logger.warning("The powerupLevels for the powerup " + powerup.toString() + " are missing");
                return 0;
            } else {
                return this.powerupLevels.get(powerup);
            }
        }

        private void increasePowerupLevel(final Powerups powerup) {
            if (powerup == null) {
                throw new IllegalArgumentException("The level of the null powerup cannot be increased");
            }
            assert this.powerupLevels.containsKey(powerup);

            final int currentLevel = this.powerupLevels.get(powerup);

            assert currentLevel + 1 < powerup.getMaxLevel();

            this.powerupLevels.replace(powerup, currentLevel + 1);

            outer.saveData();
        }
    }

}
