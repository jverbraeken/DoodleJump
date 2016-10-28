package objects.doodles.Projectiles;

import objects.AGameObject;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.Point;

/**
 * A RegularProjectile, mostly spawned in the regular gaming mode.
 */
public class RegularProjectile extends AGameObject {

    /**
     * The speed this projectile is going up.
     */
    private static final int VERTICAL_SPEED = -40;

    /**
     * The speed this projectile is going up.
     */
    private int direction;

    /**
     * Create and initialize a RegularProjectile.
     * @param sL The serviceLocator of this game.
     * @param point The location.
     */
    /* package */ RegularProjectile(final IServiceLocator sL, final Point point, final int direction) {
        super(sL, point, sL.getSpriteFactory().getRegularProjectileSprite(), RegularProjectile.class);
        this.direction = direction;
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
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), this.getPoint());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        this.setYPos(getYPos() + RegularProjectile.VERTICAL_SPEED);
        this.setXPos(getXPos() + this.direction);
    }

}
