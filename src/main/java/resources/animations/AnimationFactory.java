package resources.animations;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import resources.IRes;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.concurrent.ExecutionException;

/**
 * Standard implementation of the AnimationFactory. Used to load and get animations.
 */
public final class AnimationFactory implements IAnimationFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the SpriteFactory class.
     */
    private final ILogger logger;

    /**
     * The animationCache for the SpriteFactory.
     */
    private final LoadingCache<IRes.Animations, IAnimation> animationCache;

    /**
     * Prevents instantiation from outside the class.
     */
    private AnimationFactory() {
        this.logger = resources.animations.AnimationFactory.serviceLocator.getLoggerFactory().createLogger(this.getClass());
        animationCache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.Animations, IAnimation>() {
                            @Override
                            public IAnimation load(final IRes.Animations animation) {
                                return loadIAnimation(animation);
                            }
                        }
                );
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        resources.animations.AnimationFactory.serviceLocator = sL;
        resources.animations.AnimationFactory.serviceLocator.provide(new AnimationFactory());
    }

    /**
     * Loads an ISprite with the name {@code ISpriteName}.
     *
     * @param animationName the enumerator defining the requested sprite
     * @return The {@link IAnimation animation} if it was found. null otherwise
     */
    private IAnimation loadIAnimation(final IRes.Animations animationName) {
        assert animationName != null;

        final int animLength = animationName.getSpriteReferences().length;
        final ISprite[] sprites = new ISprite[animLength];
        for (int i = 0; i < animLength; i++) {
            sprites[i] = serviceLocator.getSpriteFactory().getSprite(animationName.getSpriteReferences()[i]);
        }
        this.logger.info("Animation loaded: \"" + animationName.toString() + "\"");
        return new Animation(sprites);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAnimation getAnimation(final IRes.Animations animation) {
        assert animation != null;
        try {
            return this.animationCache.get(animation);
        } catch (ExecutionException e) {
            this.logger.error(e);
        }
        return null;
    }
}
