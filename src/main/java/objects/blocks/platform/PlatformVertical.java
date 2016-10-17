package objects.blocks.platform;

import system.IServiceLocator;

/**
 * The platform decorator used to describe vertical movement.
 */
/* package */ final class PlatformVertical extends PlatformDecorator implements IPlatform {

    /**
     * One fifth.
     */
    private static final double ONE_FIFTH = 0.2d;
    /**
     * Fifty-fifty chance.
     */
    private static final double FIFTY_FIFTY = 0.5d;
    /**
     * Moving left speed.
     */
    private static final int MOVING_SPEED = 2;

    /**
     * The speed of the moving platform.
     */
    private int speed = 2;

    /**
     * Vertical moving platform decorator constructor.
     *
     * @param sL       The serviceLocator.
     * @param platform The encapsulated platform.
     */
    /* package */ PlatformVertical(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        this.getContained().setSprite(sL.getSpriteFactory().getPlatformSpriteVertical());
        this.getContained().getProps().put(Platform.PlatformProperties.movingVertically, 1);

        this.speed = (sL.getCalc().getRandomDouble(1) < FIFTY_FIFTY) ? MOVING_SPEED : -MOVING_SPEED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        int distance = (int) (this.getServiceLocator().getConstants().getGameHeight() * ONE_FIFTH);

        if (this.getOffset() > distance) {
            this.speed = -MOVING_SPEED;
        } else if (this.getOffset() < -distance) {
            this.speed = MOVING_SPEED;
        }

        this.setOffset(this.getOffset() + this.speed);
        this.setYPos(this.getYPos() + this.speed);
        this.getContained().update(delta);
    }

}
