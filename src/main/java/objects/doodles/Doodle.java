package objects.doodles;

import constants.IConstants;
import input.Keys;
import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.doodles.doodle_behavior.MovementBehavior;
import objects.doodles.doodle_behavior.RegularBehavior;
import objects.doodles.doodle_behavior.SpaceBehavior;
import objects.doodles.doodle_behavior.UnderwaterBehavior;
import objects.enemies.IEnemy;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import rendering.ICamera;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class describes the behaviour of the Doodle.
 */
@SuppressWarnings("checkstyle:designforextension")
public class Doodle extends AGameObject implements IDoodle {

    /**
     * The height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     */
    private static final double LEGS_HEIGHT = .8d;
    /**
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_LEFT = .3d;
    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_RIGHT = .8d;
    /**
     * An additional offset for the top of the hitbox for the Doodle.
     */
    private static final double TOP_HITBOX_OFFSET = 25d;
    /**
     * Amount of star frames.
     */
    private static final double STAR_FRAMES = 9d;
    /**
     * First star animation in frames.
     */
    private static final double FIRST_STAR_FRAME = 3d;
    /**
     * Second star animation in frames.
     */
    private static final double SECOND_STAR_FRAME = 6d;
    /**
     * The scalar for the Stars sprite.
     */
    private static final double STARS_SCALAR = .7;
    /**
     * The offset for the Stars sprite.
     */
    private static final int STARS_OFFSET = 20;
    /**
     * The minimum and maximum value of the spriteScaler.
     */
    private static final double SPRITE_SCALAR_MIN = 0d, SPRITE_SCALAR_MAX = 2d;
    /**
     * The scalar value that assists in calculating the size of the out of screen arrow.
     */
    private static final double ARROW_SCALAR = 100d;

    /**
     * Fake APowerup instance to return when actual powerup value is null.
     */
    private static volatile IPowerup fakePowerup = new IPowerup() {
        @Override
        public void perform(PowerupOccasion occasion) {
        }

        @Override
        public void setPositionOnPlatform(IPlatform platform) {
        }

        @Override
        public void endPowerup() {
        }

        @Override
        public void addXPos(double xPos) {
        }

        @Override
        public void addYPos(double yPos) {
        }

        @Override
        public boolean checkCollision(IGameObject gameObject) {
            return false;
        }

        @Override
        public void collidesWith(IDoodle doodle) {
        }

        @Override
        public double[] getHitBox() {
            return new double[0];
        }

        @Override
        public ISprite getSprite() {
            return null;
        }

        @Override
        public double getXPos() {
            return 0;
        }

        @Override
        public double getYPos() {
            return 0;
        }

        @Override
        public Point getPoint() {
            return null;
        }

        @Override
        public void setHitBox(int left, int top, int right, int bottom) {
        }

        @Override
        public void setSprite(ISprite sprite) {
        }

        @Override
        public void setXPos(double xPos) {
        }

        @Override
        public void setYPos(double yPos) {
        }

        @Override
        public void render() {
        }

        @Override
        public void update(double delta) {
        }
    };
    /**
     * The world the Doodle lives in.
     */
    private final World world;
    /**
     * Gives true if the doodle is alive.
     */
    private boolean alive = true;
    /**
     * Keeps the number of the star animation when killed by an enemy.
     */
    private int starNumber = 0;
    /**
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private EnumMap<MovementBehavior.Directions, ISprite[]> sprites = new EnumMap<>(MovementBehavior.Directions.class);
    /**
     * The current score of the doodle.
     */
    private double score;
    /**
     * The extra experience earned, by for example killing enemies.
     */
    private double experience = 0;
    /**
     * All the passives the can Doodle have.
     */
    private IPowerup powerup;
    /**
     * Describes the movement behavior of the doodle.
     */
    private MovementBehavior behavior;
    /**
     * The scalar for the Doodle sprite.
     */
    private double spriteScalar = 1d;
    /**
     * The keys the Doodle responds to.
     */
    private Keys[] keys = new Keys[]{Keys.arrowLeft, Keys.arrowRight};
    /**
     * A list of all the projectiles shot by this Enemy.
     */
    private final List<IGameObject> projectiles = new ArrayList<>();
    /**
     * The shooting observer of this Doodle.
     */
    private ShootingObserver shootingObserver;

