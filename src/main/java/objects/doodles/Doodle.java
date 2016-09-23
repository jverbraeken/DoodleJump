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
import scenes.World;
import system.Game;
import system.IServiceLocator;

public final class Doodle extends AGameObject implements IDoodle {
    /**
     * The height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     */
    private final double legsHeight = 0.8;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private double hSpeedLimit = 6d;
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
    private double hAcceleration = .5d;
    /**
     * The sprite pack for the Doodle, containing all sprites for one direction.
     */
    private ISprite[] spritePack;
    /**
     * The direction the Doodle is moving towards.
     */
    private directions moving;
    /**
     * The direction the Doodle is facing.
     */
    private directions facing;
    /**
     * The current score of the doodle
     */
    private double score;

    /**
     * Doodle constructor.
     *
     * @param serviceLocator The service locator
     */
     /* package */ Doodle(final IServiceLocator serviceLocator) {
        super(serviceLocator, serviceLocator.getConstants().getGameWidth() / 2, serviceLocator.getConstants().getGameHeight() / 2, serviceLocator.getSpriteFactory().getDoodleSprite(directions.right)[0]);
        this.setHitBox(getSprite().getWidth() / 3, (int) (getSprite().getHeight() * 0.25), 2 * getSprite().getWidth() / 3, getSprite().getHeight());

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(directions.right);

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
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
    public void collidesWith(IDoodle doodle) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getVSpeed() {
        return this.vSpeed;
    }

    @Override
    public void setVerticalSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
    }

    @Override
    public double getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPress(int keyCode) {
        if (this.leftPressed(keyCode)) {
            this.moving = directions.left;
            this.facing = directions.left;
        } else if (this.rightPressed(keyCode)) {
            this.moving = directions.right;
            this.facing = directions.right;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyRelease(int keyCode) {
        if (this.leftPressed(keyCode) && this.moving == directions.left) {
            this.moving = null;
        } else if (this.rightPressed(keyCode) && this.moving == directions.right) {
            this.moving = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(IBlock block) {
    }

    @Override
    public double getLegsHeight() {
        return legsHeight;
    }

    private void move(double delta) {
        moveHorizontally(delta);
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally(double delta) {
        if (moving == directions.left) {
            if (this.hSpeed > -this.hSpeedLimit) {
                this.hSpeed -= this.hAcceleration;
            }
        } else if (moving == directions.right) {
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
     * Check if the left key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for left is pressed.
     */
    private boolean leftPressed(int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowLeft) ||
                keyCode == KeyCode.getKeyCode(Keys.a);
    }

    /**
     * Check if the right key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for right is pressed.
     */
    private boolean rightPressed(int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowRight) ||
                keyCode == KeyCode.getKeyCode(Keys.d);
    }

    /**
     * Wrap the Doodle around the screen.
     */
    private void wrap() {
        double middle = this.getXPos() + this.getHitBox()[AGameObject.HITBOX_RIGHT] / 2;
        final int width = serviceLocator.getConstants().getGameWidth();
        if (middle < 0) {
            this.addXPos(width);
        } else if (middle > width) {
            this.addXPos(-width);
        }
    }

    private void animate(double delta) {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
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
        this.vSpeed += World.gravityAcceleration;
        addYPos(this.vSpeed);
    }

    private void checkHighPosition() {
        ICamera camera = serviceLocator.getRenderer().getCamera();
        final int height = serviceLocator.getConstants().getGameHeight();
        if (getYPos() < camera.getYPos() + height / 2) {
            score += (camera.getYPos() + height / 2 - getYPos()) * World.SCOREMULTIPLIER;
            camera.setYPos(getYPos() - height / 2);
        }
    }

    private void checkDeadPosition() {
        ICamera camera = serviceLocator.getRenderer().getCamera();
        if (getYPos() > camera.getYPos() + serviceLocator.getConstants().getGameHeight() - getHitBox()[HITBOX_BOTTOM]) {
            Game.setAlive(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(IJumpable jumpable) {
        this.vSpeed = jumpable.getBoost();
    }

}
