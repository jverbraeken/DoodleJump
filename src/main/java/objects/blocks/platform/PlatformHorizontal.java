package objects.blocks.platform;

import objects.blocks.BlockFactory;
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

        if (BlockFactory.isSpecialPlatform(this)) {
            updateEnums(xPos, yPos);
        }

        if (getContained().getProps().containsKey(Platform.PlatformProperties.movingHorizontally)) {
            if (getContained().getProps().get(Platform.PlatformProperties.movingHorizontally).equals(getContained().getDirections().get(Platform.Directions.right))) {
                this.setXPos(xPos + 2);
            } else if (getContained().getProps().get(Platform.PlatformProperties.movingHorizontally).equals(getContained().getDirections().get(Platform.Directions.left))) {
                this.setXPos(xPos - 2);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void updateEnums(final double xPos, final double yPos) {
        int gameWidth = getServiceLocator().getConstants().getGameWidth();
        if (xPos > gameWidth - this.getSprite().getWidth()) {
            this.getProps().replace(Platform.PlatformProperties.movingHorizontally, -1);
        } else if (xPos < 1) {
            this.getProps().replace(Platform.PlatformProperties.movingHorizontally, 1);
        }
    }
}
