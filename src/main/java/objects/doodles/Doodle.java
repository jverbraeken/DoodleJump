package objects.doodles;

import input.IInputManager;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.DoodleBehavior.UnderwaterBehavior;
import rendering.ICamera;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle.
 */
public class Doodle extends AGameObject implements IDoodle {

    /**
     * The relative center of the camera on the y axis.
     */
    private static final double CAMERA_POS = 3 / 7d;
    /**
     * The ratio of doodle to offset the frame size vs panel size
     */
    private static final double DEAD_OFFSET = 1.5d;
    /**
     * The height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     */
    private final double LEGS_HEIGHT = 0.8;
    /**
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private final double WIDTH_HIT_BOX_LEFT = .3;
    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private final double WIDTH_HIT_BOX_RIGHT = .7;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private ISprite[] spritePack;
    /**
     * The current score of the doodle
     */
    private double score;
    /**
     * Describes the movement behavior of the doodle.
     */
    private MovementBehavior behavior;

    /**
     * Doodle constructor.
     *
     * @param sL The service locator
     */
     /* package */ Doodle(final IServiceLocator sL) {

        super(sL, sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2, sL.getSpriteFactory().getDoodleSprite(MovementBehavior.Directions.Right)[0], Doodle.class);
        this.setHitBox((int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT), (int) (getSprite().getHeight() * 0.25), (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT), getSprite().getHeight());

        setBehavior(Game.getMode());
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(MovementBehavior.Directions.Right);

        IInputManager inputManager = sL.getInputManager();
        inputManager.addObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double delta) {
        this.animate(delta);
        this.applyMovementBehavior(delta);
        this.wrap();
        this.checkHighPosition();
        this.checkDeadPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getVerticalSpeed() {
        return behavior.getVerticalSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerticalSpeed(double vSpeed) {
        behavior.setVerticalSpeed(vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(IBlock block) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void collide(IJumpable jumpable) {
        behavior.setVerticalSpeed(jumpable.getBoost());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(IDoodle doodle) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLegsHeight() {
        return LEGS_HEIGHT;
    }

    /**
     * Move the doodle.
     *
     * @param delta The difference in time between the current frame and the last frame
     */
    private void applyMovementBehavior(final double delta) {
        behavior.move(delta);
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
     * Animate the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void animate(double delta) {
        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(this.behavior.getFacing());

        // If the Doodle moves up quickly shorten its legs
        if (behavior.getVerticalSpeed() < -15) {
            setSprite(this.spritePack[1]);
        } else {
            setSprite(this.spritePack[0]);
        }
    }

    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(double delta) {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
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
            Game.endGameInstance(this.score);
        }
    }


    @Override
    public void keyPress(int keyCode) {
        behavior.keyPress(keyCode);
    }

    @Override
    public void keyRelease(int keyCode) {
        behavior.keyRelease(keyCode);
    }

    /**
     * Set the behavior of the doodle with respect to the mode.
     *
     * @param mode the behavior.
     */
    private void setBehavior(Game.Modes mode) {
        switch (mode) {
            case regular:
                behavior = new RegularBehavior(this, getServiceLocator());
                break;
            case space:
                behavior = new SpaceBehavior(this, getServiceLocator());
                break;
            case underwater:
                behavior = new UnderwaterBehavior(this, getServiceLocator());
                break;
            default:
                behavior = new RegularBehavior(this, getServiceLocator());
        }
    }
}
