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
 * It is not deemed necessary for all individual sprites to have a JavaDoc.
 */
@SuppressWarnings({"checkstyle:JavadocVariable", "checkstyle:JavadocType", "checkstyle:JavadocMethod", "checkstyle:magicnumber"})
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
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
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


    // Buttons
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getMenuButtonSprite() {
        return this.getSprite(IRes.Sprites.menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPauseButtonSprite() {
        return this.getSprite(IRes.Sprites.pause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlayButtonSprite() {
        return this.getSprite(IRes.Sprites.play);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getMultiplayerButtonSprite() {
        return this.getSprite(IRes.Sprites.multiplayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlayAgainButtonSprite() {
        return this.getSprite(IRes.Sprites.playAgain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getResumeButtonSprite() {
        return this.getSprite(IRes.Sprites.resume);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreButtonSprite() {
        return this.getSprite(IRes.Sprites.scoreButton);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getChooseModeButtonSprite() {
        return this.getSprite(IRes.Sprites.chooseMode);
    }


    // Covers
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getBackground() {
        return this.getSprite(IRes.Sprites.background);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPauseCoverSprite() {
        return this.getSprite(IRes.Sprites.pauseCover);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStartCoverSprite() {
        return this.getSprite(IRes.Sprites.startCover);
    }


    // Doodle
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getDoodleLeftSprites() {
        ISprite[] sprites = new ISprite[2];
        sprites[0] = this.getSprite(IRes.Sprites.doodleLeftAscend);
        sprites[1] = this.getSprite(IRes.Sprites.doodleLeftDescend);

        return sprites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getDoodleRightSprites() {
        ISprite[] sprites = new ISprite[2];
        sprites[0] = this.getSprite(IRes.Sprites.doodleRightAscend);
        sprites[1] = this.getSprite(IRes.Sprites.doodleRightDescend);

        return sprites;
    }


    // Kill screen
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getGameOverSprite() {
        return this.getSprite(IRes.Sprites.gameOver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getKillScreenBottomSprite() {
        return this.getSprite(IRes.Sprites.killScreenBottom);
    }


    // Monsters
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite1() {
        return this.getSprite(IRes.Sprites.puddingMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite2() {
        return this.getSprite(IRes.Sprites.puddingMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite3() {
        return this.getSprite(IRes.Sprites.puddingMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite4() {
        return this.getSprite(IRes.Sprites.puddingMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPuddingMonsterSprite5() {
        return this.getSprite(IRes.Sprites.puddingMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTwinMonsterSprite() {
        return this.getSprite(IRes.Sprites.twinMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite1() {
        return this.getSprite(IRes.Sprites.threeEyedMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite2() {
        return this.getSprite(IRes.Sprites.threeEyedMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite3() {
        return this.getSprite(IRes.Sprites.threeEyedMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite4() {
        return this.getSprite(IRes.Sprites.threeEyedMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getThreeEyedMonsterSprite5() {
        return this.getSprite(IRes.Sprites.threeEyedMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite1() {
        return this.getSprite(IRes.Sprites.vampireMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite2() {
        return this.getSprite(IRes.Sprites.vampireMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite3() {
        return this.getSprite(IRes.Sprites.vampireMonster3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite4() {
        return this.getSprite(IRes.Sprites.vampireMonster4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getVampireMonsterSprite5() {
        return this.getSprite(IRes.Sprites.vampireMonster5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getOrdinaryMonsterSprite() {
        return this.getSprite(IRes.Sprites.ordinaryMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCactusMonsterSprite1() {
        return this.getSprite(IRes.Sprites.cactusMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCactusMonsterSprite2() {
        return this.getSprite(IRes.Sprites.cactusMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getFiveFeetMonsterSprite() {
        return this.getSprite(IRes.Sprites.fiveFeetMonster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getLowFiveFeetMonsterSprite1() {
        return this.getSprite(IRes.Sprites.lowFiveFeetMonster1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getLowFiveFeetMonsterSprite2() {
        return this.getSprite(IRes.Sprites.lowFiveFeetMonster2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSmallMonsterSprite() {
        return this.getSprite(IRes.Sprites.smallMonster);
    }


    // Stars
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStarSprite1() {
        return this.getSprite(IRes.Sprites.confusedStars1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStarSprite2() {
        return this.getSprite(IRes.Sprites.confusedStars2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStarSprite3() {
        return this.getSprite(IRes.Sprites.confusedStars3);
    }


    // Numbers
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getDigitSprites() {
        return new ISprite[] {
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


    // Platform
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite1() {
        return this.getSprite(IRes.Sprites.platform1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSpriteHorizontal() {
        return this.getSprite(IRes.Sprites.platformHorizontal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSpriteVertical() {
        return this.getSprite(IRes.Sprites.platformVertical);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite4() {
        return this.getSprite(IRes.Sprites.platform4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite5() {
        return this.getSprite(IRes.Sprites.platform5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite6() {
        return this.getSprite(IRes.Sprites.platform6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite7() {
        return this.getSprite(IRes.Sprites.platform7);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite8() {
        return this.getSprite(IRes.Sprites.platform8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformSprite9() {
        return this.getSprite(IRes.Sprites.platform9);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite1() {
        return this.getSprite(IRes.Sprites.platformBroken1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite2() {
        return this.getSprite(IRes.Sprites.platformBroken2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite3() {
        return this.getSprite(IRes.Sprites.platformBroken3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformBrokenSprite4() {
        return this.getSprite(IRes.Sprites.platformBroken4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite1() {
        return this.getSprite(IRes.Sprites.platformExplosive1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite2() {
        return this.getSprite(IRes.Sprites.platformExplosive2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformExplosiveSprite3() {
        return this.getSprite(IRes.Sprites.platformExplosive3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovableSprite1() {
        return this.getSprite(IRes.Sprites.platformMovable1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovableSprite2() {
        return this.getSprite(IRes.Sprites.platformMovable2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovableSprite3() {
        return this.getSprite(IRes.Sprites.platformMovable3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformMovableSprite4() {
        return this.getSprite(IRes.Sprites.platformMovable4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShiningSprite1() {
        return this.getSprite(IRes.Sprites.platformShining1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShiningSprite2() {
        return this.getSprite(IRes.Sprites.platformShining2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPlatformShiningSprite3() {
        return this.getSprite(IRes.Sprites.platformShining3);
    }


    // Powerups
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTrampolineSprite() {
        return this.getSprite(IRes.Sprites.trampoline);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getTrampolineUsedSprite() {
        return this.getSprite(IRes.Sprites.trampolineUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringSprite() {
        return this.getSprite(IRes.Sprites.spring);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringUsedSprite() {
        return this.getSprite(IRes.Sprites.springUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpringShoesSprite() {
        return this.getSprite(IRes.Sprites.springShoes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getJetpackSprite() {
        return this.getSprite(IRes.Sprites.jetpack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getPropellerSprite() {
        return this.getSprite(IRes.Sprites.propeller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getShieldSprite() {
        return this.getSprite(IRes.Sprites.shield);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSizeUpSprite() {
        return this.getSprite(IRes.Sprites.sizeUp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSizeDownSprite() {
        return this.getSprite(IRes.Sprites.sizeDown);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCannonSprite() {
        return getSprite(IRes.Sprites.cannon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getCannonUsedSprite() {
        return getSprite(IRes.Sprites.cannonUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getRocketLauncherSprite() {
        return getSprite(IRes.Sprites.rocketLauncher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getRocketLauncherUsedSprite() {
        return getSprite(IRes.Sprites.rocketLauncherUsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getJetpackActiveSprites() {
        ISprite[] sprites = new ISprite[9];
        sprites[0] = this.getSprite(IRes.Sprites.jetpack0);
        sprites[1] = this.getSprite(IRes.Sprites.jetpack1);
        sprites[2] = this.getSprite(IRes.Sprites.jetpack2);
        sprites[3] = this.getSprite(IRes.Sprites.jetpack3);
        sprites[4] = this.getSprite(IRes.Sprites.jetpack4);
        sprites[5] = this.getSprite(IRes.Sprites.jetpack5);
        sprites[6] = this.getSprite(IRes.Sprites.jetpack6);
        sprites[7] = this.getSprite(IRes.Sprites.jetpack7);
        sprites[8] = this.getSprite(IRes.Sprites.jetpack8);

        return sprites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite[] getPropellerActiveSprites() {
        ISprite[] sprites = new ISprite[4];
        sprites[0] = this.getSprite(IRes.Sprites.propeller0);
        sprites[1] = this.getSprite(IRes.Sprites.propeller1);
        sprites[2] = this.getSprite(IRes.Sprites.propeller0);
        sprites[3] = this.getSprite(IRes.Sprites.propeller2);

        return sprites;
    }


    // Misc
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getWaitDoNotShootSprite() {
        return this.getSprite(IRes.Sprites.waitDoNotShoot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getAvoidSprite() {
        return this.getSprite(IRes.Sprites.avoid);
    }




    // Score Screen
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenBottom() {
        return this.getSprite(IRes.Sprites.scoreScreenBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenLeft() {
        return this.getSprite(IRes.Sprites.scoreScreenLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreScreenTop() {
        return this.getSprite(IRes.Sprites.scoreScreenTop);
    }


    // Top bar
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getScoreBarSprite() {
        return this.getSprite(IRes.Sprites.scoreBar);
    }


    // UFO
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUFOSprite() {
        return this.getSprite(IRes.Sprites.ufo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUFOShiningSprite() {
        return this.getSprite(IRes.Sprites.ufoShining);
    }

    // Choose Mode Icons
    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getRegularModeButton() {
        return this.getSprite(IRes.Sprites.regularMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getStoryModeButton() {
        return this.getSprite(IRes.Sprites.storyMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getDarknessModeButton() {
        return this.getSprite(IRes.Sprites.darknessMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getInvertModeButton() {
        return this.getSprite(IRes.Sprites.invertMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getUnderwaterModeButton() {
        return this.getSprite(IRes.Sprites.underwaterMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSpaceModeButton() {
        return this.getSprite(IRes.Sprites.spaceMode);
    }

    // Missions

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getAchievementSprite() {
        return getSprite(IRes.Sprites.achievement);
    }

    // Coins

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
        }
        return null;
    }

    // Miscellaneous
    /**
     * Loads an ISprite with the name {@code ISpriteName}.
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
     * Return the requested sprite.
     * @param sprite the enumerator defining the requested sprite.
     * @return the sprite.
     */
    private ISprite getSprite(final IRes.Sprites sprite) {
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
     * </pre>
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
