package objects.powerups;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.*;

/**
 * This class describes the behaviour of the SpringShoes powerup.
 */
/* package */ final class SpringShoes extends APowerup implements IEquipmentPowerup {

    /**
     * The maximum amount of times SpringShoes can be used.
     */
    private final int maxUses;
    /**
     * The boost provided by the SpringShoes.
     */
    private final double boost;

    /**
     * The Doodle that owns these SpringShoes.
     */
    private IDoodle owner;
    /**
     * The amount of times the SpringShoes are used.
     */
    private int uses = 0;

    /**
     * Jump boots constructor.
     *
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ SpringShoes(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.springShoes, level);
        final Powerups type = Powerups.springShoes;
        this.boost = type.getBoost(level);
        this.maxUses = type.getMaxUses(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(final PowerupOccasion occasion) {
        if (this.owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }

        if (occasion == PowerupOccasion.collision) {
            this.uses += 1;
            this.owner.setVerticalSpeed(boost);

            if (this.uses >= maxUses) {
                this.owner.removePowerup(this);
                this.owner = null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (this.owner == null && doodle.getVerticalSpeed() > 0 && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            getLogger().info("Doodle collided with a pair of SpringShoes");
            this.owner = doodle;
            doodle.setPowerup(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (this.owner == null && this.uses < maxUses) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), new Point((int) this.getXPos(), (int) this.getYPos()));
        } else if (this.owner != null) {
            int xPos = (int) owner.getXPos()
                    + owner.getSprite().getWidth() / 2
                    - this.getSprite().getWidth() / 2;
            int yPos = (int) owner.getYPos()
                    + owner.getSprite().getHeight();
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), new Point(xPos, yPos));
        }
    }

    /**
     *
     */
    @Override
    public void endPowerup() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }

}
