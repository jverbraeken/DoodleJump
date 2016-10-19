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
public class HighScoreList {

    /**
     * The maximum amount of entries in a HighScoresList.
     */
    private static final int MAX_ENTRIES = 10;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the HighScoreList class.
     */
    private final ILogger logger;
    /**
     * A list of high scores for the game.
     */
    private final ArrayList<HighScore> highScores = new ArrayList<>(MAX_ENTRIES + 1);

    /**
     * Package protected constructor allowing Game to make an instance.
     * @param sL The serviceLocator.
     */
    /* package */ HighScoreList(final IServiceLocator sL) {
        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(HighScoreList.class);
    }

    /**
     * Add a score to the list of highscores.
     * @param name  The name for the score.
     * @param score The actual score.
     */
    public final void addHighScore(final String name, final double score) {
        HighScore scoreEntry = new HighScore(name, score);
        this.highScores.add(scoreEntry);
        updateHighScores();
    }

    /**
     * Initialize the high scores when the program is launched.
     */
    /* package */ final void initHighScores() {
        String plain = this.loadFromFile();
        this.parseHighScoreString(plain);
    }

    /**
     * Get the actual HighScores lists.
     * @return A List of HighScores.
     */
    public final List<HighScore> getList() {
        return this.highScores;
    }

    /**
     * Get the high scores from the high scores file.
     * @return The relevant content of the file.
     */
    private String loadFromFile() {
        IFileSystem fileSystem = this.serviceLocator.getFileSystem();
        IConstants constants = this.serviceLocator.getConstants();

        try {
            List<String> content = fileSystem.readProjectFile(constants.getHighScoresFilePath());
            return content.get(0);
        } catch (Exception e) {
            this.logger.error(e);
            this.logger.warning("High scores file not found, starting with empty high scores list");
        }

        return "";
    }

    /**
     * Parse a high scores string as saved in the high scores file.
     * @param plain The plaintext string.
     */
    private void parseHighScoreString(final String plain) {
        String[] scores = plain.split("\\s+");
        for (int i = 1; i < scores.length; i += 2) {
            HighScore score = new HighScore(scores[i - 1], scores[i]);
            this.highScores.add(score);
        }
    }

    /**
     * Save the high scores to the high scores file.
     */
    private void saveHighScores() {
        // Convert high scores to string
        StringBuilder buffer = new StringBuilder();
        for (HighScore score : this.highScores) {
            buffer.append(score.getName());
            buffer.append(" ");
            buffer.append(score.getScore());
            buffer.append(" ");
        }
        String data = buffer.toString();

        // Save high scores.
        IFileSystem fileSystem = this.serviceLocator.getFileSystem();
        IConstants constants = this.serviceLocator.getConstants();
        try {
            fileSystem.writeProjectFile(constants.getHighScoresFilePath(), data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.logger.error(e);
        }
    }

    /**
     * Update the high scores for the game. Makes sure the max amount of high scores is not exceeded and the high scores
     * are saved.
     */
    private void updateHighScores() {
        Collections.sort(this.highScores);
        for (int i = this.highScores.size(); i > HighScoreList.MAX_ENTRIES; i--) {
            this.highScores.remove(i - 1);
        }

        this.saveHighScores();
    }

}
