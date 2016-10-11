package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import objects.doodles.DoodleBehavior.MovementBehavior;
import resources.IRes;
import system.IServiceLocator;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

/**
 * Standard implementation of the SpriteFactory. Used to load and get sprites.
 * <br>
 * Javadoc is not deemed necessary for all individual sprites to have a javadoc.
 */
@SuppressWarnings({"checkstyle:JavadocVariable", "checkstyle:JavadocType", "checkstyle:JavadocMethod"})
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
        logger = serviceLocator.getLoggerFactory().createLogger(SpriteFactory.class);

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
        assert sL != null;
        SpriteFactory.serviceLocator = sL;
        sL.provide(new SpriteFactory());
    }

    // Buttons

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getMenuButtonSprite() {
        return getSprite(IRes.Sprites.menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPauseButtonSprite() {
        return getSprite(IRes.Sprites.pause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlayButtonSprite() {
        return getSprite(IRes.Sprites.play);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getMultiplayerButtonSprite() {
        return getSprite(IRes.Sprites.multiplayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlayAgainButtonSprite() {
        return getSprite(IRes.Sprites.playAgain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getResumeButtonSprite() {
        return getSprite(IRes.Sprites.resume);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreButtonSprite() {
        return getSprite(IRes.Sprites.scoreButton);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getChooseModeButtonSprite() {
        return getSprite(IRes.Sprites.chooseMode);
    }

    // Covers

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getBackground() {
        return getSprite(IRes.Sprites.background);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPauseCoverSprite() {
        return getSprite(IRes.Sprites.pauseCover);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStartCoverSprite() {
        return getSprite(IRes.Sprites.startCover);
    }


    // Doodle

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getDoodleSprite(final MovementBehavior.Directions direction) {
        ISprite[] sprites = new ISprite[2];
        if (direction == MovementBehavior.Directions.Left) {
            sprites[0] = this.getSprite(IRes.Sprites.doodleLeftAscend);
            sprites[1] = this.getSprite(IRes.Sprites.doodleLeftDescend);
        } else { // Use Right by default
            sprites[0] = this.getSprite(IRes.Sprites.doodleRightAscend);
            sprites[1] = this.getSprite(IRes.Sprites.doodleRightDescend);
        }

        return sprites;
    }


    // Kill screen

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getGameOverSprite() {
        return getSprite(IRes.Sprites.gameOver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getKillScreenBottomSprite() {
        return getSprite(IRes.Sprites.killScreenBottom);
    }


    // Monsters

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite2() {
        return getSprite(IRes.Sprites.puddingMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite3() {
        return getSprite(IRes.Sprites.puddingMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite4() {
        return getSprite(IRes.Sprites.puddingMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite5() {
        return getSprite(IRes.Sprites.puddingMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTwinMonsterSprite() {
        return getSprite(IRes.Sprites.twinMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite1() {
        return getSprite(IRes.Sprites.threeEyedMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite2() {
        return getSprite(IRes.Sprites.threeEyedMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite3() {
        return getSprite(IRes.Sprites.threeEyedMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite4() {
        return getSprite(IRes.Sprites.threeEyedMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite5() {
        return getSprite(IRes.Sprites.threeEyedMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite1() {
        return getSprite(IRes.Sprites.vampireMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite2() {
        return getSprite(IRes.Sprites.vampireMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite3() {
        return getSprite(IRes.Sprites.vampireMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite4() {
        return getSprite(IRes.Sprites.vampireMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite5() {
        return getSprite(IRes.Sprites.vampireMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getOrdinaryMonsterSprite() {
        return getSprite(IRes.Sprites.ordinaryMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCactusMonster1Sprite() {
        return getSprite(IRes.Sprites.cactusMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCactusMonster2Sprite() {
        return getSprite(IRes.Sprites.cactusMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getFiveFeetMonsterSprite() {
        return getSprite(IRes.Sprites.fiveFeetMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getLowFiveFeetMonster1Sprite() {
        return getSprite(IRes.Sprites.lowFiveFeetMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getLowFiveFeetMonster2Sprite() {
        return getSprite(IRes.Sprites.lowFiveFeetMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSmallMonsterSprite() {
        return getSprite(IRes.Sprites.smallMonster);
    }


    // Numbers

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Override
    public ISprite getDigitSprite(final int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("A digit must be between 0 and 9 (inclusive)");
        }
        switch (digit) {
            case 0:
                return getSprite(IRes.Sprites.zero);
            case 1:
                return getSprite(IRes.Sprites.one);
            case 2:
                return getSprite(IRes.Sprites.two);
            case 3:
                return getSprite(IRes.Sprites.three);
            case 4:
                return getSprite(IRes.Sprites.four);
            case 5:
                return getSprite(IRes.Sprites.five);
            case 6:
                return getSprite(IRes.Sprites.six);
            case 7:
                return getSprite(IRes.Sprites.seven);
            case 8:
                return getSprite(IRes.Sprites.eight);
            case 9:
                return getSprite(IRes.Sprites.nine);
            default:
                return null;
        }
    }


    // Platform

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite1() {
        return getSprite(IRes.Sprites.platform1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSpriteHori() {
        return getSprite(IRes.Sprites.platformHorizontal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSpriteVert() {
        return getSprite(IRes.Sprites.platformVertical);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite4() {
        return getSprite(IRes.Sprites.platform4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite5() {
        return getSprite(IRes.Sprites.platform5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite6() {
        return getSprite(IRes.Sprites.platform6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite7() {
        return getSprite(IRes.Sprites.platform7);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite8() {
        return getSprite(IRes.Sprites.platform8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite9() {
        return getSprite(IRes.Sprites.platform9);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite1() {
        return getSprite(IRes.Sprites.platformBroken1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite2() {
        return getSprite(IRes.Sprites.platformBroken2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite3() {
        return getSprite(IRes.Sprites.platformBroken3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite4() {
        return getSprite(IRes.Sprites.platformBroken4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite1() {
        return getSprite(IRes.Sprites.platformExplosive1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite2() {
        return getSprite(IRes.Sprites.platformExplosive2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite3() {
        return getSprite(IRes.Sprites.platformExplosive3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovable1() {
        return getSprite(IRes.Sprites.platformMovable1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovable2() {
        return getSprite(IRes.Sprites.platformMovable2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovable3() {
        return getSprite(IRes.Sprites.platformMovable3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovable4() {
        return getSprite(IRes.Sprites.platformMovable4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShining1() {
        return getSprite(IRes.Sprites.platformShining1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShining2() {
        return getSprite(IRes.Sprites.platformShining2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShining3() {
        return getSprite(IRes.Sprites.platformShining3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite1() {
        return getSprite(IRes.Sprites.puddingMonster1);
    }


    // Powerups

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTrampolineSprite() {
        return getSprite(IRes.Sprites.trampoline);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTrampolineUsedSprite() {
        return getSprite(IRes.Sprites.trampolineUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringSprite() {
        return getSprite(IRes.Sprites.spring);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringUsedSprite() {
        return getSprite(IRes.Sprites.springUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringShoesSprite() {
        return getSprite(IRes.Sprites.springShoes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getJetpackSprite() {
        return getSprite(IRes.Sprites.jetpack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPropellerSprite() {
        return getSprite(IRes.Sprites.propeller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getShieldSprite() {
        return getSprite(IRes.Sprites.shield);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSizeUpSprite() {
        return getSprite(IRes.Sprites.sizeUp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSizeDownSprite() {
        return getSprite(IRes.Sprites.sizeDown);
    }

    // Misc

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getWaitDoNotShootSprite() {
        return getSprite(IRes.Sprites.waitDoNotShoot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getAvoidSprite() {
        return getSprite(IRes.Sprites.avoid);
    }


    // Score Screen

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenBottom() {
        return getSprite(IRes.Sprites.scoreScreenBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenLeft() {
        return getSprite(IRes.Sprites.scoreScreenLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenTop() {
        return getSprite(IRes.Sprites.scoreScreenTop);
    }


    // Top bar

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreBarSprite() {
        return getSprite(IRes.Sprites.scoreBar);
    }


    // UFO

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUFOSprite() {
        return getSprite(IRes.Sprites.ufo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUFOShiningSprite() {
        return getSprite(IRes.Sprites.ufoShining);
    }

    // Choose Mode Icons

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getRegularModeButton() {
        return getSprite(IRes.Sprites.regularMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStoryModeButton() {
        return getSprite(IRes.Sprites.storyMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getDarknessModeButton() {
        return getSprite(IRes.Sprites.darknessMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getInvertModeButton() {
        return getSprite(IRes.Sprites.invertMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUnderwaterModeButton() {
        return getSprite(IRes.Sprites.underwaterMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpaceModeButton() {
        return getSprite(IRes.Sprites.spaceMode);
    }

    // Miscellaneous

    /**
     * Loads an ISprite with the name {@code ISpriteName}.
     *
     * @param spriteName the enumerator defining the requested sprite
     * @return The {@link ISprite sprite} if it was found. null otherwise
     */
    private ISprite loadISprite(final IRes.Sprites spriteName) {
        String filepath = serviceLocator.getRes().getSpritePath(spriteName);
        BufferedImage image = null;
        try {
            image = serviceLocator.getFileSystem().readImage(filepath);
            logger.info("Sprite loaded: \"" + filepath + "\"");
            return new Sprite(getFileName(filepath), image);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * Return the requested sprite.
     *
     * @param sprite the enumerator defining the requested sprite.
     * @return the sprite.
     */
    private ISprite getSprite(final IRes.Sprites sprite) {
        try {
            return cache.get(sprite);
        } catch (ExecutionException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Returns the filename from a filepath.
     * <p>
     * Example:
     * <pre>
     * {@code
     *     getFileName("resources/Sprites/sprite.png").equals("sprite.png")
     * }
     * </pre>
     *
     * @param filepath The full path to the file, the directories separated by '('
     * @return The name of the file
     */
    private String getFileName(final String filepath) {
        int fileNameIndex = filepath.lastIndexOf('/') + 1;
        return filepath.substring(fileNameIndex);
    }

}
