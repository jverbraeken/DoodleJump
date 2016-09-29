package system;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILogger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class for the high scores of the game.
 */
public final class HighScoreList {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * The logger for the HighScoreList class.
     */
    private static ILogger logger;
    /**
     * A list of high scores for the game.
     */
    private static ArrayList<HighScore> highScores = new ArrayList<>();

    /**
     * Package protected constructor allowing Game to make an instance.
     *
     * @param sL The serviceLocator.
     */
    /* package */ HighScoreList(final IServiceLocator sL) {
        serviceLocator = sL;
        logger = serviceLocator.getLoggerFactory().createLogger(HighScoreList.class);
    }

    /**
     * Add a score to the list of highscores.
     * @param name  The name for the score.
     * @param score The actual score.
     */
    public void addHighScore(final String name, final double score) {
        HighScore scoreEntry = new HighScore(name, score);
        highScores.add(scoreEntry);
        updateHighScores();
    }

    /**
     * Initialize the high scores when the program is launched.
     */
    /* package */ void initHighScores() {
        String plain = this.loadFromFile();
        if (plain != null) {
            this.parseHighScoreString(plain);
        }
    }

    /**
     * Get the high scores from the high scores file.
     */
    private String loadFromFile() {
        IFileSystem fileSystem = serviceLocator.getFileSystem();
        IConstants constants = serviceLocator.getConstants();
        try {
            List<String> content = fileSystem.readTextFile(constants.getHighScoresFilePath());

            if (content.size() > 0) {
                return content.get(0);
            }
        } catch (FileNotFoundException e) {
            logger.warning("High scores file not found, starting with empty high scores list");
        }

        return null;
    }

    /**
     * Parse a high scores string as saved in the high scores file.
     *
     * @param plain The plaintext string.
     */
    private void parseHighScoreString(final String plain) {
        String[] highScores = plain.split("\\s+");
        for (int i = 0; i < highScores.length; i += 2) {
            HighScore score = new HighScore(highScores[i], highScores[i + 1]);
            HighScoreList.highScores.add(score);
        }
    }

    /**
     * Save the high scores to the high scores file.
     */
    private void saveHighScores() {
        // Convert high scores to string
        String data = "";
        for (HighScore score : highScores) {
            data += score.getName() + " " + score.getScore() + " ";
        }

        // Save high scores.
        IFileSystem fileSystem = serviceLocator.getFileSystem();
        IConstants constants = serviceLocator.getConstants();
        try {
            fileSystem.writeTextFile(constants.getHighScoresFilePath(), data);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }

    /**
     * Update the high scores for the game. Makes sure the
     * max amount of high scores is not exceeded and the high
     * scores are saved.
     */
    private void updateHighScores() {
        Collections.sort(highScores);
        for (int i = highScores.size(); i > highScores.size(); i--) {
            highScores.remove(i - 1);
        }

        saveHighScores();
    }


}
