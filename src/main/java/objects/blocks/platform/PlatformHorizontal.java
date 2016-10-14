package objects.blocks.platform;

import system.IServiceLocator;

/**
 * The platform decorator to support horizontal movement.
 */
public final class PlatformHorizontal extends PlatformDecorator implements IPlatform {

    /**
     * Horizontal moving platform decorator constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    /* package */PlatformHorizontal(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        if (!getProps().containsKey(Platform.PlatformProperties.breaks)) {
            getContained().setSprite(sL.getSpriteFactory().getPlatformSpriteHori());
        }
        getContained().getProps().put(Platform.PlatformProperties.movingHorizontally, 1);

        getDirections().put(Platform.Directions.left, -1);
        getDirections().put(Platform.Directions.right, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        double xPos = this.getXPos();
        double yPos = this.getYPos();

        updateEnums(xPos, yPos);

        if (getContained().getProps().containsKey(Platform.PlatformProperties.movingHorizontally)) {

            final int movingProperty = getContained().getProps().get(Platform.PlatformProperties.movingHorizontally);

            if (movingProperty == (getContained().getDirections().get(Platform.Directions.right))) {
                setXPos(xPos + 2);
            } else if (movingProperty == getContained().getDirections().get(Platform.Directions.left)) {
                setXPos(xPos - 2);
            }
        }

        getContained().update(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEnums(final double xPos, final double yPos) {
        int gameWidth = getServiceLocator().getConstants().getGameWidth();
        if (xPos > gameWidth - this.getSprite().getWidth()) {
            getProps().replace(Platform.PlatformProperties.movingHorizontally, -1);
        } else if (xPos < 1) {
            getProps().replace(Platform.PlatformProperties.movingHorizontally, 1);
        }
    }
}
