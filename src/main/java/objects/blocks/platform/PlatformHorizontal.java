package objects.blocks.platform;

import system.IServiceLocator;

/**
 * The platform decorator to support horizontal movement.
 */
/* package */ final class PlatformHorizontal extends PlatformDecorator implements IPlatform {

    /**
     * Fifty-fifty chance.
     */
    private static final double FIFTY_FIFTY = 0.5d;

    /**
     * The speed of the moving platform.
     */
    private int speed = 2;

    /**
     * Horizontal moving platform decorator constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    /* package */PlatformHorizontal(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        this.getContained().setSprite(sL.getSpriteFactory().getPlatformSpriteHorizontal());
        this.getContained().getProps().put(Platform.PlatformProperties.movingVertically, 1);

        this.speed = (sL.getCalc().getRandomDouble(1) < FIFTY_FIFTY) ? 2 : -2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        int gameWidth = getServiceLocator().getConstants().getGameWidth();
        if (this.getXPos() + this.getSprite().getWidth() > gameWidth) {
            this.speed = -2;
        } else if (this.getXPos() < 0) {
            this.speed = 2;
        }

        this.setXPos(this.getXPos() + this.speed);
        this.getContained().update(delta);
    }

}
