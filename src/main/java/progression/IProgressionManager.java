package progression;

import java.util.List;

/**
 * Defines a progression manager.
 */
public interface IProgressionManager {

    /**
     * Initializes the progression manager. Normally the progression will be loaded from the disk in this method.
     */
    void init();

    /**
     * Adds a new high score and saves it to the disk.
     *
     * @param name  The name associated with the new highscore
     * @param score The score associated with the new highscore
     */
    void addHighScore(String name, double score);

    /**
     * @return A list containing the highscores of the player.
     */
    List<HighScore> getHighscores();

    /**
     * @return The amount of coins the player has.
     */
    int getCoins();

    /**
     * @return A list containing the active missions.
     */
    List<Mission> getMissions();

    /**
     * Alert the observers of a certain type that an action happened.
     *
     * @param progressionObserver Indicates which kind of observer should be alerted.
     */
    void alertObservers(IProgressionObserver progressionObserver);

    /**
     * Alert the observers of a certain type that an action happened.
     *
     * @param progressionObserver Indicates which kind of observer should be alerted.
     * @param amount              The amount that changed for the thing that caused the reason to alert the observers.
     */
    void alertObservers(IProgressionObserver progressionObserver, double amount);
}
