package objects.enemies;

import resources.IRes;

/**
 * Defines all enemies in the game.
 */
public enum EEnemies {
    /**
     * By default the green monster with some blood
     */
    puddingMonster(new IRes.Sprites[]{
            IRes.Sprites.puddingMonster1,
            IRes.Sprites.puddingMonster2,
            IRes.Sprites.puddingMonster3,
            IRes.Sprites.puddingMonster4,
            IRes.Sprites.puddingMonster5
    }),
    /**
     * By default the blue twin
     */
    twinMonster(new IRes.Sprites[]{
            IRes.Sprites.twinMonster
    }),
    /**
     * By default three yellow eyes
     */
    threeEyedMonster(new IRes.Sprites[]{
            IRes.Sprites.threeEyedMonster1,
            IRes.Sprites.threeEyedMonster2,
            IRes.Sprites.threeEyedMonster3,
            IRes.Sprites.threeEyedMonster4,
            IRes.Sprites.threeEyedMonster5
    }),
    /**
     * By default a blue monster with 2 red teeth
     */
    vampireMonster(new IRes.Sprites[]{
            IRes.Sprites.vampireMonster1,
            IRes.Sprites.vampireMonster2,
            IRes.Sprites.vampireMonster3,
            IRes.Sprites.vampireMonster4,
            IRes.Sprites.vampireMonster5
    }),
    /**
     * By default the purple monster with a blank head
     */
    ordinaryMonster(new IRes.Sprites[]{
            IRes.Sprites.ordinaryMonster
    }),
    /**
     * By default with three flaps at its Left and Right side and three eyes
     */
    cactusMonster(new IRes.Sprites[]{
            IRes.Sprites.cactusMonster1,
            IRes.Sprites.cactusMonster2
    }),
    /**
     * By default a blue pudding with 5 red feet
     */
    fiveFeetMonster(new IRes.Sprites[]{
            IRes.Sprites.fiveFeetMonster
    }),
    /**
     * By default a green very low monster with 5 feet
     */
    lowFiveFeetMonster(new IRes.Sprites[]{
            IRes.Sprites.lowFiveFeetMonster1,
            IRes.Sprites.lowFiveFeetMonster2
    }),
    /**
     * By default a very small three eyed red monster
     */
    smallMonster(new IRes.Sprites[]{
            IRes.Sprites.smallMonster
    });

    /**
     * Contains the sprites of the animation of the enemy.
     */
    private IRes.Sprites[] sprites;

    /**
     * Constructs a new enemy type.
     *
     * @param sprites Contains the sprites of the animation of the enemy
     */
    EEnemies(IRes.Sprites[] sprites) {
        this.sprites = sprites;
    }

    /**
     * @param index The index of the animation sprite you want to get
     * @return The reference to the sprite with the correct index of the enemy
     */
    public IRes.Sprites getSprite(final int index) {
        return sprites[index];
    }
}
