package objects.blocks.platform;

import resources.sprites.ISprite;
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
     * Register the block factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        PlatformFactory.serviceLocator = sL;
        sL.provide(new PlatformFactory());
    }

    /** {@inheritDoc} */
    @Override
    public IPlatform createPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformSprite1();
        return new Platform(serviceLocator, x, y, sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createHoriMovingPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);
        IPlatform sideways = new PlatformSideways(serviceLocator, platform);

        return sideways;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createVertMovingPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformSpriteVert();
        IPlatform platform = new Platform(serviceLocator, x, y, sprite);

        Platform.PlatformProperties vertical = Platform.PlatformProperties.movingVertically;


        int upOrDown = 1;
        if (serviceLocator.getCalc().getRandomDouble(1) < 0.50d) {
            upOrDown = -1;
        }
        platform.getProps().put(vertical, upOrDown);

        return platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createBreakPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getPlatformBrokenSprite1();
        IPlatform platform = new Platform(serviceLocator, x, y, sprite);
        platform.getProps().put(Platform.PlatformProperties.breaks, 1);

        return platform;
    }


}
