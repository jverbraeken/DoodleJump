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
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * The sprite for the Trampoline.
     */
    private ISprite sprite;

    /**
     * Trampoline constructor.
     *
     * @param serviceLocator - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Trampoline(final IServiceLocator serviceLocator, final int x, final int y) {
        super(serviceLocator, x, y, serviceLocator.getSpriteFactory().getTrampolineSprite());
    }

    private void animate() {
        int oldHeight = this.sprite.getHeight();

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite newSprite = spriteFactory.getTrampolineUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);

        this.sprite = newSprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getBoost() {
        //TODO This is can cause bugs as the programmer does not a getter to do these kind of things
        this.animate();
        this.playSound();

        return Trampoline.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Trampoline.
     */
    private void playSound() {
        IAudioManager audioManager = serviceLocator.getAudioManager();
        audioManager.playTrampoline();
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }

}
