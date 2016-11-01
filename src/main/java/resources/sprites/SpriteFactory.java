package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import resources.IRes;
import system.IServiceLocator;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

/**
 * Standard implementation of the SpriteFactory. Used to load and get sprites.
 * <br>
 * It is not deemed necessary for all individual sprites to have a JavaDoc.
 */
public final class SpriteFactory implements ISpriteFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the SpriteFactory class.
     */
    private final ILogger logger;

    /**
     * The spriteCache for the SpriteFactory.
     */
    private final LoadingCache<IRes.Sprites, ISprite> spriteCache;

    /**
     * The animationCache for the SpriteFactory.
     */
    private final LoadingCache<IRes.Animations, IAnimation> animationCache;

    /**
     * Prevents instantiation from outside the class.
     */
    public SpriteFactory() {
        this.logger = SpriteFactory.serviceLocator.getLoggerFactory().createLogger(SpriteFactory.class);
        spriteCache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.Sprites, ISprite>() {
                            @Override
                            public ISprite load(final IRes.Sprites sprite) {
                                logger.info("Sprite loaded: \"" + sprite + "\"");
                                return loadISprite(sprite);
                            }
                        }
                );
        animationCache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.Animations, IAnimation>() {
                            @Override
                            public IAnimation load(final IRes.Animations sprite) {
                                logger.info("Animation loaded: \"" + sprite + "\"");
                                return loadIAnimation(sprite);
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
        SpriteFactory.serviceLocator = sL;
        SpriteFactory.serviceLocator.provide(new SpriteFactory());
    }

    /**
     * Loads an ISprite with the name {@code ISpriteName}.
     *
     * @param spriteName the enumerator defining the requested sprite
     * @return The {@link ISprite sprite} if it was found. null otherwise
     */
    private ISprite loadISprite(final IRes.Sprites spriteName) {
        assert spriteName != null;

        String filepath = SpriteFactory.serviceLocator.getRes().getSpritePath(spriteName);

        try {
            BufferedImage image = SpriteFactory.serviceLocator.getFileSystem().readImage(filepath);
            this.logger.info("Sprite loaded: \"" + filepath + "\"");
            return new Sprite(getFileName(filepath), image);
        } catch (FileNotFoundException e) {
            this.logger.error("CRITICAL ERROR: the sprite \"" + spriteName.toString() + "\" could not be found!");
            this.logger.error(e);
            e.printStackTrace();
            return null;
        }
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
            sprites[i] = getSprite(animationName.getSpriteReferences()[i]);
        }
        return new Animation(sprites);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSprite(final IRes.Sprites sprite) {
        assert sprite != null;
        try {
            return this.spriteCache.get(sprite);
        } catch (ExecutionException e) {
            this.logger.error(e);
        }

        return null;
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

    /**
     * Returns the filename from a filepath.
     * <br>
     * Example: {@code getFileName("resources/Sprites/sprite.png").equals("sprite.png")}
     *
     * @param filepath The full path to the file, the directories seperated by '/'. Cannot be null
     * @return The name of the file
     */
    private String getFileName(final String filepath) {
        assert filepath != null;
        assert !filepath.contains("\\");

        int fileNameIndex = filepath.lastIndexOf('/') + 1;
        return filepath.substring(fileNameIndex);
    }
}
