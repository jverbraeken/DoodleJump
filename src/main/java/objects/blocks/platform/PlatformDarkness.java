package objects.blocks.platform;

import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * The platform decorator to support darkness platforms.
 * These platforms are invisible until jumped upon.
 * This is done by retaining their default skin until jumped upon
 * The default skin iss set to invisible due to the mode.
 */
public final class PlatformDarkness extends PlatformDecorator implements IPlatform {

    /**
     * Darkness platform constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    /* package */PlatformDarkness(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        if (!getProps().containsKey(Platform.PlatformProperties.breaks)) {
            getContained().setSprite(sL.getSpriteFactory().getPlatformSprite1());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        getContained().setSprite(getServiceLocator().getSpriteFactory().getPlatformSprite4());
        getContained().collidesWith(doodle);
    }
}
