package objects.enemies;

import resources.IRes;

/**
 * Defines all enemies in the game.
 */
public enum Enemies {
    /**
     * By default the green monster with some blood
     */
    puddingMonster(
            new EnemyData(IRes.Animations.puddingMonster)
    ),
    /**
     * By default the blue twin
     */
    twinMonster(
            new EnemyData(IRes.Animations.twinMonster)
    ),
    /**
     * By default three yellow eyes
     */
    threeEyedMonster(
            new EnemyData(IRes.Animations.threeEyedMonster)
    ),
    /**
     * By default a blue monster with 2 red teeth
     */
    vampireMonster(
            new EnemyData(IRes.Animations.vampireMonster)
    ),
    /**
     * By default the purple monster with a blank head
     */
    ordinaryMonster(
            new EnemyData(IRes.Animations.ordinaryMonster)
    ),
    /**
     * By default with three flaps at its Left and Right side and three eyes
     */
    cactusMonster(
            new EnemyData(IRes.Animations.cactusMonster)
    ),
    /**
     * By default a blue pudding with 5 red feet
     */
    fiveFeetMonster(
            new EnemyData(IRes.Animations.fiveFeetMonster)
    ),
    /**
     * By default a green very low monster with 5 feet
     */
    lowFiveFeetMonster(
            new EnemyData(IRes.Animations.lowFiveFeetMonster)
    ),
    /**
     * By default a very small three eyed red monster
     */
    smallMonster(
            new EnemyData(IRes.Animations.smallMonster)
    );

    private final EnemyData data;

    /**
     * Constructs a new enemy type.
     *
     * @param data A data object knowing the attributes of the enemy
     */
    Enemies(final EnemyData data) {
        this.data = data;
    }

    /**
     * @return The reference to the animation of the enemy
     */
    public IRes.Animations getAnimation() {
        return this.data.animation;
    }

    /**
     * @return The amount of experience the player gains when he jumpos upon the enemy
     */
    public int getExpAmountAtKill() {
        return this.data.expAmountAtKill;
    }

    /**
     * @return The boost the Doodle gets from colliding with the Enemy.
     */
    public double getBoost() {
        return this.data.boost;
    }

    /**
     * @return The boost the Doodle gets from colliding with the Enemy.
     */
    public double getTooFastSpeed() {
        return this.data.tooFastSpeed;
    }

    /**
     * @return The amount of pixels the enemy should move left and right
     */
    public double getMovingDistance() {
        return this.data.movingDistance;
    }

    /**
     * Contains the data to build an enemy
     */
    private static class EnemyData {

        /**
         * Contains the sprites of the animation of the enemy.
         */
        private final IRes.Animations animation;
        /**
         * The amount of experience the player gains when he jumps upon the enemy
         */
        private int expAmountAtKill = 200;
        /**
         * The boost the Doodle gets from colliding with the Enemy.
         */
        private double boost = -22;
        /**
         * The boost the Doodle gets from colliding with the Enemy.
         */
        private double tooFastSpeed = -5;
        /**
         * The amount of pixels the enemy should move left and right
         */
        private double movingDistance = 15;

        private EnemyData(final IRes.Animations animation) {
            this.animation = animation;
        }

        /**
         * @param expAmountAtKill The amount of experience the player gains when he jumpos upon the enemy
         */
        private EnemyData setExpAmountAtKill(final int expAmountAtKill) {
            this.expAmountAtKill = expAmountAtKill;
            return this;
        }

        /**
         * @param boost The boost the Doodle gets from colliding with the Enemy.
         */
        private EnemyData setBoost(final double boost) {
            this.boost = boost;
            return this;
        }

        /**
         * @param tooFastSpeed The boost the Doodle gets from colliding with the Enemy.
         */
        private EnemyData setTooFastSpeed(final double tooFastSpeed) {
            this.tooFastSpeed = tooFastSpeed;
            return this;
        }
        /**
         * @param movingDistance The amount of pixels the enemy should move left and right
         */
        private EnemyData setMovingDistance(final double movingDistance) {
            this.movingDistance = movingDistance;
            return this;
        }
    }
}
