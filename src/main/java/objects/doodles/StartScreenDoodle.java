package objects.doodles;

import input.IInputManager;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
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
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
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
     * @param sL The service locator
     */
     /* package */ StartScreenDoodle(final IServiceLocator sL) {
        super(sL, sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2, sL.getSpriteFactory().getDoodleSprite(Directions.Right)[0]);
        this.setHitBox((int) (getSprite().getWidth() * widthHitboxLeft), (int) (getSprite().getHeight() * 0.25), (int) (getSprite().getWidth() * widthHitboxRight), getSprite().getHeight());

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

}
