package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import system.IServiceLocator;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    /**
     * The BOOST value for the Spring.
     */
    private static final double BOOST = -18;

    /**
     * Platform constructor.
     *
     * @param sL - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getPlatformSprite1(), Platform.class);
    }

    /** {@inheritDoc} */
    @Override
    public final double getBoost() {
        return Platform.BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public final void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
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

}