    /**
     * Doodle constructor.
     *
     * @param serviceLocator The service locator.
     * @param world  The world the Doodle lives in.
     */
    /* package */ Doodle(final IServiceLocator serviceLocator, final ISprite[] sprites, final World world) {
        super(serviceLocator,
                new Point(serviceLocator.getConstants().getGameWidth() / 2,
                        serviceLocator.getConstants().getGameHeight() / 2),
                sprites[0],
                Doodle.class);

        this.shootingObserver = new ShootingObserver(serviceLocator, this);

        this.updateHitBox();
        this.setBehavior(Game.getMode());

        ISprite[] leftSprites = new ISprite[2];
        leftSprites[0] = sprites[0];
        leftSprites[1] = sprites[1];
        this.sprites.put(MovementBehavior.Directions.Left, leftSprites);

        ISprite[] rightSprites = new ISprite[2];
        rightSprites[0] = sprites[2];
        rightSprites[1] = sprites[3];
        this.sprites.put(MovementBehavior.Directions.Right, rightSprites);

        this.world = world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlive(final boolean al) {
        this.alive = al;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(final IJumpable jumpable) {
        double boost = jumpable.getBoost();
        this.behavior.setVerticalSpeed(boost);
        this.getPowerup().perform(PowerupOccasion.collision);
        if (jumpable instanceof IEnemy) {
            addExperiencePoints(((IEnemy) jumpable).getAmountOfExperience());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementBehavior.Directions getFacing() {
        return this.behavior.getFacing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPress(final Keys key) {
        this.behavior.keyPress(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyRelease(final Keys key) {
        this.behavior.keyRelease(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Keys getKeyLeft() {
        return this.keys[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Keys getKeyRight() {
        return this.keys[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeys(final Keys left, final Keys right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("A key cannot be null");
        }

        this.deregister();
        this.keys[0] = left;
        this.keys[1] = right;
        this.register();
        this.behavior.updateActions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getLegsHeight() {
        return Doodle.LEGS_HEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IPowerup getPowerup() {
        if (this.powerup != null) {
            return this.powerup;
        } else {
            return Doodle.fakePowerup;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPowerup(final IPowerup item) {
        this.getPowerup().endPowerup();
        this.powerup = item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removePowerup(final IPowerup item) {
        if (this.powerup.equals(item)) {
            this.powerup = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void register() {
        this.shootingObserver.register();
        Doodle.getServiceLocator().getInputManager().addObserver(this.getKeyLeft(), this);
        Doodle.getServiceLocator().getInputManager().addObserver(this.getKeyRight(), this);
        this.getLogger().info("The doodle registered itself as an observer of the input manager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deregister() {
        shootingObserver.deregister();
        Doodle.getServiceLocator().getInputManager().removeObserver(this.getKeyLeft(), this);
        Doodle.getServiceLocator().getInputManager().removeObserver(this.getKeyRight(), this);
        this.getLogger().info("The doodle removed itself as an observer from the input manager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        final IRenderer renderer = Doodle.getServiceLocator().getRenderer();
        final ISpriteFactory spriteFactory = Doodle.getServiceLocator().getSpriteFactory();
        final double camY = renderer.getCamera().getYPos();
        final ISprite sprite = this.getSprite();

        if (camY > this.getYPos() + sprite.getHeight()) {
            final Point arrowPoint = new Point((int) this.getXPos(), spriteFactory.getSprite(IRes.Sprites.scoreBar).getHeight());
            ISprite arrowSprite = spriteFactory.getSprite(IRes.Sprites.doodleArrow);
            final double scale = Math.sqrt((camY - getYPos() - sprite.getHeight()) / ARROW_SCALAR) + 2;
            renderer.drawSpriteHUD(arrowSprite, arrowPoint, (int) (arrowPoint.getX() / scale), (int) (arrowPoint.getY() / scale));
        } else {
            final int width = (int) (sprite.getWidth() * this.spriteScalar);
            final int height = (int) (sprite.getHeight() * this.spriteScalar);
            renderer.drawSprite(sprite, this.getPoint(), width, height);
        }

        if (!this.isAlive()) {
            Doodle.getServiceLocator().getRenderer().drawSprite(getStarSprite(),
                    new Point((int) (this.getXPos() + (STARS_OFFSET * this.spriteScalar)),
                            (int) this.getYPos()),
                    (int) (getSprite().getWidth() * this.spriteScalar * STARS_SCALAR),
                    (int) (getSprite().getHeight() * this.spriteScalar * STARS_SCALAR));
        }

        this.getPowerup().render();
        this.renderProjectiles();
    }

    /**
     * Render the projectiles this Doodle has shot.
     */
    private void renderProjectiles() {
        for (IGameObject projectile : this.projectiles) {
            projectile.render();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        starNumber++;

        this.applyMovementBehavior(delta);
        this.wrap();
        this.checkDeadPosition();
        this.updateScore();
        this.updateProjectiles(delta);
        this.getPowerup().update(delta);
    }

    /**
     * Update the projectiles this Doodle has shot.
     *
     * @param delta The time in milliseconds that has passed between the last frame and the new frame
     */
    private void updateProjectiles(final double delta) {
        int width = getServiceLocator().getConstants().getGameWidth();
        Set<IGameObject> toRemove = new HashSet<>();
        for (IGameObject projectile : projectiles) {
            if (projectile.getXPos() <= width + projectile.getHitBox()[HITBOX_TOP] && projectile.getXPos() >= -projectile.getHitBox()[HITBOX_TOP]) {
                if (projectile.getYPos() >= -projectile.getHitBox()[HITBOX_BOTTOM] + getServiceLocator().getRenderer().getCamera().getYPos()) {
                    projectile.update(delta);
                } else {
                    toRemove.add(projectile);
                }
            }
        }

        for (IGameObject projectile : toRemove) {
            projectiles.remove(projectile);
        }
    }

    /**
     * Returns the current score.
     *
     * @return the score.
     */

    public final double getScore() {
        return this.score;
    }

    /**
     * Update the active sprite.
     */
    public final void updateActiveSprite() {
        // Get the sprite array
        ISprite[] sprites = this.sprites.get(this.getFacing());

        // Get the index of the correct sprite in the array
        // Compare always returns -1, 0, 1
        int compare = Double.compare(this.getVerticalSpeed(), this.getJumpingThreshold());
        // Math.max() makes sure this is 0 or 1
        int index = Math.max(0, compare);

        // Set the sprite
        this.setSprite(sprites[index]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void increaseSpriteScalar(final double inc) {
        if (this.spriteScalar + inc > Doodle.SPRITE_SCALAR_MIN && this.spriteScalar + inc < Doodle.SPRITE_SCALAR_MAX) {
            double oldScalar = this.spriteScalar;
            this.spriteScalar += inc;

            double heightDiff = this.getSprite().getHeight() * oldScalar
                    - this.getSprite().getHeight() * this.spriteScalar;
            this.addYPos(heightDiff);

            this.updateHitBox();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getVerticalSpeed() {
        return this.behavior.getVerticalSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setVerticalSpeed(final double vSpeed) {
        this.behavior.setVerticalSpeed(vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World getWorld() {
        return this.world;
    }

    /**
     * Move the doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyMovementBehavior(final double delta) {
        this.behavior.move(delta);
    }

    /**
     * Check the dead position of the Doodle.
     */
    private void checkDeadPosition() {
        ICamera camera = getServiceLocator().getRenderer().getCamera();
        if (this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM] > camera.getYPos() + getServiceLocator().getConstants().getGameHeight()) {
            getLogger().info("The Doodle died with score " + this.score);
            this.world.endGameInstance(this.score, this.experience);
        }
    }

    /**
     * Set the behavior of the Doodle with respect to the mode.
     *
     * @param mode the behavior mode.
     */
    private void setBehavior(final Game.Modes mode) {
        switch (mode) {
            case regular:
                behavior = new RegularBehavior(getServiceLocator(), this);
                break;
            case space:
                behavior = new SpaceBehavior(getServiceLocator(), this);
                break;
            case underwater:
                behavior = new UnderwaterBehavior(getServiceLocator(), this);
                break;
            default:
                behavior = new RegularBehavior(getServiceLocator(), this);
        }
    }

    /**
     * Wrap the Doodle around the screen.
     */
    private void wrap() {
        double middle = this.getXPos() + ((this.getHitBox()[AGameObject.HITBOX_LEFT] + this.getHitBox()[AGameObject.HITBOX_RIGHT]) / 2);
        final int width = getServiceLocator().getConstants().getGameWidth();
        if (middle < 0) {
            this.addXPos(width);
        } else if (middle > width) {
            this.addXPos(-width);
        }
    }

    /**
     * Update the score for the Doodle.
     */
    private void updateScore() {
        IConstants constants = getServiceLocator().getConstants();
        double effectiveYPos = this.getYPos() - constants.getGameHeight();
        double newScore = -1 * effectiveYPos * constants.getScoreMultiplier();
        double oldScore = this.score;
        this.score = Math.max(this.score, newScore);

        this.experience += this.score - oldScore;
    }

    /**
     * Update the hitbox for the Doodle.
     */
    private void updateHitBox() {
        ISprite sprite = this.getSprite();
        int spriteWidth = (int) (sprite.getWidth() * this.spriteScalar);
        int spriteHeight = (int) (sprite.getHeight() * this.spriteScalar);
        int left = (int) (spriteWidth * WIDTH_HIT_BOX_LEFT);
        int right = (int) (spriteWidth * WIDTH_HIT_BOX_RIGHT);
        this.setHitBox(left, (int) Doodle.TOP_HITBOX_OFFSET, right, spriteHeight);
    }

    /**
     * Get the jumping threshold for the Doodle.
     *
     * @return A double representing the jumping threshold.
     */
    private double getJumpingThreshold() {
        return this.behavior.getJumpingThreshold();
    }

    /**
     * Returns the Star sprite by looking at the current starNumber.
     *
     * @return a star sprite.
     */
    private ISprite getStarSprite() {
        if (starNumber % STAR_FRAMES < FIRST_STAR_FRAME) {
            return getServiceLocator().getSpriteFactory().getSprite(IRes.Sprites.confusedStars1);
        } else if (starNumber % STAR_FRAMES < SECOND_STAR_FRAME) {
            return getServiceLocator().getSpriteFactory().getSprite(IRes.Sprites.confusedStars2);
        }
        return getServiceLocator().getSpriteFactory().getSprite(IRes.Sprites.confusedStars3);
    }

    /**
     * Adds a projectile to the Set with projectiles.
     *
     * @param projectile the projectile that has to be added.
     */
    @Override
    public final void addProjectile(final IGameObject projectile) {
        this.projectiles.add(projectile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeProjectile(final IGameObject projectile) {
        this.projectiles.remove(projectile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<IGameObject> getProjectiles() {
        return this.projectiles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExperiencePoints(final double extraAmountOfExperience) {
        this.experience += extraAmountOfExperience;
    }

}
