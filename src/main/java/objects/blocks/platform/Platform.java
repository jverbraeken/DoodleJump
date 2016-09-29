package objects.blocks.platform;

import objects.AGameObject;
import objects.blocks.BlockFactory;
import objects.doodles.Doodle;
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
     * One third of the game height.
     */
    private static double MOVING_DISTANCE;

    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;

    /**
     * An enum to define what the platform does.
     */
    public enum Directions {
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
     * The properties for this platform.
     */
    private Map<PlatformProperties, Integer> props = new EnumMap<>(PlatformProperties.class);

    /**
     * The directions a platform can go to.
     */
    private Map<Directions, Integer> directions = new EnumMap<>(Directions.class);

    /**
     * The start y of the platform.
     */
    private int offSet = 0;

    /**
     * Platform constructor.
     *
     * @param sL - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite);

        int gameHeight = sL.getConstants().getGameHeight();
        MOVING_DISTANCE = gameHeight * 0.20;

        directions.put(Directions.up, 1);
        directions.put(Directions.down, -1);

        directions.put(Directions.right, 1);
        directions.put(Directions.left, -1);
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
    public void render() {
        double xpos = this.getXPos();
        double ypos = this.getYPos();

        if(BlockFactory.isSpecialPlatform(this)) {
            updateEnums(xpos, ypos);
        }

        if (props.containsKey(PlatformProperties.movingHorizontally)) {
            if (props.get(PlatformProperties.movingHorizontally).equals(directions.get(Directions.right))) {
                this.setXPos(xpos + 2);
            } else if (props.get(PlatformProperties.movingHorizontally).equals(directions.get(Directions.left))) {
                this.setXPos(xpos - 2);
            }
        } else if (props.containsKey(PlatformProperties.movingVertically)) {
            if (props.get(PlatformProperties.movingVertically).equals(directions.get(Directions.up))) {
                this.setYPos(ypos - 2);
                offSet = offSet - 2;
            } else if (props.get(PlatformProperties.movingVertically).equals(directions.get(Directions.down))) {
                this.setYPos(ypos + 2);
                offSet = offSet + 2;
            }
        }

        xpos = this.getXPos();
        ypos = this.getYPos();

        if (props.containsKey(PlatformProperties.breaks)) {
            int breaks = (int) props.get(PlatformProperties.breaks);
            if (breaks == 1) {
                sL.getRenderer().drawSprite(getSprite(), (int) xpos, (int) ypos);
            } else if (breaks < 5 && breaks > 1) {
                sL.getRenderer().drawSprite(getBrokenSprite(breaks), (int) xpos, (int) ypos);
            } else if (breaks == -1) {
                applyGravity();
                sL.getRenderer().drawSprite(getBrokenSprite(breaks), (int) xpos, (int) ypos);
            }
        } else {
            sL.getRenderer().drawSprite(getSprite(), (int) xpos, (int) ypos);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void updateEnums(final double xpos, final double ypos) {

        int gameWidth = sL.getConstants().getGameWidth();
        if (xpos > gameWidth - this.getSprite().getWidth()) {
            this.props.replace(PlatformProperties.movingHorizontally, -1);
        } else if (xpos < 1) {
            this.props.replace(PlatformProperties.movingHorizontally, 1);
        }

        if (offSet > MOVING_DISTANCE) {
            this.props.replace(PlatformProperties.movingVertically, 1);
        } else if (offSet < - MOVING_DISTANCE ) {
            this.props.replace(PlatformProperties.movingVertically, -1);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
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
        IAudioManager audioManager = sL.getAudioManager();
        audioManager.playLomise();
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = sL.getAudioManager();
        audioManager.playJump();
    }

    /** {@inheritDoc} */
    @Override
    public Map<PlatformProperties, Integer> getProps() {
        return props;
    }

    /** {@inheritDoc} */
    @Override
    public void setOffset(final int offSet) {
        this.offSet = offSet;
    }

    /** {@inheritDoc} */
    @Override
    public int getOffset() {
        return offSet;
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getBrokenSprite(final int numberOfAnimation) {
        if (numberOfAnimation == 2) {
            props.replace(PlatformProperties.breaks, 3);
            return sL.getSpriteFactory().getPlatformBrokenSprite2();
        } else if (numberOfAnimation == 3) {
            props.replace(PlatformProperties.breaks, 4);
            return sL.getSpriteFactory().getPlatformBrokenSprite3();
        } else if (numberOfAnimation == 4 || numberOfAnimation == -1) {
            props.replace(PlatformProperties.breaks, -1);
            return sL.getSpriteFactory().getPlatformBrokenSprite4();
        } else {
            return getSprite();
        }
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    private void applyGravity() {
        vSpeed += sL.getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

}
