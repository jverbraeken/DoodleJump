package objects.blocks.platform;

import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * The platform decorator to support horizontal movement.
 */
@SuppressWarnings("checkstyle:magicnumber")
public final class PlatformBroken extends PlatformDecorator implements IPlatform {

    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;

    /**
     * Platform constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    PlatformBroken(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        getContained().setSprite(sL.getSpriteFactory().getPlatformBrokenSprite1());
        getContained().getProps().put(Platform.PlatformProperties.breaks, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        double xPos = this.getXPos();
        double yPos = this.getYPos();

        int breaks = (int) getProps().get(Platform.PlatformProperties.breaks);
        if (breaks == 1) {
            getServiceLocator().getRenderer().drawSprite(getSprite(), (int) xPos, (int) yPos);
        } else if (breaks < 5 && breaks > 1) {
            getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), (int) xPos, (int) yPos);
        } else if (breaks == -1) {
            applyGravity();
            getServiceLocator().getRenderer().drawSprite(getBrokenSprite(breaks), (int) xPos, (int) yPos);
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
    private ISprite getBrokenSprite(final int numberOfAnimation) {
        if (numberOfAnimation == 2) {
            getProps().replace(Platform.PlatformProperties.breaks, 3);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite2();
        } else if (numberOfAnimation == 3) {
            getProps().replace(Platform.PlatformProperties.breaks, 4);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite3();
        } else if (numberOfAnimation == 4 || numberOfAnimation == -1) {
            getProps().replace(Platform.PlatformProperties.breaks, -1);
            return getServiceLocator().getSpriteFactory().getPlatformBrokenSprite4();
        } else {
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
        System.out.println(getProps().get(Platform.PlatformProperties.breaks));
        if (getProps().get(Platform.PlatformProperties.breaks).equals(1)) {
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
        audioManager.playLomise();
    }
}
