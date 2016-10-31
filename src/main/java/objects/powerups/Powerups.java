package objects.powerups;

import resources.IRes;

/**
 * Indicates which types of powerups are available.
 */
public enum Powerups {
    /**
     * Jetpack.
     */
    jetpack("jetpack", new PowerupLevel[]{
            new EquipmentPowerupLevel(IRes.Sprites.jetpack, 50, Animations.jetpack, new JetpackPowerupBehaviour(new EquipmentPowerupBehaviourPhysics(-2d, -25d, -25d, 1.2d), 175, 35, 3, new double[] {0.1d, 0.8d, 1d}, new int[] {0, 3, 6})),
            new EquipmentPowerupLevel(IRes.Sprites.jetpack, 100, Animations.jetpack, new JetpackPowerupBehaviour(new EquipmentPowerupBehaviourPhysics(-2d, -25d, -25d, 1.2d), 200, 35, 3, new double[] {0.1d, 0.8d, 1d}, new int[] {0, 3, 6})),
            new EquipmentPowerupLevel(IRes.Sprites.spaceRocket, 150, Animations.spaceRocket, new JetpackPowerupBehaviour(new EquipmentPowerupBehaviourPhysics(-2d, -25d, -25d, 1.2d), 225, -70, 3, new double[] {0.1d, 0.8d, 1d}, new int[] {0, 3, 6}))
    }),
    /**
     * Propeller.
     */
    propeller("propeller", new PowerupLevel[]{
            new EquipmentPowerupLevel(IRes.Sprites.propeller, 50, Animations.propeller, new EquipmentPowerupBehaviour(new EquipmentPowerupBehaviourPhysics(-1d, -20d, -20d, 1.2d), 150, -26, 3))
    }),
    springShoes("springShoes", new PowerupLevel[]{
            new SpringShoesPowerupLevel(IRes.Sprites.propeller, 50, 3, -30d)
    }),
    sizeUp("sizeUp", new PowerupLevel[] {
            new ScalingPowerupLevel(IRes.Sprites.sizeUp, 50, 0.4d)
    }),
    sizeDown("sizeDown", new PowerupLevel[] {
            new ScalingPowerupLevel(IRes.Sprites.sizeUp, 50, -0.4d)
    }),
    spring("spring", new PowerupLevel[] {
            new JumpablePowerupLevel(IRes.Sprites.spring, 0, IRes.Sprites.springUsed, -30),
            new JumpablePowerupLevel(IRes.Sprites.doubleSpring, 0, IRes.Sprites.doubleSpringUsed, -40),
            new JumpablePowerupLevel(IRes.Sprites.titaniumSpring, 0, IRes.Sprites.titaniumSpringUsed, -50)
    }),
    trampoline("trampoline", new PowerupLevel[] {
            new JumpablePowerupLevel(IRes.Sprites.trampoline, 0, IRes.Sprites.trampolineUsed, -30),
            new JumpablePowerupLevel(IRes.Sprites.circusCannon, 0, IRes.Sprites.circusCannonUsed, -40),
            new JumpablePowerupLevel(IRes.Sprites.rocketLauncher, 0, IRes.Sprites.rocketLauncherUsed, -50)
    });

    /**
     * Used for debugging purposes; the name of the enum value.
     */
    private final String name;

    /**
     * Contains the data for each level of the powerup.
     */
    private final PowerupLevel[] powerupLevels;

    /**
     * Construct a new powerup.
     *
     * @param name  The name of the powerup, only used for debugging purposes
     * @param powerupLevels The data of each level of the powerup
     */
    Powerups(final String name, final PowerupLevel[] powerupLevels) {
        this.name = name;
        this.powerupLevels = powerupLevels;
    }

    /**
     * @return The maximum level of the powerup
     */
    public int getMaxLevel() {
        return powerupLevels.length;
    }

    /**
     * Returns the price to upgrade to a certain level.
     *
     * @param level The level you want the amount of coins it costs to upgrade to from
     * @return The price in coins of the upgrade to the level specified
     */
    public int getPrice(final int level) {
        if (level <= 0) {
            throw new IllegalArgumentException("Powerups do not have levels lower than or equal to 0");
        }
        if (level > powerupLevels.length) {
            throw new IllegalArgumentException("The maximum level of the powerup \""
                    + this.name
                    + "\" is "
                    + this.powerupLevels.length
                    + ", while price was asked for a level exceeding this maximum level: "
                    + level);
        }
        return powerupLevels[level - 1].coins; // The array is 0-indexed, the input 1-indexed
    }

