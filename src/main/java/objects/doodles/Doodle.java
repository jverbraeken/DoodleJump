package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
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
     * The height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     */
    private static final double legsHeight = 0.8;

    /**
     * Standard speed limit for the Doodle.
     */
    private static final double standardSpeedLimit = 6d;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private static double hSpeedLimit = standardSpeedLimit;
    /**
     * Current horizontal speed for the Doodle.
     */
    private static double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private static double vSpeed = 0d;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private static final double hAcceleration = .5d;
    /**
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private static final double widthHitboxLeft = .3;
    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private static final double widthHitboxRight = .7;
    /**
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private ISprite[] spritePack;
    /**
     * The direction the Doodle is moving towards.
     */
    private Directions moving;
    /**
     * The direction the Doodle is facing.
     */
    private Directions facing;
    /**
     * The current score of the doodle
     */
    private double score;

    /**
     * Doodle constructor.
     *
     * @param sL The service locator
     */
     /* package */ Doodle(final IServiceLocator sL) {
        super(sL, sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2, sL.getSpriteFactory().getDoodleSprite(Directions.Right)[0]);
        this.setHitBox((int) (getSprite().getWidth() * widthHitboxLeft), (int) (getSprite().getHeight() * 0.25), (int) (getSprite().getWidth() * widthHitboxRight), getSprite().getHeight());

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(Directions.Right);

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
        this.move(delta);
        this.wrap();
        this.applyGravity(delta);
        this.checkHighPosition();
        this.checkDeadPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getVerticalSpeed() {
        return this.vSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerticalSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
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
    public final void keyPress(final int keyCode) {
        if (this.leftPressed(keyCode)) {
            this.moving = Directions.Left;
            this.facing = Directions.Left;
        } else if (this.rightPressed(keyCode)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final int keyCode) {
        if (this.leftPressed(keyCode) && this.moving == Directions.Left) {
            this.moving = null;
        } else if (this.rightPressed(keyCode) && this.moving == Directions.Right) {
            this.moving = null;
        }
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
    public void collide(IJumpable jumpable) {
        this.vSpeed = jumpable.getBoost();
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
        return legsHeight;
    }

    /**
     * TODO: ADD JAVADOC
     * @param delta The difference in time between the current frame and the last frame
     */
    private void move(final double delta) {
        moveHorizontally(delta);
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally(final double delta) {
        if (moving == Directions.Left) {
            if (this.hSpeed > -this.hSpeedLimit) {
                this.hSpeed -= this.hAcceleration;
            }
        } else if (moving == Directions.Right) {
            if (this.hSpeed < this.hSpeedLimit) {
                this.hSpeed += this.hAcceleration;
            }
        } else {
            if (this.hSpeed < 0) {
                this.hSpeed += this.hAcceleration;
            } else if (this.hSpeed > 0) {
                this.hSpeed -= this.hAcceleration;
            }
        }

        this.addXPos((int) this.hSpeed);
    }

    /**
     * Check if the Left key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for Left is pressed.
     */
    private boolean leftPressed(final int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowLeft)
                || keyCode == KeyCode.getKeyCode(Keys.a);
    }

    /**
     * Check if the Right key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for Right is pressed.
     */
    private boolean rightPressed(final int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowRight)
                || keyCode == KeyCode.getKeyCode(Keys.d);
    }

    /**
     * Wrap the Doodle around the screen.
     */
    private void wrap() {
        double middle = this.getXPos() + this.getHitBox()[AGameObject.HITBOX_RIGHT] / 2;
        final int width = getServiceLocator().getConstants().getGameWidth();
        if (middle < 0) {
            this.addXPos(width);
        } else if (middle > width) {
            this.addXPos(-width);
        }
    }

    private void animate(double delta) {
        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(this.facing);

        // If the Doodle moves up quickly shorten its legs
        if (this.vSpeed < -15) {
            setSprite(this.spritePack[1]);
        } else {
            setSprite(this.spritePack[0]);
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private void applyGravity(double delta) {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    private void checkHighPosition() {
        ICamera camera = getServiceLocator().getRenderer().getCamera();
        final int height = getServiceLocator().getConstants().getGameHeight();
        final double yThreshold = camera.getYPos() + (double) height / 2d;
        if (getYPos() < yThreshold) {
            score += (yThreshold - getYPos()) * getServiceLocator().getConstants().getScoreMultiplier();
            camera.setYPos(getYPos() - height / 2);
        }
    }

    private void checkDeadPosition() {
        ICamera camera = getServiceLocator().getRenderer().getCamera();
        if (getYPos() > camera.getYPos() + getServiceLocator().getConstants().getGameHeight() - getHitBox()[HITBOX_BOTTOM]) {
            Game.setAlive(false);
        }
    }

}
