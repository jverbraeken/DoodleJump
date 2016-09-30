package resources.sprites;

import objects.doodles.DoodleBehavior.MovementBehavior;
import system.IFactory;

/**
 * Interface for a SpriteFactory.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public interface ISpriteFactory extends IFactory {

    // Buttons
    ISprite getMenuButtonSprite();

    ISprite getPauseButtonSprite();

    ISprite getPlayButtonSprite();

    ISprite getPlayAgainButtonSprite();

    ISprite getResumeButtonSprite();

    ISprite getChooseModeButtonSprite();

    // Covers
    ISprite getBackground();

    ISprite getPauseCoverSprite();

    ISprite getStartCoverSprite();

    // Doodle
    ISprite[] getDoodleSprite(final MovementBehavior.Directions direction);

    // Kill screen
    ISprite getGameOverSprite();

    ISprite getKillScreenBottomSprite();

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

    ISprite getCactusMonster1Sprite(); // By default with three flaps at its Left and Right side and three eyes

    ISprite getCactusMonster2Sprite();

    ISprite getFiveFeetMonsterSprite(); // By default a blue pudding with 5 red feet

    ISprite getLowFiveFeetMonster1Sprite(); // By default a green very low monster with 5 feet

    ISprite getLowFiveFeetMonster2Sprite();

    ISprite getSmallMonsterSprite(); // By default a very small three eyed red monster

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

    // Powerups
    ISprite getTrampolineSprite();

    ISprite getTrampolineUsedSprite();

    ISprite getSpringSprite();

    ISprite getSpringUsedSprite();

    ISprite getSpringShoesSprite();

    ISprite getJetpackSprite();

    ISprite getPropellerSprite();

    ISprite getShieldSprite();

    ISprite getSizeUpSprite();

    // Text
    ISprite getWaitDoNotShootSprite();

    ISprite getAvoidSprite();

    // Top bar
    ISprite getScoreBarSprite();

    // UFO
    ISprite getUFOSprite();

    ISprite getUFOShiningSprite();

    // Choose mode icons
    ISprite getRegularModeButton();

    ISprite getStoryModeButton();

    ISprite getDarknessModeButton();

    ISprite getInvertModeButton();

    ISprite getSpaceModeButton();

    ISprite getUnderwaterModeButton();

    /**
     * Returns a sprite of the digit specified in Doodle Jump font.
     *
     * @param digit Between 0 and 10
     * @return A sprite expressing the digit
     */
    ISprite getDigitSprite(int digit);

}
