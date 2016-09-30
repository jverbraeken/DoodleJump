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
    private IServiceLocator serviceLocator;
    /**
     * The logger for the HighScoreList class.
     */
    private ILogger logger;
    /**
     * A list of high scores for the game.
     */
    private ArrayList<HighScore> highScores = new ArrayList<>(MAX_ENTRIES + 1);

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
    public final void addHighScore(final String name, final double score) {
        HighScore scoreEntry = new HighScore(name, score);
        highScores.add(scoreEntry);
        updateHighScores();
    }

    /**
     * Initialize the high scores when the program is launched.
     */
    /* package */ final void initHighScores() {
        String plain = this.loadFromFile();
        if (plain != null) {
            this.parseHighScoreString(plain);
        }
    }

    /**
     * Get the high scores from the high scores file.
     *
     * @return The relevant content of the file.
     */
    private String loadFromFile() {
        IFileSystem fileSystem = serviceLocator.getFileSystem();
        IConstants constants = serviceLocator.getConstants();
        try {
            List<String> content = fileSystem.readProjectFile(constants.getHighScoresFilePath());

            if (content.size() > 0 && !content.get(0).equals("")) {
                return content.get(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        StringBuilder buf = new StringBuilder();
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
            fileSystem.writeProjectFile(constants.getHighScoresFilePath(), data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        for (int i = highScores.size(); i > MAX_ENTRIES; i--) {
            highScores.remove(i - 1);
        }

        saveHighScores();
    }

}
