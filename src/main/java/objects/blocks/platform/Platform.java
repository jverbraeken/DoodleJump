package objects.blocks.platform;

import input.Keys;
import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    /**
<<<<<<< Updated upstream
     * The BOOST value for the Spring.
     */
    private static final double BOOST = -18;
=======
     * The boost the Doodle gets from colliding with the platform.
     */
    private static final double BOOST = -16;

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
>>>>>>> Stashed changes

    /**
     * Platform constructor.
     *
     * @param sL - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
<<<<<<< Updated upstream
=======
        this.playSound();
>>>>>>> Stashed changes
        return Platform.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        double xpos = this.getXPos();
        int gameWidth = sL.getConstants().getGameWidth();
        if (xpos > gameWidth - this.getSprite().getWidth()) {
            this.props.replace(PlatformProperties.movingHorizontally, -1);
        } else if (xpos < 1) {
            this.props.replace(PlatformProperties.movingHorizontally, 1);
        }

        if (this.props.containsKey(PlatformProperties.movingHorizontally)) {
            if (this.props.get(PlatformProperties.movingHorizontally) > 0) {
                this.setXPos(xpos + 2);
            } else {
                this.setXPos(xpos - 2);
            }
        }
        sL.getRenderer().drawSprite(getSprite(), (int) xpos, (int) this.getYPos());
    }


    /** {@inheritDoc} */
    @Override
<<<<<<< Updated upstream
    public void collidesWith(IDoodle doodle) {
        this.playSound();
=======
    public void collidesWith(final IDoodle doodle) {
>>>>>>> Stashed changes
        doodle.collide(this);
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
    public Map getProps() {
        return props;
    }

}
