package objects.doodles;

import input.IInputManager;
import objects.AGameObject;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle in the StartScreen.
 */
/* package */ class StartScreenDoodle extends AGameObject implements IDoodle {

    /**
     * Boost reduction specifically for the StartScreen Doodle.
     */
    private static final double BOOST_REDUCTION = 2d;
    /**
     * Where the hitbox of the Doodle starts in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_LEFT = .3;
    /**
     * Where the hitbox of the Doodle ends in relation to the sprite width.
     */
    private static final double WIDTH_HIT_BOX_RIGHT = .7;

    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;

    /**
     * Doodle constructor.
     *
     * @param sL The ServiceLocator.
     */
    /* package */ StartScreenDoodle(final IServiceLocator sL) {
        super(sL,
                sL.getConstants().getGameWidth() / 2,
                sL.getConstants().getGameHeight() / 2,
                sL.getSpriteFactory().getDoodleSprite(MovementBehavior.Directions.Right)[0]);

        this.setHitBox(
                (int) (getSprite().getWidth() * WIDTH_HIT_BOX_LEFT),
                getSprite().getHeight(),
                (int) (getSprite().getWidth() * WIDTH_HIT_BOX_RIGHT),
                getSprite().getHeight());

        IInputManager inputManager = sL.getInputManager();
        inputManager.addObserver(this);
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void update(final double delta) {
        this.applyGravity(delta);
    }

    /** {@inheritDoc} */
    @Override
    public double getVerticalSpeed() {
        return vSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalSpeed(final double speed) {
        this.vSpeed = speed;
    }

    /** {@inheritDoc} */
    @Override
    public double getScore() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public final void keyPress(final int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public void collide(final IJumpable jumpable) {
        this.vSpeed = jumpable.getBoost() + BOOST_REDUCTION;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
    }

    /** {@inheritDoc} */
    @Override
    public double getLegsHeight() {
        return 0d;
    }

    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(final double delta) {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

}
