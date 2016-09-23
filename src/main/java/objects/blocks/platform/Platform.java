package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    private static final double boost = -16;

    /**
     * Platform constructor.
     *
     * @param sL             - The games service locator.
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
        this.playSound();
        return Platform.boost; }

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