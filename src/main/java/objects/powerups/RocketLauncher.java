package objects.powerups;

import objects.AGameObject;
import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the rocket launcher powerup.
 */
/* package */ public final class RocketLauncher extends AGameObject implements IJumpable {

    /**
     * The BOOST value for the Trampoline.
     */
    private static final double BOOST = -90;

    /**
     * RocketLauncher constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the rocket launcher.
     * @param y - The Y location for the rocket launcher.
     */
    /* package */ RocketLauncher(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y - sL.getSpriteFactory().getRocketLauncherSprite().getHeight(), sL.getSpriteFactory().getRocketLauncherSprite(), Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        this.animate();
        //this.playSound();

        return RocketLauncher.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Rocket Launcher.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playTrampoline();
    }

    /**
     * Animate the Rocket Launcher.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();

        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        ISprite newSprite = spriteFactory.getRocketLauncherUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }

}
