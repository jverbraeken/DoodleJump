package objects.blocks.platform;

import system.IServiceLocator;

/**
 * The platform decorator to support horizontal movement.
 */
public class PlatformHorizontal extends PlatformDecorator implements IPlatform {

    /**
     * Platform constructor.
     *
     * @param sL the servicelocator.
     * @param platform the encapsulated platform.
     */
     PlatformHorizontal(final IServiceLocator sL, final IPlatform platform) {
         super(sL, platform);
         getContained().setSprite(sL.getSpriteFactory().getPlatformSpriteHori());
         getContained().getProps().put(Platform.PlatformProperties.movingHorizontally, 1);
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
        double xPos = this.getXPos();
        double yPos = this.getYPos();

        updateEnums(xPos, yPos);

        if (getContained().getProps().containsKey(Platform.PlatformProperties.movingHorizontally)) {
            if (getContained().getProps().get(Platform.PlatformProperties.movingHorizontally).equals(getContained().getDirections().get(Platform.Directions.right))) {
                this.setXPos(xPos + 2);
            } else if (getContained().getProps().get(Platform.PlatformProperties.movingHorizontally).equals(getContained().getDirections().get(Platform.Directions.left))) {
                this.setXPos(xPos - 2);
            }
        }

        getContained().update(delta);
    }

    /** {@inheritDoc} */
    @Override
    public final void updateEnums(final double xPos, final double yPos) {
        double gameWidth = getServiceLocator().getConstants().getGameWidth();
        if (xPos > gameWidth - getSprite().getWidth()) {
            getProps().replace(Platform.PlatformProperties.movingHorizontally, -1);
        } else if (xPos < 1) {
            getProps().replace(Platform.PlatformProperties.movingHorizontally, 1);
        }
    }
}
