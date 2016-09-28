package objects.doodles;

import input.IInputManager;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.MovementBehavior;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle in the startscreen.
 */
/* package */ class StartScreenDoodle extends AGameObject implements IDoodle {

    /**
     * Boost reduction specifically for the startscreen Doodle.
     */
    private static final double BOOST_REDUCTION = 2d;
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
     *  Describes the movement behavior of the doodle.
     */
    private MovementBehavior behavior;

    /**
     * Doodle constructor.
     * @param sL The service locator
     */
    /* package */ StartScreenDoodle(final IServiceLocator sL) {
        super(sL, sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2, sL.getSpriteFactory().getDoodleSprite(MovementBehavior.Directions.Right)[0]);
        this.setHitBox((int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT), (int) (getSprite().getHeight() * 0.25), (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT), getSprite().getHeight());

        behavior = new RegularBehavior(sL, this);
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
        this.applyGravity(delta);
    }

    /** {@inheritDoc} */
    @Override
    public double getVerticalSpeed() {
        return 0d;
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public double getScore() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public final void keyPress(final int keyCode) {
    }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) {
    }

    /** {@inheritDoc} */
    @Override
    public void collide(IBlock block) {
    }

    /** {@inheritDoc} */
    @Override
    public void collide(IJumpable jumpable) {
        this.vSpeed = jumpable.getBoost() + BOOST_REDUCTION;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
    }

    /** {@inheritDoc} */
    @Override
    public double getLegsHeight() {
        return 0d;
    }

    /**
     * Apply gravity to the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(double delta) {
        this.vSpeed += sL.getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    /** {@inheritDoc} */
    @Override
    public ISprite[] getSpritePack() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void setSpritePack(ISprite[] sprites) {
    }

}
