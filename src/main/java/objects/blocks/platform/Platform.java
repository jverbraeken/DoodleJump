package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    /**
     * The boost the Doodle gets from colliding with the platform.
     */
    private static final double BOOST = -18;

    /**
     * The maximum moving distance the platform can move.
     */
    private static double movingDistance;

    /**
     * The multiplier to calculate the movingDistance.
     */
    private static final double MOVING_DISTANCE_HEIGHT_MULTIPLIER = 0.20;

    /**
     * Current vertical speed for the Platform.
     */
    private double vSpeed = 0d;
    /**
     * The offSet of the vertical moving platform.
     */
    private int offSet = 0;
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
     * @param x      - The X location for the platform.
     * @param y      - The Y location for the platform.
     * @param sprite - The sprite for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite, Platform.class);

        int gameHeight = sL.getConstants().getGameHeight();
        movingDistance = gameHeight * MOVING_DISTANCE_HEIGHT_MULTIPLIER;

        directions.put(Directions.up, 1);
        directions.put(Directions.down, -1);

    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public final double getBoost() {
        return Platform.BOOST;
    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        double xPos = this.getXPos();
        double yPos = this.getYPos();
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) xPos, (int) yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateEnums(final double xPos, final double yPos) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        this.playSound();
        doodle.collide(this);
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playJump();
    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public final Map<PlatformProperties, Integer> getProps() {
        return props;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<Directions, Integer> getDirections() {
        return directions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setOffset(final int value) {
        this.offSet = value;
    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public final int getOffset() {
        return offSet;
    }

}
