package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
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
        this.logger.info("Animation loaded: \"" + animationName.toString() + "\"");
        return new Animation(sprites);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getDigitSprites() {
        return new ISprite[]{
                this.getSprite(IRes.Sprites.zero),
                this.getSprite(IRes.Sprites.one),
                this.getSprite(IRes.Sprites.two),
                this.getSprite(IRes.Sprites.three),
                this.getSprite(IRes.Sprites.four),
                this.getSprite(IRes.Sprites.five),
                this.getSprite(IRes.Sprites.six),
                this.getSprite(IRes.Sprites.seven),
                this.getSprite(IRes.Sprites.eight),
                this.getSprite(IRes.Sprites.nine)
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getGreenDoodleSprites() {
        ISprite[] sprites = new ISprite[4];
        sprites[0] = this.getSprite(IRes.Sprites.greenDoodleLeftDescend);
        sprites[1] = this.getSprite(IRes.Sprites.greenDoodleLeftAscend);
        sprites[2] = this.getSprite(IRes.Sprites.greenDoodleRightDescend);
        sprites[3] = this.getSprite(IRes.Sprites.greenDoodleRightAscend);

        return sprites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getRedDoodleSprites() {
        ISprite[] sprites = new ISprite[4];
        sprites[0] = this.getSprite(IRes.Sprites.redDoodleLeftDescend);
        sprites[1] = this.getSprite(IRes.Sprites.redDoodleLeftAscend);
        sprites[2] = this.getSprite(IRes.Sprites.redDoodleRightDescend);
        sprites[3] = this.getSprite(IRes.Sprites.redDoodleRightAscend);

        return sprites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getBlueDoodleSprites() {
        ISprite[] sprites = new ISprite[4];
        sprites[0] = this.getSprite(IRes.Sprites.blueDoodleLeftDescend);
        sprites[1] = this.getSprite(IRes.Sprites.blueDoodleLeftAscend);
        sprites[2] = this.getSprite(IRes.Sprites.blueDoodleRightDescend);
        sprites[3] = this.getSprite(IRes.Sprites.blueDoodleRightAscend);

        return sprites;
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
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite(final int index) {
        if (index < 1 || index > 4) {
            final String error = "The index of the platformBroken sprite must be between 1 and 4 (both inclusive)";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        switch (index) {
            case 1: return this.getSprite(IRes.Sprites.platformBroken1);
            case 2: return this.getSprite(IRes.Sprites.platformBroken2);
            case 3: return this.getSprite(IRes.Sprites.platformBroken3);
            case 4: return this.getSprite(IRes.Sprites.platformBroken4);
            default: return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPauseCoverSprite(PauseScreenModes mode) {
        switch (mode) {
            case mission:
                return this.getSprite(IRes.Sprites.pauseCover);
            case shop:
                return this.getSprite(IRes.Sprites.shopCover);
            default:
                final String error = "Trying to get the cover sprite of a mode that's not available";
                logger.error(error);
                throw new UnavailableLevelException(error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCoinSprite(final int digit) {
        if (digit < 1 || digit > 10) {
            throw new IllegalArgumentException("The coin animation sprites are between 1 and 10 (both inclusive)");
        }
        switch (digit) {
            case 1:
                return getSprite(IRes.Sprites.coin1);
            case 2:
                return getSprite(IRes.Sprites.coin2);
            case 3:
                return getSprite(IRes.Sprites.coin3);
            case 4:
                return getSprite(IRes.Sprites.coin4);
            case 5:
                return getSprite(IRes.Sprites.coin5);
            case 6:
                return getSprite(IRes.Sprites.coin6);
            case 7:
                return getSprite(IRes.Sprites.coin7);
            case 8:
                return getSprite(IRes.Sprites.coin8);
            case 9:
                return getSprite(IRes.Sprites.coin9);
            case 10:
                return getSprite(IRes.Sprites.coin10);
            default:
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPowerupSprite(final Powerups powerup, final int level) throws UnavailableLevelException {
        // TODO parameter checking
        switch (powerup) {
            case jetpack:
                return getJetpackSprite(level);
            case propeller:
                return getSprite(IRes.Sprites.propeller);
            case sizeDown:
                return getSprite(IRes.Sprites.sizeDown);
            case sizeUp:
                return getSprite(IRes.Sprites.sizeUp);
            case spring:
                return getSpringSprite(level);
            case springShoes:
                return getSprite(IRes.Sprites.springShoes);
            case trampoline:
                return getTrampolineSprite(level);
            default:
                logger.error("The sprite of powerup " + powerup.name() + " could not be found");
                return null;
        }
    }

    /**
     * Returns a sprite of the spring corresponding to the level of the object.
     *
     * @param level The level of the {@link objects.powerups.Spring spring} you want to have
     * @return A sprite of the spring of the requested level
     * @throws UnavailableLevelException Thrown when the level is either too low or too high
     */
    private ISprite getSpringSprite(final int level) throws UnavailableLevelException {
        switch (level) {
            case 1:
                return getSprite(IRes.Sprites.spring);
            case 2:
                return getSprite(IRes.Sprites.doubleSpring);
            case 3:
                return getSprite(IRes.Sprites.titaniumSpring);
            case 4:
                return getSprite(IRes.Sprites.titaniumSpring);
            default:
                final String error = "Trying to get a spring of a level that's not available: " + level;
                logger.error(error);
                throw new UnavailableLevelException(error);
        }
    }

    /**
     * Returns a sprite of the trampoline corresponding to the level of the object.
     *
     * @param level The level of the {@link objects.powerups.Trampoline trampoline} you want to have
     * @return A sprite of the trampoline of the requested level
     * @throws UnavailableLevelException Thrown when the level is either too low or too high
     */
    private ISprite getTrampolineSprite(final int level) throws UnavailableLevelException {
        switch (level) {
            case 1:
                return getSprite(IRes.Sprites.trampoline);
            case 2:
                return getSprite(IRes.Sprites.circusCannon);
            case 3:
                return getSprite(IRes.Sprites.rocketLauncher);
            case 4:
                return getSprite(IRes.Sprites.rocketLauncher);
            default:
                final String error = "Trying to get a trampoline of a level that's not available: " + level;
                logger.error(error);
                throw new UnavailableLevelException(error);
        }
    }

    /**
     * Returns a sprite of the jetpack corresponding to the level of the object.
     *
     * @param level The level of the {@link objects.powerups.Trampoline trampoline} you want to have
     * @return A sprite of the trampoline of the requested level
     * @throws UnavailableLevelException Thrown when the level is either too low or too high
     */
    private ISprite getJetpackSprite(final int level) throws UnavailableLevelException {
        switch (level) {
            case 1:
                return getSprite(IRes.Sprites.jetpack);
            case 2:
                return getSprite(IRes.Sprites.spaceRocket);
            case 3:
                return getSprite(IRes.Sprites.spaceRocket);
            case 4:
                return getSprite(IRes.Sprites.spaceRocket);
            default:
                final String error = "Trying to get a space rocket of a level that's not available: " + level;
                logger.error(error);
                throw new UnavailableLevelException(error);
        }
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

    /**
     * Thrown when the sprite is asked for a level that's either too low or too high.
     */
    private static final class UnavailableLevelException extends RuntimeException {

        /**
         * Creates a new UnavailableException.
         *
         * @param message The message describing what went wrong
         */
        private UnavailableLevelException(final String message) {
            super(message);
        }
    }
}
