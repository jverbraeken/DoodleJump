package objects.doodles;

import input.IMouseInputObserver;
import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

/**
 * A ShootingObserver is a MouseInputObserver which creates projectiles
 * at the click on the mouse.
 */
/*PACKAGE */ class ShootingObserver implements IMouseInputObserver {

    /**
     * The logger of this game.
     */
    private ILogger logger;
    /**
     * The serviceLocator of this game.
     */
    private IServiceLocator serviceLocator;
    /**
     * The Doodle in this world.
     */
    private Doodle doodle;

    /**
     * Create and initialize a ShootingObserver.
     *
     * @param sL the servicelocator of this game.
     * @param d  the Doodle this observer belongs to.
     */
    /* package */ ShootingObserver(final IServiceLocator sL, final Doodle d) {
        serviceLocator = sL;
        doodle = d;
        logger = sL.getLoggerFactory().createLogger(ShootingObserver.class);
    }

    /**
     * Registers this ShootingObserver at the InputManager.
     */
    public final void register() {
        this.serviceLocator.getInputManager().addObserver(this);
        this.logger.info("The doodle registered an observer to observe the mouse input for shooting.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void mouseClicked(final int x, final int y) {
        int doodleXPos = (int) (doodle.getXPos() + doodle.getHitBox()[1] / 2);
        int doodleYPos = (int) doodle.getYPos();
        int xDir = 0;
        if (doodleYPos - y < 0) {
            xDir = (-(doodleXPos - x)) / 2;
        } else {
            xDir = (doodleXPos - x) / 2;
        }

        IGameObject projectile = serviceLocator.getProjectileFactory().createRegularProjectile(doodleXPos, doodleYPos, xDir, 0);
        this.doodle.addProjectile(projectile);
        this.logger.info("The mouse has been clicked in-game, a projectile has been created.");
    }
}
