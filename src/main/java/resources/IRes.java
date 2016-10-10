package resources;

import system.Game;

/**
 * Interface for the resources.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public interface IRes {

    /**
     * Returns the path to a sprite.
     *
     * @param sprite A reference to the sprite you want the path to
     * @return The filepath to the sprite
     */
    String getSpritePath(final Sprites sprite);

    /**
     * Set the skin of the game.
     *
     * @param mode The mode to get the skin for.
     */
    void setSkin(final Game.Modes mode);

    /**
     * Enum containing a value for each sprite.
     */
    enum Sprites {
        // Buttons
        menu,
        pause,
        play,
        playAgain,
        resume,
        scoreButton,
        chooseMode,

        // Covers
        background,
        pauseCover,
        startCover,

        // Doodle
        doodleLeftAscend,
        doodleLeftDescend,
        doodleRightAscend,
        doodleRightDescend,

        // Kill screen
        gameOver,
        killScreenBottom,

        // Enemies
        puddingMonster1,
        puddingMonster2,
        puddingMonster3,
        puddingMonster4,
        puddingMonster5,
        twinMonster,
        threeEyedMonster1,
        threeEyedMonster2,
        threeEyedMonster3,
        threeEyedMonster4,
        threeEyedMonster5,
        vampireMonster1,
        vampireMonster2,
        vampireMonster3,
        vampireMonster4,
        vampireMonster5,
        ordinaryMonster,
        cactusMonster1,
        cactusMonster2,
        fiveFeetMonster,
        lowFiveFeetMonster1,
        lowFiveFeetMonster2,
        smallMonster,

        //Stars
        confusedStars1, confusedStars2, confusedStars3,

        // Numbers
        zero, one, two, three, four, five, six, seven, eight, nine,

        // Platforms
        platform1,
        platformHorizontal,
        platformVertical,
        platform4,
        platform5,
        platform6,
        platform7,
        platform8,
        platform9,
        platformBroken1,
        platformBroken2,
        platformBroken3,
        platformBroken4,
        platformExplosive1,
        platformExplosive2,
        platformExplosive3,
        platformMovable1,
        platformMovable2,
        platformMovable3,
        platformMovable4,
        platformShining1,
        platformShining2,
        platformShining3,

        // Powerups
        trampoline,
        trampolineUsed,
        spring,
        springUsed,
        springShoes,
        jetpack,
        propeller,
        shield,
        sizeUp,
        sizeDown,

        // Score screen
        scoreScreenBottom,
        scoreScreenLeft,
        scoreScreenTop,

        // Top bar
        scoreBar,

        // Miscellaneous
        waitDoNotShoot,
        avoid,

        // UFO
        ufo,
        ufoShining,

        // Mode icons
        regularMode,
        darknessMode,
        invertMode,
        underwaterMode,
        spaceMode,
        storyMode
    }

}
