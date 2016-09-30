package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
public class Trampoline extends APowerup implements IJumpable {

    /**
     * The BOOST value for the Trampoline.
     */
    private static final double BOOST = -50;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Trampoline(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getTrampolineSprite());
    }

    /** {@inheritDoc} */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

    /** {@inheritDoc} */
    @Override
    public final double getBoost() {
        //TODO This is can cause bugs as the programmer does not a getter to do these kind of things
        this.animate();
        this.playSound();

        return Trampoline.BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public final void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Trampoline.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playTrampoline();
    }

    /**
     * Animate the Trampoline.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();

        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        ISprite newSprite = spriteFactory.getTrampolineUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }

}
