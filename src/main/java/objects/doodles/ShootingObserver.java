package objects.doodles;

import input.IMouseInputObserver;
import logging.ILogger;
import objects.IGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class ShootingObserver implements IMouseInputObserver {

    private ILogger logger;
    private IServiceLocator serviceLocator;
    private Doodle doodle;

    ShootingObserver(final IServiceLocator sL, final Doodle d, final Class<?> objectClass) {
        serviceLocator = sL;
        doodle = d;
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
        IGameObject projectile = serviceLocator.getProjectileFactory().createRegularProjectile((int) doodle.getXPos(), (int) doodle.getYPos());
        doodle.addProjectile(projectile);
        getLogger().info("The mouse has been clicked in-game, a projectile has been created.");
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
