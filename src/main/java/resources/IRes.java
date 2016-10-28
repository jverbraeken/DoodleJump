package resources;

import system.Game;

/**
 * Interface for the resources.
 * <br>
 * It is not deemed necessary for all individual resources to have a JavaDoc.
 */
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
        multiplayer,
        pause,
        play,
        playAgain,
        resume,
        scoreButton,
        chooseMode,
        shop,

        // Covers
        background,
        pauseCover,
        startCover,

        // Doodle
        doodleLeftAscend,
        doodleLeftDescend,
        doodleRightAscend,
        doodleRightDescend,

        // Projectiles
        regularProjectile,

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
        doubleSpring,
        doubleSpringUsed,
        titaniumSpring,
        titaniumSpringUsed,
        springShoes,
        jetpack,
        propeller,
        shield,
        sizeUp,
        sizeDown,
        circusCannon,
        circusCannonUsed,
        rocketLauncher,
        rocketLauncherUsed,
        spaceRocket,

        // Passive
        jetpack0,
        jetpack1,
        jetpack2,
        jetpack3,
        jetpack4,
        jetpack5,
        jetpack6,
        jetpack7,
        jetpack8,
        jetpack9,
        propeller0,
        propeller1,
        propeller2,
        spaceRocket0,
        spaceRocket1,
        spaceRocket2,
        spaceRocket3,
        spaceRocket4,
        spaceRocket5,
        spaceRocket6,
        spaceRocket7,
        spaceRocket8,


        // Score screen
        scoreScreenBottom,
        scoreScreenLeft,
        scoreScreenTop,

        // Top bar
        scoreBar,

        // Miscellaneous
        waitDoNotShoot,
        avoid,
        popupOkButton,

        // UFO
        ufo,
        ufoShining,

        // Mode icons
        regularMode,
        darknessMode,
        invertMode,
        underwaterMode,
        spaceMode,
        storyMode,

        redCross,

        // coins
        coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, coin9, coin10,

        // Missions
        achievement,

        //Popup
        popupBackground,

        // Unimplemented
        unimplemented
    }

}
