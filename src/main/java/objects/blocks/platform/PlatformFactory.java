package objects.blocks.platform;

import system.Game;
import system.IServiceLocator;

/**
 * This class is a factory that produces platforms.
 */
public final class PlatformFactory implements IPlatformFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

    /**
     * Prevent instantiations of PlatformFactory.
     */
    private PlatformFactory() {
    }

    /**
     * Register the block factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        PlatformFactory.sL = sL;
        sL.provide(new PlatformFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createPlatform(final int x, final int y) {
        return new Platform(sL, x, y, sL.getSpriteFactory().getPlatformSprite1());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createMovingPlatform(int x, final int y) {
        IPlatform platform = new Platform(sL, x, y, sL.getSpriteFactory().getPlatformSprite2());
        platform.getProps().put(Platform.PlatformProperties.movingHorizontally, 1);

        return platform;
    }



}
