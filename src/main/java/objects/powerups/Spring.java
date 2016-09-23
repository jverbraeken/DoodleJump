package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

public class Spring extends APowerup implements IJumpable {

    /**
     * The sprite for the Spring.
     */
    private ISprite sprite;
    /**
     * The boost value for the Spring.
     */
    private static final double boost = -35;

    /**
     * Trampoline constructor.
     * @param serviceLocator - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(final IServiceLocator serviceLocator, final int x, final int y) {
        super(serviceLocator, x, y, serviceLocator.getSpriteFactory().getSpringSprite());
    }

    private void animate() {
        int oldHeight = this.sprite.getHeight();

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite newSprite = spriteFactory.getSpringUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);

        this.sprite = newSprite;
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        //TODO very unexpected behaviour for a getter. Source of bugs as the programmer does not expect this from a getter
        this.animate();
        this.playSound();

        return Spring.boost;
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Trampoline.
     */
    private void playSound() {
        IAudioManager audioManager = serviceLocator.getAudioManager();
        audioManager.playFeder();
    }


    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }
}