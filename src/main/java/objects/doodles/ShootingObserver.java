package objects.doodles;

import input.IMouseInputObserver;
import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

import java.awt.Point;

/**
 * A ShootingObserver is a MouseInputObserver which creates projectiles
 * at the click on the mouse.
 */
/* PACKAGE */ class ShootingObserver implements IMouseInputObserver {

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
    private IDoodle doodle;

    /**
     * Create and initialize a ShootingObserver.
     *
     * @param sL The serviceLocator of this game.
     * @param d  The Doodle this observer belongs to.
     */
    /* package */ ShootingObserver(final IServiceLocator sL, final IDoodle d) {
        this.serviceLocator = sL;
        this.doodle = d;
        this.logger = sL.getLoggerFactory().createLogger(ShootingObserver.class);
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
        int doodleXPos = (int) (this.doodle.getXPos() + this.doodle.getHitBox()[1]/2);
        int doodleYPos = (int) this.doodle.getYPos();
        int direction;
        if (doodleYPos - y < 0) {
            direction = (-(doodleXPos - x)) /2;
        } else {
            direction = (doodleXPos - x) /2;
        }

        IGameObject projectile = this.serviceLocator.getProjectileFactory().createRegularProjectile(new Point(doodleXPos, doodleYPos), direction);

        this.doodle.addProjectile(projectile);
        this.logger.info("The mouse has been clicked in-game, a projectile has been created.");
    }

}
