package resources;

public interface IRes {

    /**
     * Returns the path to a sprite
     *
     * @param sprite A reference to the sprite you want the path to
     * @return The filepath to the sprite
     */
    String getSpritePath(sprites sprite);

    enum sprites {
        // Buttons
        menu,
        pause,
        play,
        playagain,
        resume,

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

        // Monsters
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

        // Numbers
        zero, one, two, three, four, five, six, seven, eight, nine,

        // Platforms
        platform1,
        platform2,
        platform3,
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
        rocket,
        propeller,
        shield,

        // Top bar
        scorebar,

        // Miscellanous
        waitDontShoot,
        avoid,

        // UFO
        ufo,
        ufoShining
    }

}
