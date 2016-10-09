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

    ISprite getScoreButtonSprite();

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

    /**
     * Creates a pudding monster and returns it.
     * @return the ISprite of pudding monster 1
     */
    ISprite getPuddingMonsterSprite1(); // By default the green monster with some blood

    /**
     * Creates a pudding monster and returns it.
     * @return the ISprite of pudding monster 2
     */
    ISprite getPuddingMonsterSprite2();

    /**
     * Creates a pudding monster and returns it.
     * @return the ISprite of pudding monster 3
     */
    ISprite getPuddingMonsterSprite3();

    /**
     * Creates a pudding monster and returns it.
     * @return the ISprite of pudding monster 4
     */
    ISprite getPuddingMonsterSprite4();

    /**
     * Creates a pudding monster and returns it.
     * @return the ISprite of pudding monster 5
     */
    ISprite getPuddingMonsterSprite5();

    /**
     * Creates a twin monster and returns it.
     * @return the ISprite of twin monster
     */
    ISprite getTwinMonsterSprite(); // By default the blue twin

    /**
     * Creates a three eyed monster and returns it.
     * @return the ISprite of three eyed monster 1
     */
    ISprite getThreeEyedMonsterSprite1(); // By default three yellow eyes

    /**
     * Creates a three eyed monster and returns it.
     * @return the ISprite of three eyed monster 1
     */
    ISprite getThreeEyedMonsterSprite2();

    /**
     * Creates a three eyed monster and returns it.
     * @return the ISprite of three eyed monster 1
     */
    ISprite getThreeEyedMonsterSprite3();

    /**
     * Creates a three eyed monster and returns it.
     * @return the ISprite of three eyed monster 1
     */
    ISprite getThreeEyedMonsterSprite4();

    /**
     * Creates a three eyed monster and returns it.
     * @return the ISprite of three eyed monster 1
     */
    ISprite getThreeEyedMonsterSprite5();

    /**
     * Creates a vampire monster sprite and returns it.
     * @return the ISprite of vampire monster 1
     */
    ISprite getVampireMonsterSprite1(); // By default a blue monster with 2 red teeth

    /**
     * Creates a vampire monster sprite and returns it.
     * @return the ISprite of vampire monster 2
     */
    ISprite getVampireMonsterSprite2();

    /**
     * Creates a vampire monster sprite and returns it.
     * @return the ISprite of vampire monster 3
     */
    ISprite getVampireMonsterSprite3();

    /**
     * Creates a vampire monster sprite and returns it.
     * @return the ISprite of vampire monster 5
     */
    ISprite getVampireMonsterSprite4();

    /**
     * Creates a vampire monster sprite and returns it.
     * @return the ISprite of vampire monster 5
     */
    ISprite getVampireMonsterSprite5();

    /**
     * Creates an ordinary monster sprite and returns it.
     * @return the ISprite of ordinary monster
     */
    ISprite getOrdinaryMonsterSprite(); // By default the purple monster with a blank head

    /**
     * Creates a cactus monster sprite and returns it.
     * @return the ISprite of cactus monster 1
     */
    ISprite getCactusMonster1Sprite(); // By default with three flaps at its Left and Right side and three eyes

    /**
     * Creates a cactus monster sprite and returns it.
     * @return the ISprite of cactus monster 2
     */
    ISprite getCactusMonster2Sprite();

    /**
     * Creates an low five feet monster sprite and returns it.
     * @return the ISprite of low five feet monster
     */
    ISprite getFiveFeetMonsterSprite(); // By default a blue pudding with 5 red feet

    /**
     * Creates an low five feet monster sprite and returns it.
     * @return the ISprite of low five feet monster 1
     */
    ISprite getLowFiveFeetMonster1Sprite(); // By default a green very low monster with 5 feet

    /**
     * Creates an low five feet monster sprite and returns it.
     * @return the ISprite of low five feet monster 2
     */
    ISprite getLowFiveFeetMonster2Sprite();

    /**
     * Creates an small monster sprite and returns it.
     * @return the ISprite of small monster
     */
    ISprite getSmallMonsterSprite(); // By default a very small three eyed red monster

    /**
     * Creates a stars sprite and returns it.
     * @return the ISprite of star1
     */
    ISprite getStarSprite1();

    /**
     * Creates a stars sprite and returns it.
     * @return the ISprite of star2
     */
    ISprite getStarSprite2();

    /**
     * Creates a stars sprite and returns it.
     * @return the ISprite of star3
     */
    ISprite getStarSprite3();

    // Platforms
    ISprite getPlatformSprite1();

    ISprite getPlatformSpriteHori();

    ISprite getPlatformSpriteVert();

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

    ISprite getSizeDownSprite();

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

    /**
     * Returns a sprite of the digit specified in Doodle Jump font.
     *
     * @param digit Between 0 and 10
     * @return A sprite expressing the digit
     */
    ISprite getDigitSprite(int digit);

}
