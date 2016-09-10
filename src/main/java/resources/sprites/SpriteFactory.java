package resources.sprites;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import resources.IRes;
import system.IServiceLocator;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public final class SpriteFactory implements ISpriteFactory {

    private static transient IServiceLocator serviceLocator;
    LoadingCache<IRes.sprites, Image> cache;

    private SpriteFactory() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<IRes.sprites, Image>() {
                            @Override
                            public Image load(IRes.sprites sprite) throws FileNotFoundException {
                                return loadImage(sprite);
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
    public Image getDoodleSprite() {
        return getSprite(IRes.sprites.doodle);
    }

    @Override
    public Image getPlatformSprite1() {
        return getSprite(IRes.sprites.platform1);
    }

    @Override
    public Image getPlatformSprite2() {
        return getSprite(IRes.sprites.platform2);
    }

    @Override
    public Image getPlatformSprite3() {
        return getSprite(IRes.sprites.platform3);
    }

    @Override
    public Image getPlatformSprite4() {
        return getSprite(IRes.sprites.platform4);
    }

    @Override
    public Image getPlatformSprite5() {
        return getSprite(IRes.sprites.platform5);
    }

    @Override
    public Image getPlatformSprite6() {
        return getSprite(IRes.sprites.platform6);
    }

    @Override
    public Image getPlatformSprite7() {
        return getSprite(IRes.sprites.platform7);
    }

    @Override
    public Image getPlatformSprite8() {
        return getSprite(IRes.sprites.platform8);
    }

    @Override
    public Image getPlatformSprite9() {
        return getSprite(IRes.sprites.platform9);
    }

    @Override
    public Image getPlatformBrokenSprite1() {
        return getSprite(IRes.sprites.platformBroken1);
    }

    @Override
    public Image getPlatformBrokenSprite2() {
        return getSprite(IRes.sprites.platformBroken2);
    }

    @Override
    public Image getPlatformBrokenSprite3() {
        return getSprite(IRes.sprites.platformBroken3);
    }

    @Override
    public Image getPlatformBrokenSprite4() {
        return getSprite(IRes.sprites.platformBroken4);
    }

    @Override
    public Image getPlatformExplosiveSprite1() {
        return getSprite(IRes.sprites.platformExplosive1);
    }

    @Override
    public Image getPlatformExplosiveSprite2() {
        return getSprite(IRes.sprites.platformExplosive2);
    }

    @Override
    public Image getPlatformExplosiveSprite3() {
        return getSprite(IRes.sprites.platformExplosive3);
    }

    @Override
    public Image getPlatformMovable1() {
        return getSprite(IRes.sprites.platformMovable1);
    }

    @Override
    public Image getPlatformMovable2() {
        return getSprite(IRes.sprites.platformMovable2);
    }

    @Override
    public Image getPlatformMovable3() {
        return getSprite(IRes.sprites.platformMovable3);
    }

    @Override
    public Image getPlatformMovable4() {
        return getSprite(IRes.sprites.platformMovable4);
    }

    @Override
    public Image getPlatformShining1() {
        return getSprite(IRes.sprites.platformShining1);
    }

    @Override
    public Image getPlatformShining2() {
        return getSprite(IRes.sprites.platformShining2);
    }

    @Override
    public Image getPlatformShining3() {
        return getSprite(IRes.sprites.platformShining3);
    }

    @Override
    public Image getPuddingMonsterSprite1() {
        return getSprite(IRes.sprites.puddingMonster1);
    }

    @Override
    public Image getPuddingMonsterSprite2() {
        return getSprite(IRes.sprites.puddingMonster2);
    }

    @Override
    public Image getPuddingMonsterSprite3() {
        return getSprite(IRes.sprites.puddingMonster3);
    }

    @Override
    public Image getPuddingMonsterSprite4() {
        return getSprite(IRes.sprites.puddingMonster4);
    }

    @Override
    public Image getPuddingMonsterSprite5() {
        return getSprite(IRes.sprites.puddingMonster5);
    }

    @Override
    public Image getTwinMonsterSprite() {
        return getSprite(IRes.sprites.twinMonster);
    }

    @Override
    public Image getThreeEyedMonsterSprite1() {
        return getSprite(IRes.sprites.threeEyedMonster1);
    }

    @Override
    public Image getThreeEyedMonsterSprite2() {
        return getSprite(IRes.sprites.threeEyedMonster2);
    }

    @Override
    public Image getThreeEyedMonsterSprite3() {
        return getSprite(IRes.sprites.threeEyedMonster3);
    }

    @Override
    public Image getThreeEyedMonsterSprite4() {
        return getSprite(IRes.sprites.threeEyedMonster4);
    }

    @Override
    public Image getThreeEyedMonsterSprite5() {
        return getSprite(IRes.sprites.threeEyedMonster5);
    }

    @Override
    public Image getVampireMonsterSprite1() {
        return getSprite(IRes.sprites.vampireMonster1);
    }

    @Override
    public Image getVampireMonsterSprite2() {
        return getSprite(IRes.sprites.vampireMonster2);
    }

    @Override
    public Image getVampireMonsterSprite3() {
        return getSprite(IRes.sprites.vampireMonster3);
    }

    @Override
    public Image getVampireMonsterSprite4() {
        return getSprite(IRes.sprites.vampireMonster4);
    }

    @Override
    public Image getVampireMonsterSprite5() {
        return getSprite(IRes.sprites.vampireMonster5);
    }

    @Override
    public Image getOrdinaryMonsterSprite() {
        return getSprite(IRes.sprites.ordinaryMonster);
    }

    @Override
    public Image getCactusMonster1Sprite() {
        return getSprite(IRes.sprites.cactusMonster1);
    }

    @Override
    public Image getCactusMonster2Sprite() {
        return getSprite(IRes.sprites.cactusMonster2);
    }

    @Override
    public Image getFiveFeetMonsterSprite() {
        return getSprite(IRes.sprites.fiveFeetMonster);
    }

    @Override
    public Image getLowFiveFeetMonster1Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster1);
    }

    @Override
    public Image getLowFiveFeetMonster2Sprite() {
        return getSprite(IRes.sprites.lowFiveFeetMonster2);
    }

    @Override
    public Image getSmallMonsterSprite() {
        return getSprite(IRes.sprites.smallMonster);
    }

    @Override
    public Image getUFOSprite() {
        return getSprite(IRes.sprites.ufo);
    }

    @Override
    public Image getUFOShiningSprite() {
        return getSprite(IRes.sprites.ufoShining);
    }

    @Override
    public Image getTrampolineSprite() {
        return getSprite(IRes.sprites.trampoline);
    }

    @Override
    public Image getSpringSprite() {
        return getSprite(IRes.sprites.spring);
    }

    @Override
    public Image getRocketSprite() {
        return getSprite(IRes.sprites.rocket);
    }

    @Override
    public Image getCapSprite() {
        return getSprite(IRes.sprites.cap);
    }

    @Override
    public Image getShieldSprite() {
        return getSprite(IRes.sprites.shield);
    }

    @Override
    public Image getWaitDontShootSprite() {
        return getSprite(IRes.sprites.waitDontShoot);
    }

    @Override
    public Image getAvoidSprite() {
        return getSprite(IRes.sprites.avoid);
    }

    // Buttons
    @Override
    public Image getPlayButton() { return getSprite(IRes.sprites.playButton); }

    // Backgrounds
    @Override
    public Image getStartMenuBackground() { return getSprite(IRes.sprites.background); }

    // Miscellaneous
    private Image getSprite(IRes.sprites sprite) {
        try {
            return cache.get(sprite);
        } catch (ExecutionException e) {
            // TODO use e.getCause() and log that
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads an image with the name {@code imageName}
     *
     * @return The image
     * @throws FileNotFoundException Thrown when the image was not found
     */
    private Image loadImage(IRes.sprites sprite) throws FileNotFoundException {
        String filepath = serviceLocator.getRes().getSpritePath(sprite);
        return serviceLocator.getFileSystem().readImage(filepath);
    }
}
