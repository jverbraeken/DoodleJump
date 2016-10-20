package objects.blocks.platform;

import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/**
 * This class is a factory that produces platforms.
 */
public final class PlatformFactory implements IPlatformFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of PlatformFactory.
     */
    private PlatformFactory() { }

    /**
     * Fifty-fifty chance.
     */
    private static final double FIFTY_FIFTY = 0.5d;

    /**
     * Register the block factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        PlatformFactory.serviceLocator = sL;
        sL.provide(new PlatformFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformSprite1();
        IPlatform platform = new Platform(serviceLocator, x, y, sprite);

        if (Game.getMode().equals(Game.Modes.darkness)) {
            IPlatform darkness = new PlatformDarkness(serviceLocator, platform);
            return  darkness;
        }
        return platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createHorizontalMovingPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);
        IPlatform sideways = new PlatformHorizontal(serviceLocator, platform);

        return sideways;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createVerticalMovingPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);
        IPlatform vertical = new PlatformVertical(serviceLocator, platform);

        return vertical;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createBreakPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformBrokenSprite1();
        IPlatform platform = new Platform(serviceLocator, x, y, sprite);
        IPlatform broken = new PlatformBroken(serviceLocator, platform);

        return broken;
    }

}
