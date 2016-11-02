package objects.blocks.platform;

import math.GenerationSet;
import objects.blocks.ElementTypes;
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
     * A weighted set for the spawning of platforms.
     */
    private GenerationSet platformGenerationSet;

    /**
     * Prevent instantiations of PlatformFactory.
     */
    private PlatformFactory() {
        platformGenerationSet = new GenerationSet(serviceLocator, "platforms");
    }

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
            return new PlatformDarkness(serviceLocator, platform);
        }
        return platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createPlatform(final ElementTypes type) {
        switch (type) {
            case normalPlatform:
                return createPlatform(0, 0);
            case verticalMovingPlatform:
                return createVerticalMovingPlatform(0, 0);
            case horizontalMovingPlatform:
                return createHorizontalMovingPlatform(0, 0);
            case darknessPlatform:
                return createDarknessPlatform(0, 0);
            case randomPlatform:
                return createRandomPlatform();
            default:
                throw new RuntimeException("No such element (" + type + ") in platform types");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createDarknessPlatform(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getSprite(IRes.Sprites.platform1);
        final Point point = new Point(x, y);
        IPlatform platform = new Platform(serviceLocator, point, sprite);
        return new PlatformDarkness(serviceLocator, platform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createHorizontalMovingPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);

        return new PlatformHorizontal(serviceLocator, platform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createVerticalMovingPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);

        return new PlatformVertical(serviceLocator, platform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createBreakPlatform(final int x, final int y) {
        IPlatform platform = createPlatform(x, y);

        return new PlatformBroken(serviceLocator, platform);
    }

    private IPlatform createRandomPlatform() {
        return (IPlatform) platformGenerationSet.getRandomElement();
    }
}
