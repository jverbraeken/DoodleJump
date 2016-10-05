package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ final class Spring extends APowerup implements IPowerup, IJumpable {

    /**
     * The BOOST value for the Spring.
     */
    private static final double BOOST = -35;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSpringSprite(), Spring.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getBoost() {
        //TODO very unexpected behaviour for a getter. Source of bugs as the programmer does not expect this from a getter
        this.animate();
        this.playSound();

        return Spring.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Spring.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playFeder();
    }

    /**
     * Animate the Spring.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();

        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        ISprite newSprite = spriteFactory.getSpringUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);

        setSprite(newSprite);
    }

}
