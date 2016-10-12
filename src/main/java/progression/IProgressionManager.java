package progression;

import java.util.List;

/**
 * Defines a progression manager.
 */
public interface IProgressionManager extends
        IHeightObserver,
        IJetpackUsedObserver,
        IPropellerUsedObserver,
        ISizeDownUsedObserver,
        ISizeUpUserObserver,
        ISpringUsedObserver,
        ISpringShoesUsedObserver,
        ITrampolineJumpedObserver {

    /**
     * Initializes the progression manager. Normally the progression will be loaded from the disk in this method.
     */
    void init();

    /**
     * Adds a new high score and saves it to the disk.
     * @param name The name associated with the new highscore
     * @param score The score associated with the new highscore
     */
    void addHighScore(String name, double score);

    /**
     * @return A list containing the highscores of the player.
     */
    List<HighScore> getHighscores();
}
