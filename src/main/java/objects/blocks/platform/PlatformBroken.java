package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.IRes;
import resources.audio.IAudioManager;
import resources.audio.Sounds;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;

/**
 * The platform decorator to support breaking platforms.
 */
public final class PlatformBroken extends PlatformDecorator implements IPlatform {

    /**
     * Current vertical speed for the platform.
     */
    private double vSpeed = 0d;

    /**
     * broken platform decorator constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    /* package */PlatformBroken(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        getContained().setSprite(sL.getSpriteFactory().getSprite(IRes.Sprites.platformBroken1));
        getContained().getProps().put(Platform.PlatformProperties.breaks, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void render() {
        int xPos = (int) this.getXPos();
        int yPos = (int) this.getYPos();

        int breaks = (int) getProps().get(Platform.PlatformProperties.breaks);
        if (breaks == 1) {
            getServiceLocator().getRenderer().drawSprite(getSprite(), new Point(xPos, yPos));
        } else if (breaks <= 4 && breaks > 1) {
            getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), new Point(xPos, yPos));
        } else if (breaks == -1) {
            applyGravity();
            getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), new Point(xPos, yPos));
        } else {
            getContained().render();
        }
    }

    /**
     * Will return the Sprite of the broken platform, dependent
     * on the number of the animation. SO which phase it is in.
     *
     * @param numberOfAnimation the phase of the animation
     * @return the sprite belonging to this animation phase
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private ISprite getBrokenSprite(final int numberOfAnimation) {
        switch (numberOfAnimation) {
            case 2:
                getProps().replace(Platform.PlatformProperties.breaks, 3);
                return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite(2);
            case 3:
                getProps().replace(Platform.PlatformProperties.breaks, 4);
                return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite(3);
            case 4:
                getProps().replace(Platform.PlatformProperties.breaks, -1);
                return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite(4);
            case -1:
                getProps().replace(Platform.PlatformProperties.breaks, -1);
                return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite(4);
            default:
                return getSprite();
        }
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    private void applyGravity() {
        vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (getProps().get(Platform.PlatformProperties.breaks).equals(1)
                && doodle.getVerticalSpeed() > 0
                && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            getProps().replace(Platform.PlatformProperties.breaks, 2);
            vSpeed = doodle.getVerticalSpeed() / 2;
            playBreakSound();
        }
    }

    /**
     * Play the breaking sound for the Platform.
     */
    private void playBreakSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.play(Sounds.LOMISE);
    }
}
