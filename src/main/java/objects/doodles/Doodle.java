package objects.doodles;

import input.Keys;
import objects.AGameObject;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.DoodleBehavior.UnderwaterBehavior;
import objects.powerups.IPowerup;
import rendering.ICamera;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Doodle.
 */
@SuppressWarnings({"checkstyle:designforextension"})
public class Doodle extends AGameObject implements IDoodle {

    /**
     * The relative center of the camera on the y axis.
     */
    private static final double CAMERA_POS = 3 / 7d;
    /**
     * The ratio of Doodle to offset the frame size vs panel size.
     */
    private static final double DEAD_OFFSET = 1.5d;
    /**
     * The height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     */
    private static final double LEGS_HEIGHT = 0.8;
    /**
     * Where the hitbox of the doodle starts in relation to the sprite height.
     */
    private static final double HEIGHT_HIT_BOX_TOP = 0.5;
    /**
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_LEFT = 0.3;
    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_RIGHT = .8;
    /**
     * Gives true if the doodle has been hit by an enemy.
     */
    private boolean hitByEnemy = false;

    /**
     * Keeps the number of the star animation when killed by an enemy.
     */
    private int starNumber = 0;

    /**
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private ISprite[][] spritePack;
    /**
     * The current score of the doodle.
     */
    private double score;
    /**
     * All the passives the can Doodle have.
     */
    private IPowerup powerup;
    /**
     * The world the Doodle lives in.
     */
    private final World world;
    /**
     * Describes the movement behavior of the doodle.
     */
    private MovementBehavior behavior;
    /**
     * The scalar for the Doodle sprite.
     */
    private double spriteScalar = 1d;
    /**
     * The scalar for the Stars sprite.
     */
    private final double starsScalar = 0.7d;
    /**
     * The scalar for the Stars sprite.
     */
    private final int starsOffset = 20;

    /**
     * Doodle constructor.
     *
     * @param sL The service locator.
     * @param w The world the Doodle lives in.
     */
     /* package */ Doodle(final IServiceLocator sL, final World w) {
        super(sL,
                sL.getConstants().getGameWidth() / 2,
                sL.getConstants().getGameHeight() / 2,
                sL.getSpriteFactory().getDoodleSprite(MovementBehavior.Directions.Right)[0],
                Doodle.class);

         this.setHitBox(
                 (int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT),
                 (int) (getSprite().getHeight() * HEIGHT_HIT_BOX_TOP),
                 (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT),
                 getSprite().getHeight());

        this.world = w;
        setBehavior(Game.getMode());
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.spritePack = new ISprite[2][2];
        this.spritePack[0] = spriteFactory.getDoodleSprite(MovementBehavior.Directions.Left);
        this.spritePack[1] = spriteFactory.getDoodleSprite(MovementBehavior.Directions.Right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(final IJumpable jumpable) {
        behavior.setVerticalSpeed(jumpable.getBoost());

        double boost = jumpable.getBoost();
        behavior.setVerticalSpeed(boost);

        if (this.powerup != null) {
            this.powerup.perform("collision");
        }
    }

    /**
     * {@inheritDoc}
     */
    public final IPowerup getPowerup() {
        return this.powerup;
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
    public final double getLegsHeight() {
        return LEGS_HEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPowerup(final IPowerup item) {
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
    public final void setSprite(final MovementBehavior.Directions direction, final boolean falling) {
        if (direction == MovementBehavior.Directions.Left) {
            setSprite(this.spritePack[0][falling ? 1 : 0]);
        }
        if (direction == MovementBehavior.Directions.Right) {
            setSprite(this.spritePack[1][falling ? 1 : 0]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getVerticalSpeed() {
        return behavior.getVerticalSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setVerticalSpeed(final double vSpeed) {
        behavior.setVerticalSpeed(vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPress(final Keys key) {
        behavior.keyPress(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyRelease(final Keys key) {
        behavior.keyRelease(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        ISprite sprite = this.getSprite();
        getServiceLocator().getRenderer().drawSprite(sprite,
                (int) this.getXPos(),
                (int) this.getYPos(),
                (int) (sprite.getWidth() * this.spriteScalar),
                (int) (sprite.getHeight() * this.spriteScalar));

        if (this.isHitByEnemy()) {
            getServiceLocator().getRenderer().drawSprite(getStarSprite(),
                    (int) (this.getXPos() + (this.starsOffset * this.spriteScalar)),
                    (int) this.getYPos(),
                    (int) (sprite.getWidth() * this.spriteScalar * this.starsScalar),
                    (int) (sprite.getHeight() * this.spriteScalar * this.starsScalar));
            starNumber++;
        }

        if (this.powerup != null) {
            this.powerup.render();
        }
    }

    private ISprite getStarSprite() {
        if (starNumber % 9 < 3) {
            return getServiceLocator().getSpriteFactory().getStarSprite1();
        }
        else if (starNumber % 9 < 6) {
            return getServiceLocator().getSpriteFactory().getStarSprite2();
        }
        return getServiceLocator().getSpriteFactory().getStarSprite3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        this.applyMovementBehavior(delta);
        this.wrap();
        this.checkHighPosition();
        this.checkDeadPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void register() {
        getServiceLocator().getInputManager().addObserver(this);
        getLogger().info("The doodle registered itself as an observer of the input manager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void increaseSpriteScalar(final double inc) {
        this.spriteScalar += inc;

        ISprite sprite = this.getSprite();
        int width = (int) (sprite.getWidth() * this.spriteScalar);
        int height = (int) (sprite.getHeight() * this.spriteScalar);
        this.setHitBox(0, 0, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deregister() {
        getServiceLocator().getInputManager().removeObserver(this);
        getLogger().info("The doodle removed itself as an observer from the input manager");
    }

    /**
     * Move the doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyMovementBehavior(final double delta) {
        behavior.move(delta);
    }

    /**
     * Check the height position of the Doodle.
     */
    private void checkHighPosition() {
        ICamera camera = getServiceLocator().getRenderer().getCamera();
        final int height = getServiceLocator().getConstants().getGameHeight();

        final double yThreshold = camera.getYPos() + height * CAMERA_POS;
        if (getYPos() < yThreshold) {
            score += (yThreshold - getYPos()) * getServiceLocator().getConstants().getScoreMultiplier();
            camera.setYPos(getYPos() - height * CAMERA_POS);
        }
    }

    /**
     * Check the dead position of the Doodle.
     */
    private void checkDeadPosition() {
        ICamera camera = getServiceLocator().getRenderer().getCamera();
        if (getYPos() > camera.getYPos() + getServiceLocator().getConstants().getGameHeight() - DEAD_OFFSET * getHitBox()[HITBOX_BOTTOM]) {
            getLogger().info("The Doodle died with score " + this.score);
            this.world.endGameInstance(this.score);
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
     * {@inheritDoc}
     */
    @Override
    public boolean isHitByEnemy() {
        return hitByEnemy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHitByEnemy(boolean isHit) {
        hitByEnemy = isHit;
    }

}
