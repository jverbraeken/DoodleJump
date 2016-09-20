package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StartBlock extends ABlock implements IBlock {

    /* package */ StartBlock(IServiceLocator serviceLocator) {
        super(serviceLocator, 0);

        createAndPlaceObjects();
    }

    /** {@inheritDoc} */
    @Override
    /* package */ IPlatform placeInitialPlatforms(IPlatform lastPlatform) {
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        //TODO 1.2 is a magic number
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH/2, (int) (Game.HEIGHT/1.2));
        elements.add(platform);
        return platform;
    }

    /** {@inheritDoc} */
    @Override
    /* package */ IPlatform placeFollowingPlatforms(IPlatform lastPlatform, final int platformAmount, final int heightDividedPlatforms) {
        for (int i = 1; i < platformAmount; i++) {
            lastPlatform = placeFollowingPlatform(lastPlatform, heightDividedPlatforms);
        }
        return lastPlatform;
    }

    /**
     * Creates and places the objects in the block.
     */
    private void createAndPlaceObjects() {
        placePlatforms(null, 6, (Game.WIDTH + Game.HEIGHT)/130);
    }


    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }
}
