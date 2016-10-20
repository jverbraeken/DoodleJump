package objects.doodles;

import input.IMouseInputObserver;
import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

/**
 * A ShootingObserver is a MouseInputObserver which creates projectiles
 * at the click on the mouse.
 */
public class ShootingObserver implements IMouseInputObserver {

    private ILogger logger;
    private IServiceLocator serviceLocator;
    private Doodle doodle;

    /**
     * Create and initialize a ShootingObserver.
     * @param sL the servicelocator of this game.
     * @param d the Doodle this observer belongs to.
     */
    ShootingObserver(final IServiceLocator sL, final Doodle d) {
        serviceLocator = sL;
        doodle = d;
        logger = sL.getLoggerFactory().createLogger(ShootingObserver.class);
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
    public final void mouseClicked(final int x, final int y) {
        IGameObject projectile = serviceLocator.getProjectileFactory().createRegularProjectile((int) doodle.getXPos(), (int) doodle.getYPos());
        doodle.addProjectile(projectile);
        getLogger().info("The mouse has been clicked in-game, a projectile has been created.");
    }


    /**
     * Will return the serviceLocator.
     * @return The serviceLocator of this game.
     */
    public final IServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    /**
     * Will return the logger of this observer.
     * @return The logger of this observer.
     */
    public final ILogger getLogger() {
        return logger;
    }
}
