package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import objects.enemies.EEnemies;
import objects.powerups.Powerups;
import resources.IRes;
import scenes.PauseScreenModes;
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
     * The cache for the SpriteFactory.
     */
    private final LoadingCache<IRes.Sprites, ISprite> cache;

    /**
     * Prevents instantiation from outside the class.
     */
    public SpriteFactory() {
        this.logger = SpriteFactory.serviceLocator.getLoggerFactory().createLogger(SpriteFactory.class);
        cache = CacheBuilder.newBuilder()
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
     * {@inheritDoc}
     */
    @Override
    public ISprite getSprite(final IRes.Sprites sprite) {
        assert sprite != null;
        try {
            return this.cache.get(sprite);
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
