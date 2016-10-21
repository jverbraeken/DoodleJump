package resources.sprites;

import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.powerups.Powerups;
import system.IFactory;

/**
 * Interface for a SpriteFactory.
 * <br>
 * It is not deemed necessary for all individual sprites to have a JavaDoc.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public interface ISpriteFactory extends IFactory {

    // Buttons
    ISprite getMenuButtonSprite();

    ISprite getPauseButtonSprite();

    ISprite getPlayButtonSprite();

    ISprite getMultiplayerButtonSprite();

    ISprite getPlayAgainButtonSprite();

    ISprite getResumeButtonSprite();

    ISprite getScoreButtonSprite();

    ISprite getChooseModeButtonSprite();

    ISprite getShopButtonSprite();
    // Covers
    ISprite getBackground();

    ISprite getPauseCoverSprite();

    ISprite getStartCoverSprite();

    // Doodle
    ISprite[] getDoodleLeftSprites();

    ISprite[] getDoodleRightSprites();

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

    ISprite getCactusMonsterSprite1(); // By default with three flaps at its Left and Right side and three eyes

    ISprite getCactusMonsterSprite2();

    ISprite getFiveFeetMonsterSprite(); // By default a blue pudding with 5 red feet

    ISprite getLowFiveFeetMonsterSprite1(); // By default a green very low monster with 5 feet

    ISprite getLowFiveFeetMonsterSprite2();

    ISprite getSmallMonsterSprite(); // By default a very small three eyed red monster

    // Stars
    ISprite getStarSprite1();

    ISprite getStarSprite2();

    ISprite getStarSprite3();

    // Platforms
    ISprite getPlatformSprite1();

    ISprite getPlatformSpriteHorizontal();

    ISprite getPlatformSpriteVertical();

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

    ISprite getPlatformMovableSprite1();

    ISprite getPlatformMovableSprite2();

    ISprite getPlatformMovableSprite3();

    ISprite getPlatformMovableSprite4();

    ISprite getPlatformShiningSprite1();

    ISprite getPlatformShiningSprite2();

    ISprite getPlatformShiningSprite3();

    // Powerups
    ISprite getPowerupSprite(final Powerups powerup, final int currentPowerupLevel);

    ISprite getTrampolineUsedSprite(final int currentPowerupLevel);

    ISprite getSpaceRocketSprite();

    ISprite getSpringUsedSprite(final int currentPowerupLevel);

    ISprite[] getJetpackActiveSprites();

    ISprite[] getPropellerActiveSprites();

    /**
     * Resturns a list with sprites when a doodle has collided with the doodle.
     * @return list of sprites.
     */
    ISprite[] getSpaceRocketActiveSprites();


    // Score screen
    ISprite getScoreScreenBottom();

    ISprite getScoreScreenLeft();

    ISprite getScoreScreenTop();

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

    // Coins
    ISprite getCoinSprite(final int i);

    ISprite[] getDigitSprites();

    // Missions

    ISprite getAchievementSprite();
}
