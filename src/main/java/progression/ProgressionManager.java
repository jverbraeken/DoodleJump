package progression;


import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import logging.ILogger;
import objects.powerups.Powerups;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

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
    public final void addHighScore(final String name, final double score) {
        HighScore scoreEntry = new HighScore(name, score);
        highScores.add(scoreEntry);
        updateHighScores();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<HighScore> getHighscores() {
        return highScores;
    }

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

    private void saveData() {
        SaveFile image = new SaveFile();

        image.setCoins(this.coins);

        List<SaveFileHighScoreEntry> highScoreEntries = new ArrayList<>(MAX_HIGHSCORE_ENTRIES);
        for (HighScore highScore : highScores) {
            SaveFileHighScoreEntry entry = new SaveFileHighScoreEntry();
            Map<String, String> map = new HashMap<>();
            map.put("name", highScore.getName());
            map.put("score", Integer.toString(highScore.getScore()));
            entry.setHighscores(map);
            highScoreEntries.add(entry);
        }
        image.setHighScores(highScoreEntries);

        Map<String, Integer> powerupLevelEntries = new HashMap<>();
        for (Map.Entry<Powerups, Integer> entry : powerupLevels.entrySet()) {
            powerupLevelEntries.put(entry.getKey().name(), entry.getValue());
        }
        image.setPowerupLevels(powerupLevelEntries);

        String json = null;
        try {
            json = LoganSquare.serialize(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String json = serviceLocator.getFileSystem().serializeJson(image);
        try {
            serviceLocator.getFileSystem().writeProjectFile(serviceLocator.getConstants().getSaveFilePath(), json);
        } catch (FileNotFoundException e) {
            logger.info("Save file was not found -> a new file is made.");
        }
    }

    private void progressionFromDefault() {
        powerupLevels.clear();
        for (Powerups powerup : Powerups.values()) {
            powerupLevels.put(powerup, 0);
        }
        powerupLevels.put(Powerups.SPRING, 1);

        coins = 0;

        highScores.clear();
        saveData();
    }

    private void progressionFromJson(SaveFile json) {
        highScores.clear();
        for (SaveFileHighScoreEntry entry : json.getHighScores()) {
            highScores.add(new HighScore(entry.getHighscores().get("name"), entry.getHighscores().get("score")));
        }

        coins = json.getCoins();

        powerupLevels.clear();
        for (Map.Entry<String, Integer> entry : json.getPowerupLevels().entrySet()) {
            switch (entry.getKey()) {
                case "jetpack":
                    powerupLevels.put(Powerups.JETPACK, entry.getValue());
                    logger.info("Jetpack level is loaded from save file: " + entry.getValue());
                    break;
                case "propeller":
                    powerupLevels.put(Powerups.PROPELLER, entry.getValue());
                    logger.info("Propeller level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeDown":
                    powerupLevels.put(Powerups.SIZEDOWN, entry.getValue());
                    logger.info("SizeDown level is loaded from save file: " + entry.getValue());
                    break;
                case "sizeUp":
                    powerupLevels.put(Powerups.SIZEUP, entry.getValue());
                    logger.info("SizeUp level is loaded from save file: " + entry.getValue());
                    break;
                case "spring":
                    powerupLevels.put(Powerups.SPRING, entry.getValue());
                    logger.info("Spring level is loaded from save file: " + entry.getValue());
                    break;
                case "springShoes":
                    powerupLevels.put(Powerups.SPRINGSHOES, entry.getValue());
                    logger.info("SpringShoes level is loaded from save file: " + entry.getValue());
                    break;
                case "trampoline":
                    powerupLevels.put(Powerups.TRAMPOLINE, entry.getValue());
                    logger.info("Trampoline level is loaded from save file: " + entry.getValue());
                    break;
            }
        }
    }

    /**
     * Parse a high scores string as saved in the high scores file.
     *
     * @param plain The plaintext string.
     */
    private void parseHighScoreString(final String plain) {
        String[] scores = plain.split("\\s+");
        for (int i = 0; i < scores.length; i += 2) {
            HighScore score = new HighScore(scores[i], scores[i + 1]);
            highScores.add(score);
        }
    }

    /**
     * Save the high scores to the high scores file.
     */
    private void saveHighScores() {
        // Convert high scores to string
        /*StringBuilder buf = new StringBuilder();
        for (HighScore score : highScores) {
            buf.append(score.getName());
            buf.append(" ");
            buf.append(score.getScore());
            buf.append(" ");
        }
        String data = buf.toString();

        // Save high scores.
        IFileSystem fileSystem = serviceLocator.getFileSystem();
        IConstants constants = serviceLocator.getConstants();
        try {
            fileSystem.writeProjectFile(constants.getProgressionFilePath(), data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e);
        }*/
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

        saveHighScores();
    }
}