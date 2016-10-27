package objects.powerups;

import objects.AGameObject;
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
    private static final int MAX_USES = 3;
    /**
     * The boost provided by the SpringShoes.
     */
    private static final double BOOST = -30d;

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
     * @param sL - The Games service locator.
     * @param point - The location for the SpringShoes.
     */
    /* package */ SpringShoes(final IServiceLocator sL, final Point point) {
        super(sL, point, sL.getSpriteFactory().getPowerupSprite(Powerups.springShoes, 1), SpringShoes.class);
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
            this.owner.setVerticalSpeed(BOOST);

            if (this.uses >= MAX_USES) {
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
        if (this.owner == null && this.uses < MAX_USES) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else if (this.owner != null) {
            int xPos = (int) owner.getXPos() + (owner.getSprite().getWidth() / 2) - (this.getSprite().getWidth() / 2);
            int yPos = (int) owner.getYPos() + owner.getSprite().getHeight();
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

    /**
     *
     */
    @Override
    public void endPowerup() {

    }

}
