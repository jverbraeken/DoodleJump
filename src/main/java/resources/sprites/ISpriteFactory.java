package resources.sprites;

import objects.doodles.IDoodle;
import system.IFactory;

public interface ISpriteFactory extends IFactory {

    // Doodle
    ISprite[] getDoodleSprite(IDoodle.directions direction);

    // Platforms
    ISprite getPlatformSprite1();
    ISprite getPlatformSprite2();
    ISprite getPlatformSprite3();
    ISprite getPlatformSprite4();
    ISprite getPlatformSprite5();
    ISprite getPlatformSprite6();
    ISprite getPlatformSprite7();
    ISprite getPlatformSprite8();
    ISprite getPlatformSprite9();
    ISprite getPlatformBrokenSprite1();
    ISprite getPlatformBrokenSprite2();
    ISprite getPlatformBrokenSprite3();
    ISprite getPlatformBrokenSprite4();
    ISprite getPlatformExplosiveSprite1();
    ISprite getPlatformExplosiveSprite2();
    ISprite getPlatformExplosiveSprite3();
    ISprite getPlatformMovable1();
    ISprite getPlatformMovable2();
    ISprite getPlatformMovable3();
    ISprite getPlatformMovable4();
    ISprite getPlatformShining1();
    ISprite getPlatformShining2();
    ISprite getPlatformShining3();

    // Monsters
    ISprite getPuddingMonsterSprite1(); // By default the green monster with some blood
    ISprite getPuddingMonsterSprite2();
    ISprite getPuddingMonsterSprite3();
    ISprite getPuddingMonsterSprite4();
    ISprite getPuddingMonsterSprite5();
    ISprite getTwinMonsterSprite(); // By default the blue twin
    ISprite getThreeEyedMonsterSprite1(); // By default three yellow eyes
    ISprite getThreeEyedMonsterSprite2();
    ISprite getThreeEyedMonsterSprite3();
    ISprite getThreeEyedMonsterSprite4();
    ISprite getThreeEyedMonsterSprite5();
    ISprite getVampireMonsterSprite1(); // By default a blue monster with 2 red teeth
    ISprite getVampireMonsterSprite2();
    ISprite getVampireMonsterSprite3();
    ISprite getVampireMonsterSprite4();
    ISprite getVampireMonsterSprite5();
    ISprite getOrdinaryMonsterSprite(); // By default the purple monster with a blank head
    ISprite getCactusMonster1Sprite(); // By default with three flaps at its left and right side and three eyes
    ISprite getCactusMonster2Sprite();
    ISprite getFiveFeetMonsterSprite(); // By default a blue pudding with 5 red feet
    ISprite getLowFiveFeetMonster1Sprite(); // By default a green very low monster with 5 feet
    ISprite getLowFiveFeetMonster2Sprite();
    ISprite getSmallMonsterSprite(); // By default a very small three eyed red monster

    // UFO
    ISprite getUFOSprite();
    ISprite getUFOShiningSprite();

    // Powerups
    ISprite getTrampolineSprite();
    ISprite getTrampolineUsedSprite();
    ISprite getSpringSprite();
    ISprite getSpringUsedSprite();
    ISprite getRocketSprite();
    ISprite getPropellerSprite();
    ISprite getShieldSprite();

    // Text
    ISprite getWaitDontShootSprite();
    ISprite getAvoidSprite();

    // Buttons
    ISprite getPlayButtonSprite();

    // Background
    ISprite getStartMenuBackgroundSprite();

    // Top bar
    ISprite getScorebarSprite();
    ISprite getPauseSprite();

    // Pause
    ISprite getPauseCoverSprite();
    ISprite getResumeButtonSprite();

    /**
     * Returns a sprite of the digit specified in Doodle Jump font.
     * @param digit Between 0 and 10
     * @return A sprite expressing the digit
     */
    ISprite getDigitSprite(int digit);
}
