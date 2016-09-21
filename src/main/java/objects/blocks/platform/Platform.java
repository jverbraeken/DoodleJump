package objects.blocks.platform;

import objects.AGameObject;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;

    /**
     * The sprite of the platform.
     */
    private ISprite sprite;

    /**
     * The boost given by the platform.
     */
    private final double boostPlatform = -20;

    /**
     * Platform constructor.
     *
     * @param sL             - The games service locator.
     * @param x              - The X location for the platform.
     * @param y              - The Y location for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y) {
        super();

        Platform.serviceLocator = sL;

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
    public final double getBoost() {
        this.playSound();
        return this.boostPlatform;
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
    public final void render() {
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
