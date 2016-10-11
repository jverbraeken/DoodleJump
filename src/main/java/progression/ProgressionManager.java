package progression;


import system.IServiceLocator;

/**
 * Standard implementation of the ProgressionManager. Used to contain all "global" variables that describe
 * the progression of the player, eg coins, highscores, unlocked/upgraded items
 */
public final class ProgressionManager implements IProgressionManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

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

    private ProgressionManager() {

    }
}