package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import objects.doodles.IDoodle;
import resources.IRes;
import system.IServiceLocator;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

/**
 * Standard implementation of the SpriteFactory. Used to load and get sprites.
 */
public final class SpriteFactory implements ISpriteFactory {

    /**
     * The logger for the SpriteFactory class.
     */
    private final ILogger LOGGER;

    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SpriteFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SpriteFactory());
    }

    /**
     * The cache for the SpriteFactory.
     */
    private LoadingCache<IRes.sprites, ISprite> cache;

    /**
     * Prevents instantiation from outside the class.
     */
    private SpriteFactory() {
        LOGGER = serviceLocator.getLoggerFactory().createLogger(SpriteFactory.class);

        cache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.sprites, ISprite>() {
                            @Override
                            public ISprite load(IRes.sprites sprite) throws FileNotFoundException {
                                LOGGER.info("Sprite loaded: \"" + sprite + "\"");
                                return loadISprite(sprite);
                            }
                        }
                );
    }

    // Buttons
    /** {@inheritDoc} */
    @Override
    public ISprite getMenuButtonSprite() { return getSprite(IRes.sprites.menu); }

    /** {@inheritDoc} */
    @Override
    public ISprite getPauseButtonSprite() {
        return getSprite(IRes.sprites.pause);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlayButtonSprite() { return getSprite(IRes.sprites.play); }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlayAgainButtonSprite() { return getSprite(IRes.sprites.playagain); }

    /** {@inheritDoc} */
    @Override
    public ISprite getResumeButtonSprite() {
        return getSprite(IRes.sprites.resume);
    }


    // Covers
    /** {@inheritDoc} */
    @Override
    public ISprite getBackground() { return getSprite(IRes.sprites.background); }

    /** {@inheritDoc} */
    @Override
    public ISprite getPauseCoverSprite() { return getSprite(IRes.sprites.pauseCover); }

    /** {@inheritDoc} */
    @Override
    public ISprite getStartCoverSprite() { return getSprite(IRes.sprites.startCover); }


    // Doodle
    /** {@inheritDoc} */
    @Override
    public ISprite[] getDoodleSprite(IDoodle.directions direction) {
        ISprite[] sprites = new ISprite[2];
        if (direction == IDoodle.directions.left) {
            sprites[0] = this.getSprite(IRes.sprites.doodleLeftAscend);
            sprites[1] = this.getSprite(IRes.sprites.doodleLeftDescend);
        } else { // Use Right by default
            sprites[0] = this.getSprite(IRes.sprites.doodleRightAscend);
            sprites[1] = this.getSprite(IRes.sprites.doodleRightDescend);
        }

        return sprites;
    }


    // Kill screen
    /** {@inheritDoc} */
    @Override
    public ISprite getGameOverSprite() { return getSprite(IRes.sprites.gameOver); }

    /** {@inheritDoc} */
    @Override
    public ISprite getKillScreenBottomSprite() { return getSprite(IRes.sprites.killScreenBottom); }


    // Monsters
    /** {@inheritDoc} */
    @Override
    public ISprite getPuddingMonsterSprite2() {
        return getSprite(IRes.sprites.puddingMonster2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPuddingMonsterSprite3() {
        return getSprite(IRes.sprites.puddingMonster3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPuddingMonsterSprite4() {
        return getSprite(IRes.sprites.puddingMonster4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPuddingMonsterSprite5() {
        return getSprite(IRes.sprites.puddingMonster5);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getTwinMonsterSprite() {
        return getSprite(IRes.sprites.twinMonster);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getThreeEyedMonsterSprite1() {
        return getSprite(IRes.sprites.threeEyedMonster1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getThreeEyedMonsterSprite2() {
        return getSprite(IRes.sprites.threeEyedMonster2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getThreeEyedMonsterSprite3() {
        return getSprite(IRes.sprites.threeEyedMonster3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getThreeEyedMonsterSprite4() {
        return getSprite(IRes.sprites.threeEyedMonster4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getThreeEyedMonsterSprite5() {
        return getSprite(IRes.sprites.threeEyedMonster5);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getVampireMonsterSprite1() {
        return getSprite(IRes.sprites.vampireMonster1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getVampireMonsterSprite2() {
        return getSprite(IRes.sprites.vampireMonster2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getVampireMonsterSprite3() {
        return getSprite(IRes.sprites.vampireMonster3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getVampireMonsterSprite4() {
        return getSprite(IRes.sprites.vampireMonster4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getVampireMonsterSprite5() {
        return getSprite(IRes.sprites.vampireMonster5);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getOrdinaryMonsterSprite() {
        return getSprite(IRes.sprites.ordinaryMonster);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getCactusMonster1Sprite() {
        return getSprite(IRes.sprites.cactusMonster1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getCactusMonster2Sprite() {
        return getSprite(IRes.sprites.cactusMonster2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getFiveFeetMonsterSprite() {
        return getSprite(IRes.sprites.fiveFeetMonster);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getLowFiveFeetMonster1Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getLowFiveFeetMonster2Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getSmallMonsterSprite() {
        return getSprite(IRes.sprites.smallMonster);
    }


    // Numbers
    /** {@inheritDoc} */
    @Override
    public ISprite getDigitSprite(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("A digit must be between 0 and 9 (inclusive)");
        }
        switch (digit) {
            case 0: return getSprite(IRes.sprites.zero);
            case 1: return getSprite(IRes.sprites.one);
            case 2: return getSprite(IRes.sprites.two);
            case 3: return getSprite(IRes.sprites.three);
            case 4: return getSprite(IRes.sprites.four);
            case 5: return getSprite(IRes.sprites.five);
            case 6: return getSprite(IRes.sprites.six);
            case 7: return getSprite(IRes.sprites.seven);
            case 8: return getSprite(IRes.sprites.eight);
            case 9: return getSprite(IRes.sprites.nine);
            default: return null;
        }
    }


    // Platform
    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite1() {
        return getSprite(IRes.sprites.platform1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite2() {
        return getSprite(IRes.sprites.platform2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite3() {
        return getSprite(IRes.sprites.platform3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite4() {
        return getSprite(IRes.sprites.platform4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite5() {
        return getSprite(IRes.sprites.platform5);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite6() {
        return getSprite(IRes.sprites.platform6);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite7() {
        return getSprite(IRes.sprites.platform7);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite8() {
        return getSprite(IRes.sprites.platform8);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformSprite9() {
        return getSprite(IRes.sprites.platform9);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformBrokenSprite1() {
        return getSprite(IRes.sprites.platformBroken1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformBrokenSprite2() {
        return getSprite(IRes.sprites.platformBroken2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformBrokenSprite3() {
        return getSprite(IRes.sprites.platformBroken3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformBrokenSprite4() {
        return getSprite(IRes.sprites.platformBroken4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformExplosiveSprite1() {
        return getSprite(IRes.sprites.platformExplosive1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformExplosiveSprite2() {
        return getSprite(IRes.sprites.platformExplosive2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformExplosiveSprite3() {
        return getSprite(IRes.sprites.platformExplosive3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformMovable1() {
        return getSprite(IRes.sprites.platformMovable1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformMovable2() {
        return getSprite(IRes.sprites.platformMovable2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformMovable3() {
        return getSprite(IRes.sprites.platformMovable3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformMovable4() {
        return getSprite(IRes.sprites.platformMovable4);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformShining1() {
        return getSprite(IRes.sprites.platformShining1);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformShining2() {
        return getSprite(IRes.sprites.platformShining2);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPlatformShining3() {
        return getSprite(IRes.sprites.platformShining3);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPuddingMonsterSprite1() {
        return getSprite(IRes.sprites.puddingMonster1);
    }


    // Powerups
    /** {@inheritDoc} */
    @Override
    public ISprite getTrampolineSprite() {
        return getSprite(IRes.sprites.trampoline);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getTrampolineUsedSprite() {
        return getSprite(IRes.sprites.trampolineUsed);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getSpringSprite() {
        return getSprite(IRes.sprites.spring);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getSpringUsedSprite() {
        return getSprite(IRes.sprites.springUsed);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getRocketSprite() {
        return getSprite(IRes.sprites.rocket);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getPropellerSprite() {
        return getSprite(IRes.sprites.propeller);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getShieldSprite() {
        return getSprite(IRes.sprites.shield);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getWaitDontShootSprite() {
        return getSprite(IRes.sprites.waitDontShoot);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getAvoidSprite() {
        return getSprite(IRes.sprites.avoid);
    }


    // Top bar
    /** {@inheritDoc} */
    @Override
    public ISprite getScorebarSprite() {
        return getSprite(IRes.sprites.scorebar);
    }


    // UFO
    /** {@inheritDoc} */
    @Override
    public ISprite getUFOSprite() {
        return getSprite(IRes.sprites.ufo);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getUFOShiningSprite() {
        return getSprite(IRes.sprites.ufoShining);
    }


    // Miscellaneous
    /**
     * Loads an ISprite with the name {@code ISpriteName}
     *
     * @return The ISprite
     * @throws FileNotFoundException Thrown when the ISprite was not found
     */
    private ISprite loadISprite(IRes.sprites spriteName) throws FileNotFoundException {
        String filepath = serviceLocator.getRes().getSpritePath(spriteName);
        BufferedImage image = serviceLocator.getFileSystem().readImage(filepath);
        return new Sprite(getFileName(filepath), image);
    }

    /**
     * Get a sprite from the cache or load the sprite.
     *
     * @param sprite The sprites name.
     * @return The sprite.
     */
    private ISprite getSprite(IRes.sprites sprite) {
        try {
            return cache.get(sprite);
        } catch (ExecutionException e) {
            LOGGER.error(e);
        }

        return null;
    }

    /**
     * Returns the filename from a filepath.
     *
     * Example:
     * <pre>
     * {@code
     *     getFileName("resources/sprites/sprite.png").equals("sprite.png")
     * }
     * </pre>
     *
     * @param filepath The full path to the file, the directories seperated by '('
     * @return The name of the file
     */
    private String getFileName(final String filepath) {
        int fileNameIndex = filepath.lastIndexOf('/') + 1;
        return filepath.substring(fileNameIndex);
    }

}
