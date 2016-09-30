package objects.doodles;

import input.IInputManager;
import input.Keys;
import logging.ILogger;
import objects.AGameObject;
import objects.IJumpable;
import objects.powerups.IPassive;
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
 * This class describes the behaviour of the Doodle.
 */
public class Doodle extends AGameObject implements IDoodle {

    /**
     * The logger for the Game class.
     */
    private static final ILogger LOGGER = getServiceLocator().getLoggerFactory().createLogger(Doodle.class);
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
     * Where the hitbox of the doodle starts in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_LEFT = .3;
    /**
     * Where the hitbox of the doodle ends in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_RIGHT = .7;

    /**
     * The sprite pack for the Doodle, containing all Sprites for one direction.
     */
    private ISprite[] spritePack;
    /**
     * The current score of the doodle.
     */
    private double score;
    /**
     * All the passives the can Doodle have.
     */
    private IPassive passive;
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
        super(sL,
                sL.getConstants().getGameWidth() / 2,
                sL.getConstants().getGameHeight() / 2,
                sL.getSpriteFactory().getDoodleSprite(MovementBehavior.Directions.Right)[0],
                Doodle.class);

        this.setHitBox(
                (int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT),
                getSprite().getHeight(),
                (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT),
                getSprite().getHeight());

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
    public final void collide(final IJumpable jumpable) {
        behavior.setVerticalSpeed(jumpable.getBoost());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) { }

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

    /** {@inheritDoc} */
    @Override
    public void setPassive(final IPassive item) {
        this.passive = item;
    }

    /** {@inheritDoc} */
    @Override
    public void removePassive(final IPassive item) {
        if (this.passive.equals(item)) {
            this.passive = null;
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
    public final void keyPress(final Keys key) {
        behavior.keyPress(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final Keys key) {
        behavior.keyRelease(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
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
    public final void update(final double delta) {
        this.applyMovementBehavior(delta);
        this.wrap();
        this.checkHighPosition();
        this.checkDeadPosition();
    }

    /** {@inheritDoc} */
    @Override
    public final ISprite[] getSpritePack() {
        return spritePack;
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

    /** {@inheritDoc} */
    @Override
    public final void setSpritePack(final ISprite[] doodleSprite) {
        this.spritePack = doodleSprite;
    }

    /**
     * Move the doodle.
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
            Game.endGameInstance(this.score);
        }
    }

    /**
     * Set the behavior of the doodle with respect to the mode.
     *
     * @param mode The game mode.
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

}
