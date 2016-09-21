package objects.blocks.platform;

import objects.AGameObject;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    private ISprite sprite;
    private double boost = -20;

    /**
     * Platform constructor.
     *
     * @param serviceLocator - The games service locator.
     * @param x              - The X location for the platform.
     * @param y              - The Y location for the platform.
     */
    /* package */ Platform(IServiceLocator serviceLocator, int x, int y) {
        super();

        Platform.serviceLocator = serviceLocator;

        this.setXPos(x);
        this.setYPos(y);
        this.sprite = serviceLocator.getSpriteFactory().getPlatformSprite1();
        this.setHeight(sprite.getHeight());
        this.setWidth(sprite.getWidth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
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
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = serviceLocator.getAudioManager();
        audioManager.playJump();
    }

}
