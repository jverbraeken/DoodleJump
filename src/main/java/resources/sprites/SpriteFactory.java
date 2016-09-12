package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import resources.IRes;
import system.IServiceLocator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public final class SpriteFactory implements ISpriteFactory {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    LoadingCache<IRes.sprites, ISprite> cache;

    /**
     * Prevents instantiation from outside the class.
     */
    private SpriteFactory() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.sprites, ISprite>() {
                            @Override
                            public ISprite load(IRes.sprites sprite) throws FileNotFoundException {
                                return loadISprite(sprite);
                            }
                        }
                );
    }

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SpriteFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SpriteFactory());
    }

    @Override
    public ISprite getDoodleSprite() {
        return getSprite(IRes.sprites.doodle);
    }

    @Override
    public ISprite getPlatformSprite1() {
        return getSprite(IRes.sprites.platform1);
    }

    @Override
    public ISprite getPlatformSprite2() {
        return getSprite(IRes.sprites.platform2);
    }

    @Override
    public ISprite getPlatformSprite3() {
        return getSprite(IRes.sprites.platform3);
    }

    @Override
    public ISprite getPlatformSprite4() {
        return getSprite(IRes.sprites.platform4);
    }

    @Override
    public ISprite getPlatformSprite5() {
        return getSprite(IRes.sprites.platform5);
    }

    @Override
    public ISprite getPlatformSprite6() {
        return getSprite(IRes.sprites.platform6);
    }

    @Override
    public ISprite getPlatformSprite7() {
        return getSprite(IRes.sprites.platform7);
    }

    @Override
    public ISprite getPlatformSprite8() {
        return getSprite(IRes.sprites.platform8);
    }

    @Override
    public ISprite getPlatformSprite9() {
        return getSprite(IRes.sprites.platform9);
    }

    @Override
    public ISprite getPlatformBrokenSprite1() {
        return getSprite(IRes.sprites.platformBroken1);
    }

    @Override
    public ISprite getPlatformBrokenSprite2() {
        return getSprite(IRes.sprites.platformBroken2);
    }

    @Override
    public ISprite getPlatformBrokenSprite3() {
        return getSprite(IRes.sprites.platformBroken3);
    }

    @Override
    public ISprite getPlatformBrokenSprite4() {
        return getSprite(IRes.sprites.platformBroken4);
    }

    @Override
    public ISprite getPlatformExplosiveSprite1() {
        return getSprite(IRes.sprites.platformExplosive1);
    }

    @Override
    public ISprite getPlatformExplosiveSprite2() {
        return getSprite(IRes.sprites.platformExplosive2);
    }

    @Override
    public ISprite getPlatformExplosiveSprite3() {
        return getSprite(IRes.sprites.platformExplosive3);
    }

    @Override
    public ISprite getPlatformMovable1() {
        return getSprite(IRes.sprites.platformMovable1);
    }

    @Override
    public ISprite getPlatformMovable2() {
        return getSprite(IRes.sprites.platformMovable2);
    }

    @Override
    public ISprite getPlatformMovable3() {
        return getSprite(IRes.sprites.platformMovable3);
    }

    @Override
    public ISprite getPlatformMovable4() {
        return getSprite(IRes.sprites.platformMovable4);
    }

    @Override
    public ISprite getPlatformShining1() {
        return getSprite(IRes.sprites.platformShining1);
    }

    @Override
    public ISprite getPlatformShining2() {
        return getSprite(IRes.sprites.platformShining2);
    }

    @Override
    public ISprite getPlatformShining3() {
        return getSprite(IRes.sprites.platformShining3);
    }

    @Override
    public ISprite getPuddingMonsterSprite1() {
        return getSprite(IRes.sprites.puddingMonster1);
    }

    @Override
    public ISprite getPuddingMonsterSprite2() {
        return getSprite(IRes.sprites.puddingMonster2);
    }

    @Override
    public ISprite getPuddingMonsterSprite3() {
        return getSprite(IRes.sprites.puddingMonster3);
    }

    @Override
    public ISprite getPuddingMonsterSprite4() {
        return getSprite(IRes.sprites.puddingMonster4);
    }

    @Override
    public ISprite getPuddingMonsterSprite5() {
        return getSprite(IRes.sprites.puddingMonster5);
    }

    @Override
    public ISprite getTwinMonsterSprite() {
        return getSprite(IRes.sprites.twinMonster);
    }

    @Override
    public ISprite getThreeEyedMonsterSprite1() {
        return getSprite(IRes.sprites.threeEyedMonster1);
    }

    @Override
    public ISprite getThreeEyedMonsterSprite2() {
        return getSprite(IRes.sprites.threeEyedMonster2);
    }

    @Override
    public ISprite getThreeEyedMonsterSprite3() {
        return getSprite(IRes.sprites.threeEyedMonster3);
    }

    @Override
    public ISprite getThreeEyedMonsterSprite4() {
        return getSprite(IRes.sprites.threeEyedMonster4);
    }

    @Override
    public ISprite getThreeEyedMonsterSprite5() {
        return getSprite(IRes.sprites.threeEyedMonster5);
    }

    @Override
    public ISprite getVampireMonsterSprite1() {
        return getSprite(IRes.sprites.vampireMonster1);
    }

    @Override
    public ISprite getVampireMonsterSprite2() {
        return getSprite(IRes.sprites.vampireMonster2);
    }

    @Override
    public ISprite getVampireMonsterSprite3() {
        return getSprite(IRes.sprites.vampireMonster3);
    }

    @Override
    public ISprite getVampireMonsterSprite4() {
        return getSprite(IRes.sprites.vampireMonster4);
    }

    @Override
    public ISprite getVampireMonsterSprite5() {
        return getSprite(IRes.sprites.vampireMonster5);
    }

    @Override
    public ISprite getOrdinaryMonsterSprite() {
        return getSprite(IRes.sprites.ordinaryMonster);
    }

    @Override
    public ISprite getCactusMonster1Sprite() {
        return getSprite(IRes.sprites.cactusMonster1);
    }

    @Override
    public ISprite getCactusMonster2Sprite() {
        return getSprite(IRes.sprites.cactusMonster2);
    }

    @Override
    public ISprite getFiveFeetMonsterSprite() {
        return getSprite(IRes.sprites.fiveFeetMonster);
    }

    @Override
    public ISprite getLowFiveFeetMonster1Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster1);
    }

    @Override
    public ISprite getLowFiveFeetMonster2Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster2);
    }

    @Override
    public ISprite getSmallMonsterSprite() {
        return getSprite(IRes.sprites.smallMonster);
    }

    @Override
    public ISprite getUFOSprite() {
        return getSprite(IRes.sprites.ufo);
    }

    @Override
    public ISprite getUFOShiningSprite() {
        return getSprite(IRes.sprites.ufoShining);
    }

    @Override
    public ISprite getTrampolineSprite() {
        return getSprite(IRes.sprites.trampoline);
    }

    @Override
    public ISprite getSpringSprite() {
        return getSprite(IRes.sprites.spring);
    }

    @Override
    public ISprite getRocketSprite() {
        return getSprite(IRes.sprites.rocket);
    }

    @Override
    public ISprite getCapSprite() {
        return getSprite(IRes.sprites.cap);
    }

    @Override
    public ISprite getShieldSprite() {
        return getSprite(IRes.sprites.shield);
    }

    @Override
    public ISprite getWaitDontShootSprite() {
        return getSprite(IRes.sprites.waitDontShoot);
    }

    @Override
    public ISprite getAvoidSprite() {
        return getSprite(IRes.sprites.avoid);
    }

    // Buttons
    @Override
    public ISprite getPlayButtonSprite() { return getSprite(IRes.sprites.playButton); }

    // Backgrounds
    @Override
    public ISprite getStartMenuBackground() { return getSprite(IRes.sprites.background); }

    // Miscellaneous
    private ISprite getSprite(IRes.sprites sprite) {
        try {
            return cache.get(sprite);
        } catch (ExecutionException e) {
            // TODO use e.getCause() and log that
            e.printStackTrace();
        }
        return null;
    }

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
     * Returns the filename from a filepath.
     *
     * Example:
     * <pre>
     *     {@code
     *     getFileName("resources/sprites/sprite.png").equals("sprite.png")
     *     }
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
