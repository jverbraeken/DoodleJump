package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.ICamera;
import scenes.World;
import system.Game;
import system.IServiceLocator;

public class Doodle extends AGameObject implements IDoodle {
    //TODO check final/static
    private static IServiceLocator serviceLocator;
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
    private final ICamera camera;

    /**
     * Doodle constructor.
     *
     * @param serviceLocator The service locator
     * @param camera         The camera that moves if the doodle jumps high
     */
     /* package */ Doodle(IServiceLocator serviceLocator, ICamera camera) {
        super(Game.WIDTH / 2, Game.HEIGHT / 2, serviceLocator.getSpriteFactory().getDoodleSprite(directions.right)[0]);
        this.setHitBox(getSprite().getWidth() / 3, (int) (getSprite().getHeight() * 0.25), 2 * getSprite().getWidth() / 3, getSprite().getHeight());
        Doodle.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(directions.right);

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);

        this.camera = camera;
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
        this.checkCollisions();
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
        if (middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if (middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
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
    private void checkCollisions() {
        /*if (this.doodle.getVSpeed() > 0) {
            for (IBlock block : blocks) {
                //TODO check for the collision
                //if (this.doodle.checkCollission(block)) {
                Set<IGameObject> elements = block.getElements();
                for (IGameObject element : elements) {
                    if (this.doodle.checkCollission(element)) {
                        if (this.doodle.getYPos() + this.doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] * this.doodle.getLegsHeight() < element.getYPos()) {
                            element.collidesWith(this.doodle);
                        }
                    }
                }
                //}
            }
        }*/
    }

    /**
     * TODO: Add JavaDoc
     */
    private void applyGravity(double delta) {
        this.vSpeed += World.gravityAcceleration;
        addYPos(this.vSpeed);
        if (vSpeed < 0d && getYPos() < .5d * Game.HEIGHT - getHitBox()[AGameObject.HITBOX_BOTTOM]) {

            for (IBlock e : blocks) {
                e.addYPos(-doodle.getVSpeed());
                score -= doodle.getVSpeed() * SCOREMULTIPLIER;
            }
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
