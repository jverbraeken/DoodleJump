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
    private final double legsHeight = 0.8;

    /**
     * Standard speed limit for the Doodle.
     */
    private final double standardSpeedLimit = 6d;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private double hSpeedLimit = standardSpeedLimit;
    /**
     * Current horizontal speed for the Doodle.
     */
    private double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private final double hAcceleration = .5d;
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
     * Enumerator of the Left side of the hitbox.
     */
    private final int hitBoxLeft = 0;
    /**
     * Enumerator of the top side of the hitbox.
     */
    private final int hitBoxTop = 1;
    /**
     * Enumerator of the Right side of the hitbox.
     */
    private final int hitBoxRight = 2;
    /**
     * Enumerator of the bottom side of the hitbox.
     */
    private final int hitBoxBottom = 3;

    /**
     * The speed at which we know the doodle is jumping.
     * this is used for the pulling up legs animation.
     */
    private final int doodleIsJumping = -15;

    /**
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private final double widthHitboxLeft = .3;

    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private final double widthHitboxRight = .7;

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
        sL.getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
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
     * @param delta
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
        final int width = sL.getConstants().getGameWidth();
        if (middle < 0) {
            this.addXPos(width);
        } else if (middle > width) {
            this.addXPos(-width);
        }
    }

    private void animate(double delta) {
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
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
        this.vSpeed += sL.getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    private void checkHighPosition() {
        ICamera camera = sL.getRenderer().getCamera();
        final int height = sL.getConstants().getGameHeight();
        if (getYPos() < camera.getYPos() + height / 2) {
            score += (camera.getYPos() + height / 2 - getYPos()) * super.sL.getConstants().getScoreMultiplier();
            camera.setYPos(getYPos() - height / 2);
        }
    }

    private void checkDeadPosition() {
        ICamera camera = sL.getRenderer().getCamera();
        if (getYPos() > camera.getYPos() + sL.getConstants().getGameHeight() - getHitBox()[HITBOX_BOTTOM]) {
            Game.setAlive(false);
        }
    }

}
