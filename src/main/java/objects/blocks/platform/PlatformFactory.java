package objects.blocks.platform;

import resources.IRes;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.awt.Point;

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
        ISprite sprite = serviceLocator.getSpriteFactory().getSprite(IRes.Sprites.platform1);
        final Point point = new Point(x, y);
        IPlatform platform = new Platform(serviceLocator, point, sprite);

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
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformBrokenSprite(1);
        IPlatform platform = createPlatform(x, y);

        return new PlatformBroken(serviceLocator, platform);
    }

}
