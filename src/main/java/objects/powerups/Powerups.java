package objects.powerups;

import resources.IRes;

/**
 * Indicates which types of powerups are available.
 * We suppress the CheckStyle warning javadocMethod here, because the methods are very straightfoward
 * and do not require any additional javadoc.
 */
@SuppressWarnings("checkstyle:javadocmethod")
public enum Powerups {
    /**
     * Jetpack.
     */
    jetpack(Jetpack.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.jetpack, 50)
                    .setAnimation(IRes.Animations.jetpack)
                    .setAcceleration(-2d)
                    .setInitDropSpeed(-25d)
                    .setBoost(-25d)
                    .setHorSpeed(1.2d)
                    .setMaxTimeInAir(175)
                    .setOwnedYOffset(35)
                    .setAnimationRefreshRate(3)
                    .setAnimationPhases(new double[]{0.1d, 0.8d, 1d})
                    .setAnimationOffsets(new int[]{0, 3, 6})
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.jetpack, 100)
                    .setAnimation(IRes.Animations.jetpack)
                    .setAcceleration(-2d)
                    .setInitDropSpeed(-25d)
                    .setBoost(-25d)
                    .setHorSpeed(1.2d)
                    .setMaxTimeInAir(200)
                    .setOwnedYOffset(35)
                    .setAnimationRefreshRate(3)
                    .setAnimationPhases(new double[]{0.1d, 0.8d, 1d})
                    .setAnimationOffsets(new int[]{0, 3, 6})
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.spaceRocket, 150)
                    .setAnimation(IRes.Animations.spaceRocket)
                    .setAcceleration(-2d)
                    .setInitDropSpeed(-25d)
                    .setBoost(-25d)
                    .setHorSpeed(1.2d)
                    .setMaxTimeInAir(225)
                    .setOwnedYOffset(-70)
                    .setAnimationRefreshRate(3)
                    .setAnimationPhases(new double[]{0.1d, 0.8d, 1d})
                    .setAnimationOffsets(new int[]{0, 3, 6})
                    .build()
    }),
    /**
     * Propeller.
     */
    propeller(Propeller.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.propeller, 50)
                    .setAnimation(IRes.Animations.propeller)
                    .setAcceleration(-1d)
                    .setInitDropSpeed(-20d)
                    .setBoost(-20d)
                    .setHorSpeed(1.2d)
                    .setMaxTimeInAir(150)
                    .setOwnedYOffset(-26)
                    .setAnimationRefreshRate(3)
                    .build()
    }),
    springShoes(SpringShoes.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.springShoes, 50)
                    .setMaxUses(3)
                    .setBoost(-30d)
                    .build()
    }),
    sizeUp(SizeUp.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.sizeUp, 50)
                    .setScale(0.4d)
                    .build()
    }),
    sizeDown(SizeDown.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.sizeDown, 50)
                    .setScale(-0.4d)
                    .build()
    }),
    spring(Spring.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.spring, 0)
                    .setUsedSprite(IRes.Sprites.springUsed)
                    .setBoost(-30)
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.doubleSpring, 50)
                    .setUsedSprite(IRes.Sprites.doubleSpringUsed)
                    .setBoost(-40)
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.titaniumSpring, 150)
                    .setUsedSprite(IRes.Sprites.titaniumSpringUsed)
                    .setBoost(-50)
                    .build()
    }),
    trampoline(Trampoline.class, new PowerupLevel[]{
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.trampoline, 50)
                    .setUsedSprite(IRes.Sprites.trampolineUsed)
                    .setBoost(-30)
                    .setRetractSpeed(250)
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.circusCannon, 150)
                    .setUsedSprite(IRes.Sprites.circusCannonUsed)
                    .setBoost(-40)
                    .setRetractSpeed(250)
                    .build(),
            new PowerupLevel.PowerupLevelBuilder(IRes.Sprites.rocketLauncher, 300)
                    .setUsedSprite(IRes.Sprites.rocketLauncherUsed)
                    .setBoost(-50)
                    .setRetractSpeed(250)
                    .build()
    });

    /**
     * Used for debugging purposes; the name of the enum value.
     */
    private final Class<?> associatedClass;

    /**
     * Contains the data for each level of the powerup.
     */
    private final PowerupLevel[] powerupLevels;

    /**
     * Construct a new powerup.
     *
     * @param associatedClass The Java class associated with the enum powerup type
     * @param powerupLevels   The data of each level of the powerup
     */
    <T extends APowerup> Powerups(final Class<T> associatedClass, final PowerupLevel[] powerupLevels) {
        this.associatedClass = associatedClass;
        this.powerupLevels = powerupLevels;
    }

    public Class<?> getAssociatedClass() {
        return associatedClass;
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
     * @param level The level you want the amount of price it costs to upgrade to from
     * @return The price in price of the upgrade to the level specified
     */
    public int getPrice(final int level) {
        checkLevel(level, "price");
        return powerupLevels[level - 1].price; // The array is 0-indexed, the input 1-indexed
    }

    public IRes.Sprites getSprite(final int level) {
        checkLevel(level, "sprite");
        return powerupLevels[level - 1].sprite;
    }

    public IRes.Animations getAnimation(final int level) {
        checkLevel(level, "animation");
        return powerupLevels[level - 1].animation;
    }

    public int getMaxTimeInAir(final int level) {
        checkLevel(level, "maxTimeInAir");
        return powerupLevels[level - 1].maxTimeInAir;
    }

    public int getOwnedYOffset(final int level) {
        checkLevel(level, "ownedYOffset");
        return powerupLevels[level - 1].ownedYOffset;
    }

    public double getAcceleration(final int level) {
        checkLevel(level, "acceleration");
        return powerupLevels[level - 1].acceleration;
    }

    public double getInitDropSpeed(final int level) {
        checkLevel(level, "initDropSpeed");
        return powerupLevels[level - 1].initDropSpeed;
    }

    public double getBoost(final int level) {
        checkLevel(level, "boost");
        return powerupLevels[level - 1].boost;
    }

    public double getHorSpeed(final int level) {
        checkLevel(level, "horSpeed");
        return powerupLevels[level - 1].horSpeed;
    }

    public int getAnimationRefreshRate(final int level) {
        checkLevel(level, "animationRefreshRate");
        return powerupLevels[level - 1].animationRefreshRate;
    }

    public int getMaxUses(final int level) {
        checkLevel(level, "maxUses");
        return powerupLevels[level - 1].maxUses;
    }

    public double[] getAnimationPhases(final int level) {
        checkLevel(level, "animationPhases");
        return powerupLevels[level - 1].animationPhases;
    }

    public int[] getAnimationOffsets(final int level) {
        checkLevel(level, "animationOffsets");
        return powerupLevels[level - 1].animationOffsets;
    }

    public IRes.Sprites getUsedSprite(final int level) {
        checkLevel(level, "animationPhases");
        return powerupLevels[level - 1].usedSprite;
    }

    public double getScale(final int level) {
        checkLevel(level, "animationOffsets");
        return powerupLevels[level - 1].scale;
    }

    public int getRetractSpeed(final int level) {
        checkLevel(level, "retractSpeed");
        return powerupLevels[level - 1].retractSpeed;
    }

    private void checkLevel(final int level, final String name) {
        if (level <= 0) {
            throw new IllegalArgumentException("Powerups do not have levels lower than or equal to 0");
        }
        if (level > this.powerupLevels.length) {
            throw new IllegalArgumentException("The maximum level of the powerup \""
                    + name
                    + "\" is "
                    + this.powerupLevels.length
                    + ", while the sprite was asked for a level exceeding this maximum level: "
                    + level);
        }
    }

    private static class PowerupLevel {
        private final IRes.Sprites sprite;
        private final int price;
        private final IRes.Animations animation;
        private final int maxTimeInAir;
        private final int ownedYOffset;
        private final double acceleration;
        private final double initDropSpeed;
        private final double boost;
        private final double horSpeed;
        private final double[] animationPhases;
        private final int[] animationOffsets;
        private final int animationRefreshRate;
        private final int maxUses;
        private final IRes.Sprites usedSprite;
        private final double scale;
        private final int retractSpeed;

        private PowerupLevel(final PowerupLevelBuilder builder) {
            this.sprite = builder.sprite;
            this.price = builder.price;
            this.animation = builder.animation;
            this.maxTimeInAir = builder.maxTimeInAir;
            this.ownedYOffset = builder.ownedYOffset;
            this.acceleration = builder.acceleration;
            this.initDropSpeed = builder.initDropSpeed;
            this.boost = builder.boost;
            this.horSpeed = builder.horSpeed;
            this.animationPhases = builder.animationPhases;
            this.animationOffsets = builder.animationOffsets;
            this.animationRefreshRate = builder.animationRefreshRate;
            this.maxUses = builder.maxUses;
            this.usedSprite = builder.usedSprite;
            this.scale = builder.scale;
            this.retractSpeed = builder.retractSpeed;
        }

        private static final class PowerupLevelBuilder {
            private final IRes.Sprites sprite;
            private final int price;
            private IRes.Animations animation;
            private int maxTimeInAir;
            private int ownedYOffset;
            private double acceleration;
            private double initDropSpeed;
            private int animationRefreshRate;
            private int maxUses;
            private IRes.Sprites usedSprite;
            private double boost;
            private double horSpeed;
            private double[] animationPhases;
            private int[] animationOffsets;
            private double scale;
            private int retractSpeed;

            /* package */ PowerupLevelBuilder(final IRes.Sprites sprite, final int price) {
                this.sprite = sprite;
                this.price = price;
            }

            /* package */ PowerupLevelBuilder setAnimation(final IRes.Animations animation) {
                this.animation = animation;
                return this;
            }

            /* package */ PowerupLevelBuilder setAcceleration(final double acceleration) {
                this.acceleration = acceleration;
                return this;
            }

            /* package */ PowerupLevelBuilder setInitDropSpeed(final double initDropSpeed) {
                this.initDropSpeed = initDropSpeed;
                return this;
            }

            /* package */ PowerupLevelBuilder setBoost(final double boost) {
                this.boost = boost;
                return this;
            }

            /* package */ PowerupLevelBuilder setHorSpeed(final double horSpeed) {
                this.horSpeed = horSpeed;
                return this;
            }

            /* package */ PowerupLevelBuilder setMaxTimeInAir(final int maxTimeInAir) {
                this.maxTimeInAir = maxTimeInAir;
                return this;
            }

            /* package */ PowerupLevelBuilder setOwnedYOffset(final int ownedYOffset) {
                this.ownedYOffset = ownedYOffset;
                return this;
            }

            /* package */ PowerupLevelBuilder setAnimationRefreshRate(final int animationRefreshRate) {
                this.animationRefreshRate = animationRefreshRate;
                return this;
            }

            /* package */ PowerupLevelBuilder setMaxUses(final int maxUses) {
                this.maxUses = maxUses;
                return this;
            }

            /* package */ PowerupLevelBuilder setUsedSprite(final IRes.Sprites usedSprite) {
                this.usedSprite = usedSprite;
                return this;
            }

            /* package */ PowerupLevelBuilder setAnimationPhases(final double[] animationPhases) {
                this.animationPhases = animationPhases;
                return this;
            }

            /* package */ PowerupLevelBuilder setAnimationOffsets(final int[] animationOffsets) {
                this.animationOffsets = animationOffsets;
                return this;
            }

            /* package */ PowerupLevelBuilder setScale(final double scale) {
                this.scale = scale;
                return this;
            }

            /* package */ PowerupLevelBuilder setRetractSpeed(final int retractSpeed) {
                this.retractSpeed = retractSpeed;
                return this;
            }

            /* package */ PowerupLevel build() {
                return new PowerupLevel(this);
            }
        }
    }
}
