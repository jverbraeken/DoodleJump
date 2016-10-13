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
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * The start y of the platform.
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
     * @param sL - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     * @param sprite - The sprite for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite, Platform.class);

        directions.put(Directions.right, 1);
        directions.put(Directions.left, -1);
    }

    /** {@inheritDoc} */
    @Override
    public final double getBoost() {
        return Platform.BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
    }

    /** {@inheritDoc} */
    @Override
    public final void render() {
        double xPos = this.getXPos();
        double yPos = this.getYPos();

        if (props.containsKey(PlatformProperties.breaks)) {
            int breaks = (int) props.get(PlatformProperties.breaks);
            if (breaks == 1) {
                getServiceLocator().getRenderer().drawSprite(getSprite(), (int) xPos, (int) yPos);
            } else if (breaks < 5 && breaks > 1) {
                getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), (int) xPos, (int) yPos);
            } else if (breaks == -1) {
                applyGravity();
                getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), (int) xPos, (int) yPos);
            }
        } else {
            getServiceLocator().getRenderer().drawSprite(getSprite(), (int) xPos, (int) yPos);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void updateEnums(final double xPos, final double yPos) {
    }

    /** {@inheritDoc} */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (props.containsKey(PlatformProperties.breaks)) {
            if (props.get(PlatformProperties.breaks) == 1) {
                props.replace(PlatformProperties.breaks, 2);
                vSpeed = doodle.getVerticalSpeed() / 2;
                playBreakSound();
            }
        } else {
            this.playSound();
            doodle.collide(this);
        }
    }

    /**
     * Play the breaking sound for the Platform.
     */
    private void playBreakSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playLomise();
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playJump();
    }

    /** {@inheritDoc} */
    @Override
    public final Map<PlatformProperties, Integer> getProps() {
        return props;
    }

    /** {@inheritDoc} */
    @Override
    public final Map<Directions, Integer> getDirections() {
        return directions;
    }

    /** {@inheritDoc} */
    @Override
    public final void setOffset(final int value) {
        this.offSet = value;
    }

    /** {@inheritDoc} */
    @Override
    public final int getOffset() {
        return offSet;
    }

    /**
     * Will return the Sprite of the broken platform, dependent
     * on the number of the animation. SO which phase it is in.
     *
     * @param numberOfAnimation the phase of the animation
     * @return the sprite belonging to this animation phase
     */
    private ISprite getBrokenSprite(final int numberOfAnimation) {
        if (numberOfAnimation == 2) {
            props.replace(PlatformProperties.breaks, 3);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite2();
        } else if (numberOfAnimation == 3) {
            props.replace(PlatformProperties.breaks, 4);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite3();
        } else if (numberOfAnimation == 4 || numberOfAnimation == -1) {
            props.replace(PlatformProperties.breaks, -1);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite4();
        } else {
            return getSprite();
        }
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    private void applyGravity() {
        vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

}