    private enum Animations {
        jetpack(
                IRes.Sprites.jetpack0,
                IRes.Sprites.jetpack1,
                IRes.Sprites.jetpack2,
                IRes.Sprites.jetpack3,
                IRes.Sprites.jetpack4,
                IRes.Sprites.jetpack5,
                IRes.Sprites.jetpack6,
                IRes.Sprites.jetpack7,
                IRes.Sprites.jetpack8,
                IRes.Sprites.jetpack9
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
        );


        Animations(IRes.Sprites... sprites) {
        }
    }

    private abstract static class PowerupLevel {
        private final IRes.Sprites sprite;
        private final int coins;

        private PowerupLevel(final IRes.Sprites sprite, final int coins) {
            this.sprite = sprite;
            this.coins = coins;
        }
    }

    private static final class EquipmentPowerupLevel extends PowerupLevel {
        private final Animations animation;
        private final EquipmentPowerupBehaviour behaviour;
        private EquipmentPowerupLevel(final IRes.Sprites sprite, final int coins, final Animations animation, final EquipmentPowerupBehaviour behaviour) {
            super(sprite, coins);
            this.animation = animation;
            this.behaviour = behaviour;
        }
    }

    private static class EquipmentPowerupBehaviour {
        private final EquipmentPowerupBehaviourPhysics physics;
        private final int maxTimer, ownedYOffset, animationRefreshRate;

        private EquipmentPowerupBehaviour(final EquipmentPowerupBehaviourPhysics physics, final int maxTimer, final int ownedYOffset, final int animationRefreshRate) {
            this.physics = physics;
            this.maxTimer = maxTimer;
            this.ownedYOffset = ownedYOffset;
            this.animationRefreshRate = animationRefreshRate;
        }
    }

    private static final class JetpackPowerupBehaviour extends EquipmentPowerupBehaviour {
        private final double[] animationPhases;
        private final int[] animationOffsets;
        private JetpackPowerupBehaviour(final EquipmentPowerupBehaviourPhysics physics, final int maxTimer, final int ownedYOffset, final int animationRefreshRate, final double[] animationPhases, final int[] animationOffsets) {
            super(physics, maxTimer, ownedYOffset, animationRefreshRate);
            if (animationPhases.length != 3) {
                throw new IllegalArgumentException("animationPhases must have a length of 3, but the length was: " + animationPhases.length);
            }
            if (animationOffsets.length != 3) {
                throw new IllegalArgumentException("animationOffsets must have a length of 3, but the length was: " + animationOffsets.length);
            }
            this.animationPhases = animationPhases;
            this.animationOffsets = animationOffsets;
        }
    }

    private static final class AnimationPhaseData {
        private final double animationPhaseOne, animationPhaseTwo, animationPhaseThree;
        private final double animationOffsetOne, animationOffsetTwo, animationOffsetThree;
        private AnimationPhaseData(final double animationPhaseOne, final double animationPhaseTwo, final double animationPhaseThree, final double animationOffsetOne, final double animationOffsetTwo, final double animationOffsetThree) {
            this.animationPhaseOne = animationPhaseOne;
            this.animationPhaseTwo = animationPhaseTwo;
            this.animationPhaseThree = animationPhaseThree;
            this.animationOffsetOne = animationOffsetOne;
            this.animationOffsetTwo = animationOffsetTwo;
            this.animationOffsetThree = animationOffsetThree;
        }
    }

    private static final class EquipmentPowerupBehaviourPhysics {
        private final double acceleration, initDropSpeed, maxBoost, horSpeed;

        private EquipmentPowerupBehaviourPhysics(final double acceleration, final double initDropSpeed, final double maxBoost, final double horSpeed) {
            this.acceleration = acceleration;
            this.initDropSpeed = initDropSpeed;
            this.maxBoost = maxBoost;
            this.horSpeed = horSpeed;
        }
    }

    private static final class JumpablePowerupLevel extends PowerupLevel {
        private final IRes.Sprites usedSprite;
        private final int boost;
        private JumpablePowerupLevel(final IRes.Sprites sprite, final int coins, final IRes.Sprites usedSprite, final int boost) {
            super(sprite, coins);
            this.usedSprite = usedSprite;
            this.boost = boost;
        }
    }

    private static final class ScalingPowerupLevel extends PowerupLevel {
        private final double scale;

        private ScalingPowerupLevel(final IRes.Sprites sprite, final int coins, final double scale) {
            super(sprite, coins);
            this.scale = scale;
        }
    }

    private static final class SpringShoesPowerupLevel extends PowerupLevel {
        private final int maxUses;
        private final double boost;

        private SpringShoesPowerupLevel(final IRes.Sprites sprite, final int coins, final int maxUses, final double boost) {
            super(sprite, coins);
            this.maxUses = maxUses;
            this.boost = boost;
        }
    }
}
