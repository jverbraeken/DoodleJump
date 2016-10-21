package objects.doodles.Projectiles;

import objects.AGameObject;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * A RegularProjectile, mostly spawned in the regular gaming mode.
 */
class RegularProjectile extends AGameObject {

    /**
     * The speed this projectile is going up.
     */
    private static final int VERTICAL_SPEED = -40;

    /**
     * Create and initialize a RegularProjectile.
     * @param sL the servicelocator of this game.
     * @param x the x location.
     * @param y the y location.
     */
    /* package */RegularProjectile(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getRegularProjectileSprite(), RegularProjectile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        setYPos(getYPos() + VERTICAL_SPEED);
        checkEnemyCollisions();
    }

    /**
     * Checks for all enemies currently in the blocks and if this
     * projectile collides with one of them.
     */
    private void checkEnemyCollisions() {
    }
}
