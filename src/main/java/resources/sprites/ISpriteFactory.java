package resources.sprites;

import java.awt.*;

/**
 * Created by joost on 6-9-16.
 */
public interface ISpriteFactory {

    // Doodle
    Image getDoodleSprite();

    // Platforms
    Image getPlatformSprite1();
    Image getPlatformSprite2();
    Image getPlatformSprite3();
    Image getPlatformSprite4();
    Image getPlatformSprite5();
    Image getPlatformSprite6();
    Image getPlatformSprite7();
    Image getPlatformSprite8();
    Image getPlatformSprite9();
    Image getPlatformBrokenSprite1();
    Image getPlatformBrokenSprite2();
    Image getPlatformBrokenSprite3();
    Image getPlatformBrokenSprite4();
    Image getPlatformExplosiveSprite1();
    Image getPlatformExplosiveSprite2();
    Image getPlatformExplosiveSprite3();
    Image getPlatformMovable1();
    Image getPlatformMovable2();
    Image getPlatformMovable3();
    Image getPlatformMovable4();
    Image getPlatformShining1();
    Image getPlatformShining2();
    Image getPlatformShining3();

    // Monsters
    Image getPuddingMonsterSprite1(); // By default the green monster with some blood
    Image getPuddingMonsterSprite2();
    Image getPuddingMonsterSprite3();
    Image getPuddingMonsterSprite4();
    Image getPuddingMonsterSprite5();
    Image getTwinMonsterSprite(); // By default the blue twin
    Image getThreeEyedMonsterSprite1(); // By default three yellow eyes
    Image getThreeEyedMonsterSprite2();
    Image getThreeEyedMonsterSprite3();
    Image getThreeEyedMonsterSprite4();
    Image getThreeEyedMonsterSprite5();
    Image getVampireMonsterSprite1(); // By default a blue monster with 2 red teeth
    Image getVampireMonsterSprite2();
    Image getVampireMonsterSprite3();
    Image getVampireMonsterSprite4();
    Image getVampireMonsterSprite5();
    Image getOrdinaryMonsterSprite(); // By default the purple monster with a blank head
    Image getCactusMonster1Sprite(); // By default with three flaps at its left and right side and three eyes
    Image getCactusMonster2Sprite();
    Image getFiveFeetMonsterSprite(); // By default a blue pudding with 5 red feet
    Image getLowFiveFeetMonster1Sprite(); // By default a green very low monster with 5 feet
    Image getLowFiveFeetMonster2Sprite();
    Image getSmallMonsterSprite(); // By default a very small three eyed red monster
    Image getUFOSprite();
    Image getUFOShiningSprite();

    // Tools
    Image getTrampolineSprite();
    Image getSpringSprite();
    Image getRocketSprite();
    Image getCapSprite();
    Image getShieldSprite();

    // Text
    Image getWaitDontShootSprite();
    Image getAvoidSprite();

    // Buttons
    Image getPlayButton();

    // Background
    Image getStartMenuBackground();

}
