package objects.blocks.platform;

import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * The platform decorator to support horizontal movement.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class PlatformDarkness extends PlatformDecorator implements IPlatform {

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
    PlatformDarkness(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        if (!getProps().containsKey(Platform.PlatformProperties.breaks)) {
            getContained().setSprite(sL.getSpriteFactory().getPlatformSprite1());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        getContained().setSprite(getServiceLocator().getSpriteFactory().getPlatformSprite4());
        getContained().collidesWith(doodle);
    }
}
