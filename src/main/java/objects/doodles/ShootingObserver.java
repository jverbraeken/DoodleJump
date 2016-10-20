package objects.doodles;

import input.IMouseInputObserver;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class ShootingObserver implements IMouseInputObserver {

    private ILogger logger;
    private IServiceLocator serviceLocator;

    ShootingObserver(final IServiceLocator sL, final Class<?> objectClass) {
        serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(objectClass);
    }
    /**
     * Registers this ShootingObserver at the InputManager.
     */
    public final void register() {
        getServiceLocator().getInputManager().addObserver(this);
        getLogger().info("The doodle registered an observer to observe the mouse input for shooting.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(int x, int y) {
        System.out.println("ScHIET");
        getLogger().info("The mouse has been clicked in-game, this means shooting.");
    }


    /**
     * @return The serviceLocator of this game.
     */
    public final IServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    /**
     * @return The logger of this observer.
     */
    public final ILogger getLogger() {
        return logger;
    }
}
