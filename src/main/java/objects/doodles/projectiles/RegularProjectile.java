package objects.doodles.Projectiles;

import objects.AGameObject;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.*;

/**
 * A RegularProjectile, mostly spawned in the regular gaming mode.
 */
public final class RegularProjectile extends AGameObject {

    /**
     * The speed this projectile is going up.
     */
    private static final int VERTICAL_SPEED = -40;

    /**
     * The speed this projectile is going up.
     */
    private int xDirection;

    /**
     * Create and initialize a RegularProjectile.
     *
     * @param sL    the servicelocator of this game.
     * @param point The location.
     * @param xDir  The speed over the X-axis
     */
    /* package */RegularProjectile(final IServiceLocator sL, final Point point, final int xDir) {
        super(sL, point, sL.getSpriteFactory().getRegularProjectileSprite(), RegularProjectile.class);
        xDirection = xDir;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), new Point((int) this.getXPos(), (int) this.getYPos()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        setYPos(getYPos() + VERTICAL_SPEED);
        setXPos(getXPos() + xDirection);
    }
}