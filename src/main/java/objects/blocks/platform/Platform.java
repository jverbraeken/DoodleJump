package objects.blocks.platform;

import objects.AGameObject;
import objects.IGameObject;
import objects.doodles.IDoodle;
import objects.powerups.IPowerup;
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
    /* package */ Platform(IServiceLocator sL, int x, int y) {
        super(sL, x, y, sL.getSpriteFactory().getPlatformSprite1());
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
        sL.getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }


    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        this.playSound();
        doodle.collide(this);
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = sL.getAudioManager();
        audioManager.playJump();
    }

}