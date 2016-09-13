package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

import java.util.ArrayList;
import java.util.HashSet;

public interface IBlock extends IGameObject {
    ArrayList<IGameObject> getContent();
    void placePlatforms(IPlatform last);

    /**
     * Checks for all the Platforms if they are under over the height
     * of the screen, if that's the case, delete that Platforms.
     */
    void cleanUpPlatforms();
}
