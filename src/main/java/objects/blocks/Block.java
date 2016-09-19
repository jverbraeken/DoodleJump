package objects.blocks;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import system.Game;
import system.IServiceLocator;

/* package */ final class Block extends ABlock implements IBlock {

    /* package */ Block(IServiceLocator serviceLocator, IPlatform lastPlatform) {
        super(serviceLocator, (int) lastPlatform.getYPos() + 800);

        createAndPlaceObjects(lastPlatform);
    }

    /**
     * Creates and places platforms in the block.
     *
     * @param lastPlatform The highest platform in the previous block.
     */
    private void createAndPlaceObjects(IPlatform lastPlatform) {
        placePlatforms(lastPlatform, 8, (Game.WIDTH + Game.HEIGHT) / 120);
    }

    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }
}
