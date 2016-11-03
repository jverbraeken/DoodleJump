package resources;

import resources.sprites.ISprite;
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
        shopCover,

        // Doodle
        doodleArrow,
        greenDoodleLeftAscend,
        greenDoodleLeftDescend,
        greenDoodleRightAscend,
        greenDoodleRightDescend,
        redDoodleLeftAscend,
        redDoodleLeftDescend,
        redDoodleRightAscend,
        redDoodleRightDescend,
        blueDoodleLeftAscend,
        blueDoodleLeftDescend,
        blueDoodleRightAscend,
        blueDoodleRightDescend,

        // projectiles
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
        afterburner,

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

        afterburner0,
        afterburner1,
        afterburner2,
        afterburner3,
        afterburner4,
        afterburner5,
        afterburner6,
        afterburner7,
        afterburner8,
        afterburner9,


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
        verticalOnlyMode,
        underwaterMode,
        spaceMode,
        horizontalOnlyMode,

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

    enum Animations {
        jetpack(
                IRes.Sprites.jetpack0,
                IRes.Sprites.jetpack1,
                IRes.Sprites.jetpack2,
                IRes.Sprites.jetpack3,
                IRes.Sprites.jetpack4,
                IRes.Sprites.jetpack5,
                IRes.Sprites.jetpack6,
                IRes.Sprites.jetpack7,
                IRes.Sprites.jetpack8
        ),
        spaceRocket(
                IRes.Sprites.spaceRocket0,
                IRes.Sprites.spaceRocket1,
                IRes.Sprites.spaceRocket2,
                IRes.Sprites.spaceRocket3,
                IRes.Sprites.spaceRocket4,
                IRes.Sprites.spaceRocket5,
                IRes.Sprites.spaceRocket6,
                IRes.Sprites.spaceRocket7,
                IRes.Sprites.spaceRocket8
        ),
        propeller(
                IRes.Sprites.propeller0,
                IRes.Sprites.propeller1,
                IRes.Sprites.propeller0,
                IRes.Sprites.propeller2
        ),
        puddingMonster(
                IRes.Sprites.puddingMonster1,
                IRes.Sprites.puddingMonster2,
                IRes.Sprites.puddingMonster3,
                IRes.Sprites.puddingMonster4,
                IRes.Sprites.puddingMonster5
        ),
        /**
         * By default the blue twin
         */
        twinMonster(
                IRes.Sprites.twinMonster
        ),
        /**
         * By default three yellow eyes
         */
        threeEyedMonster(
                IRes.Sprites.threeEyedMonster1,
                IRes.Sprites.threeEyedMonster2,
                IRes.Sprites.threeEyedMonster3,
                IRes.Sprites.threeEyedMonster4,
                IRes.Sprites.threeEyedMonster5
        ),
        /**
         * By default a blue monster with 2 red teeth
         */
        vampireMonster(
                IRes.Sprites.vampireMonster1,
                IRes.Sprites.vampireMonster2,
                IRes.Sprites.vampireMonster3,
                IRes.Sprites.vampireMonster4,
                IRes.Sprites.vampireMonster5
        ),
        /**
         * By default the purple monster with a blank head
         */
        ordinaryMonster(
                IRes.Sprites.ordinaryMonster
        ),
        /**
         * By default with three flaps at its Left and Right side and three eyes
         */
        cactusMonster(
                IRes.Sprites.cactusMonster1,
                IRes.Sprites.cactusMonster2
        ),
        /**
         * By default a blue pudding with 5 red feet
         */
        fiveFeetMonster(
                IRes.Sprites.fiveFeetMonster
        ),
        /**
         * By default a green very low monster with 5 feet
         */
        lowFiveFeetMonster(
                IRes.Sprites.lowFiveFeetMonster1,
                IRes.Sprites.lowFiveFeetMonster2
        ),
        /**
         * By default a very small three eyed red monster
         */
        smallMonster(
                IRes.Sprites.smallMonster
        );

        private final IRes.Sprites[] sprites;

        Animations(IRes.Sprites... sprites) {
            this.sprites = sprites;
        }

        public IRes.Sprites[] getSpriteReferences() {
            IRes.Sprites[] dest = new IRes.Sprites[sprites.length];
            System.arraycopy(sprites, 0, dest, 0, sprites.length);
            return dest;
        }

        public IRes.Sprites getSpriteReference(final int index) {
            if (index < 0) {
                throw new IllegalArgumentException("Negative values are not allowed for an animation index");
            }
            if (index >= sprites.length) {
                throw new IllegalArgumentException("The frame requested was number " + index + ", while the maximum number of frames is " + sprites.length);
            }
            return this.sprites[index];
        }
    }
}
