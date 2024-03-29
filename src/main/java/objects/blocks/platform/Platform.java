package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.audio.Sounds;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class focuses on the implementation of platforms.
 */
public final class Platform extends AGameObject implements IPlatform {

    /**
     * The boost the Doodle gets from colliding with the platform.
     */
    private static final double BOOST = -18;
    /**
     * The offSet of the vertical moving platform.
     */
    private double offSet = 0;
    /**
     * The properties for this platform.
     */
    private Map<PlatformProperties, Integer> props = new EnumMap<>(PlatformProperties.class);

    /**
     * The directions a platform can go to.
     */
    private Map<Directions, Integer> directions = new EnumMap<>(Directions.class);

    /**
     * An enum to define what the platform does.
     */
    enum Directions {
        /**
         * For moving up, normally 1.
         */
        up,
        /**
         * For moving down, normally -1.
         */
        down,
        /**
         * For moving down, normally 1.
         */
        right,
        /**
         * For moving down, normally -1.
         */
        left
    }

    /**
     * An enum to define what the platform does.
     */
    public enum PlatformProperties {
        /**
         * Defined if the platform moves vertically.
         */
        movingVertically,
        /**
         * Defined if the platform moves horizontally.
         */
        movingHorizontally,
        /**
         * Defined if the platform breaks when colliding with Doodle.
         */
        breaks
    }

    /**
     * Platform constructor.
     *
     * @param sL     - The games service locator.
     * @param point  - The location for the platform.
     * @param sprite - The sprite for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final Point point, final ISprite sprite) {
        super(sL, point, sprite, Platform.class);
    }

    /**
    * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return Platform.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        double xPos = this.getXPos();
        double yPos = this.getYPos();
        getServiceLocator().getRenderer().drawSprite(getSprite(), new Point((int) xPos, (int) yPos));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEnums(final double xPos, final double yPos) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (doodle.getVerticalSpeed() > 0 && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            this.playSound();
            doodle.collide(this);
        }
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.play(Sounds.JUMP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<PlatformProperties, Integer> getProps() {
        return props;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Directions, Integer> getDirections() {
        return directions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffset(final double value) {
        this.offSet = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getOffset() {
        return offSet;
    }

}
