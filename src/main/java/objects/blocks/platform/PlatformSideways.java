package objects.blocks.platform;

import objects.blocks.BlockFactory;
import system.IServiceLocator;

/**
 * Created by Nick on 11-10-2016.
 */
public class PlatformSideways extends PlatformDecorator implements IPlatform {

    /**
     * Platform constructor.
     *
     * @param sL the servicelocator.
     * @param platform the encapsulated platform.
     */
     PlatformSideways(final IServiceLocator sL, final IPlatform platform) {
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
}
