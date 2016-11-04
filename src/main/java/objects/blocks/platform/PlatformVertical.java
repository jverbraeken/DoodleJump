package objects.blocks.platform;

import resources.IRes;
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
    private double speed = 2;

    /**
     * Vertical moving platform decorator constructor.
     *
     * @param sL       The serviceLocator.
     * @param platform The encapsulated platform.
     */
    /* package */ PlatformVertical(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        this.getContained().setSprite(sL.getSpriteFactory().getSprite(IRes.Sprites.platformVertical));
        this.getContained().getProps().put(Platform.PlatformProperties.movingVertically, 1);

        this.speed = MOVING_SPEED;
        double newOffset = sL.getCalc().getRandomDouble(this.getServiceLocator().getConstants().getGameHeight() * ONE_FIFTH);
        this.setOffset(sL.getCalc().getRandomDouble(1d) < FIFTY_FIFTY ? newOffset : -newOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        double distance = this.getServiceLocator().getConstants().getGameHeight() * ONE_FIFTH;

        if (Math.abs(this.getOffset()) > distance) {
            this.speed = -this.speed;
        }

        this.setOffset(this.getOffset() + this.speed);
        this.setYPos(this.getYPos() + this.speed);
        this.getContained().update(delta);
    }

}
