package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.powerups.IPowerup;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle.
 */
public class Doodle extends AGameObject implements IDoodle {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;

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
     * The sprite for the Doodle.
     */
    private ISprite sprite;
    /**
     * The direction the Doodle is moving towards.
     */
    private Directions moving;
    /**
     * The direction the Doodle is facing.
     */
    private Directions facing;

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
     * @param sL The service locator.
     */
     /* package */ Doodle(final IServiceLocator sL) {
        Doodle.serviceLocator = sL;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(Directions.Right);
        this.sprite = this.spritePack[0];

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);

        this.setXPos(Game.WIDTH / 2);
        this.setYPos(Game.HEIGHT / 2);
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());

        double[] hit = {getWidth() * widthHitboxLeft, 0d, getWidth() * widthHitboxRight, (double) getHeight()};
        this.setHitBox(hit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animate() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(this.facing);

        // If the Doodle moves up quickly shorten its legs
        if (this.vSpeed < doodleIsJumping) {
            this.sprite = this.spritePack[1];
        } else {
            this.sprite = this.spritePack[0];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean collide(final IGameObject collidee) {
        if (collidee == null) {
            throw new IllegalArgumentException("collidee cannot be null");
        }

        // If one of these boolean turns false there is no intersection possible between 2 rectangles
        if (this.getXPos() + getHitBox()[hitBoxLeft] < collidee.getXPos() + collidee.getWidth()
                && this.getXPos() + getHitBox()[hitBoxRight] > collidee.getXPos()
                && this.getYPos() + getHitBox()[hitBoxTop] < collidee.getYPos() + collidee.getHeight()
                && this.getYPos() + getHitBox()[hitBoxBottom] > collidee.getYPos()) {

            if (collidee instanceof IPlatform || collidee instanceof IPowerup) {
                return this.getYPos() + this.getHeight() < collidee.getYPos() + collidee.getHeight();
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void move() {
        this.moveHorizontally();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        this.animate();
        this.move();
        this.wrap();
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
     *
     * Set the vertical speed of the Doodle.
     * @param v the new speed.
     */
    public final void setVerticalSpeed(final double v) {
        this.vSpeed = v;
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
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
        double middle = this.getXPos() + this.getWidth() / 2;
        if (middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if (middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
        }
    }

}
