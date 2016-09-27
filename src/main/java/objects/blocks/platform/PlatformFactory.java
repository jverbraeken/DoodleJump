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
    public IPlatform createHoriMovingPlatform(int x, final int y) {
        IPlatform platform = new Platform(sL, x, y, sL.getSpriteFactory().getPlatformSpriteHori());
        platform.getProps().put(Platform.PlatformProperties.movingHorizontally, 1);

        return platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createVertMovingPlatform(final int x, final int y) {
        IPlatform platform = new Platform(sL, x, y, sL.getSpriteFactory().getPlatformSpriteVert());

        Platform.PlatformProperties vertical = Platform.PlatformProperties.movingVertically;


        int upOrDown = 1;
        if (sL.getCalc().getRandomDouble(1) < 0.50d) {
            upOrDown = -1;
        }
        platform.getProps().put(vertical, upOrDown);

        return platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createBreakPlatform(int x, final int y) {
        IPlatform platform = new Platform(sL, x, y, sL.getSpriteFactory().getPlatformBrokenSprite1());
        platform.getProps().put(Platform.PlatformProperties.movingHorizontally, 1);

        return platform;
    }


}
