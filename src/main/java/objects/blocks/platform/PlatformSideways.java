package objects.blocks.platform;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.BlockFactory;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

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
     PlatformSideways(IServiceLocator sL, IPlatform platform) {
         super(sL, platform);
         contained.setSprite(sL.getSpriteFactory().getPlatformSpriteHori());
         contained.getProps().put(Platform.PlatformProperties.movingHorizontally, 1);
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) {
        double xPos = this.getXPos();
        double yPos = this.getYPos();

        if (BlockFactory.isSpecialPlatform(this)) {
            updateEnums(xPos, yPos);
        }

        if (contained.getProps().containsKey(Platform.PlatformProperties.movingHorizontally)) {
            if (contained.getProps().get(Platform.PlatformProperties.movingHorizontally).equals(contained.getDirections().get(Platform.Directions.right))) {
                this.setXPos(xPos + 2);
            } else if (contained.getProps().get(Platform.PlatformProperties.movingHorizontally).equals(contained.getDirections().get(Platform.Directions.left))) {
                this.setXPos(xPos - 2);
            }
        }
    }
}
