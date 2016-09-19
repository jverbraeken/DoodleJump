package objects.powerups;

import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

public class Trampoline extends APowerup implements IPowerup {

    /**
     * The boost value for the Trampoline.
     */
    private static final double boost = -50;
    private static IServiceLocator serviceLocator;
    /**
     * The sprite for the Trampoline.
     */
    private ISprite sprite;

    /**
     * Trampoline constructor.
     *
     * @param serviceLocator - The Games service locator.
     * @param x              - The X location for the trampoline.
     * @param y              - The Y location for the trampoline.
     */
    /* package */ Trampoline(final IServiceLocator serviceLocator, final int x, final int y) {
        Trampoline.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getTrampolineSprite();

        this.setXPos(x);
        this.setYPos(y);
        this.setHeight(sprite.getHeight());
        this.setWidth(sprite.getWidth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
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
    public double getBoost() {
        this.animate();
        this.playSound();

        return this.boost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
    }

    /**
     * Play the sound for the Trampoline.
     */
    private void playSound() {
        IAudioManager audioManager = serviceLocator.getAudioManager();
        audioManager.playTrampoline();
    }

}
