package objects.blocks.platform;

import objects.IGameObject;
import objects.IJumpable;
import objects.doodles.IDoodle;

import java.util.Map;

/**
 * This class focuses on the implementation of platforms.
 */
public interface IPlatform extends IGameObject, IJumpable {
    /**
     * Will return the BOOST attribute.
     * @return the BOOST attribute.
     */
    double getBoost();

    /**
     * Renders the Platform inside of the scene.
     */
    void render();

    /**
     * Will send to the Doodle that it is colliding with this platform.
     * @param doodle the Doodle it is colliding with.
     */
    void collidesWith(IDoodle doodle);

    /**
     * Returns the Map with properties of the Platform.
     * @return the Map with properties
     */
    Map getProps();

}
