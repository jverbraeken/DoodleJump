package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.DoodleBehavior.movementBehavior;
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
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private ISprite[] spritePack;

    /**
     * The current score of the doodle
     */
    private double score;
    /**
     *  Describes the movement behavior of the doodle.
     */
    private movementBehavior behavior;
    /**
     * Doodle constructor.
     * @param sL The service locator
     */
     /* package */ Doodle(final IServiceLocator sL) {

        super(sL, sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2, sL.getSpriteFactory().getDoodleSprite(movementBehavior.Directions.Right)[0]);
        this.setHitBox((int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT), (int) (getSprite().getHeight() * 0.25), (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT), getSprite().getHeight());

        behavior = new SpaceBehavior(this, sL);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(movementBehavior.Directions.Right);

        IInputManager inputManager = sL.getInputManager();
        inputManager.addObserver(this);
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        sL.getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) {
        this.animate(delta);
        this.applyMovementBehavior(delta);
        this.wrap();
        this.checkHighPosition();
        this.checkDeadPosition();
    }

    /** {@inheritDoc} */
    @Override
    public double getVerticalSpeed() {
        return behavior.getVerticalSpeed();
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalSpeed(double vSpeed) {
        behavior.setVerticalSpeed(vSpeed);
    }

    /** {@inheritDoc} */
    @Override
    public double getScore() {
        return score;
    }

    /** {@inheritDoc} */
    @Override
    public void collide(IBlock block) {
    }

    /** {@inheritDoc} */
    @Override
    public void collide(IJumpable jumpable) {
        behavior.setVerticalSpeed(jumpable.getBoost());
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
    }

    /** {@inheritDoc} */
    @Override
    public double getLegsHeight() {
        return LEGS_HEIGHT;
    }

    /**
     * Move the doodle.
     * @param delta Delta time since previous animate.
     */
    private void applyMovementBehavior(final double delta) {
        behavior.move(delta);
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

    /**
     * Animate the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void animate(double delta) {
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(this.behavior.getFacing());

        // If the Doodle moves up quickly shorten its legs
        if (behavior.getVerticalSpeed() < -15) {
            setSprite(this.spritePack[1]);
        } else {
            setSprite(this.spritePack[0]);
        }
    }

    /**
     * Check the height position of the Doodle.
     */
    private void checkHighPosition() {
        ICamera camera = sL.getRenderer().getCamera();
        final int height = sL.getConstants().getGameHeight();
        if (getYPos() < camera.getYPos() + height / 2) {
            score += (camera.getYPos() + height / 2 - getYPos()) * sL.getConstants().getScoreMultiplier();
            camera.setYPos(getYPos() - height / 2);
        }
    }

    /**
     * Check the dead position of the Doodle.
     */
    private void checkDeadPosition() {
        ICamera camera = sL.getRenderer().getCamera();
        if (getYPos() > camera.getYPos() + sL.getConstants().getGameHeight() - getHitBox()[HITBOX_BOTTOM]) {
            Game.setAlive(false);
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
}
