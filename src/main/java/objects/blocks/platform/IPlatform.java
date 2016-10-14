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
     *
     * @return the BOOST attribute.
     */
    double getBoost();

    /**
     * Renders the Platform inside of the scene.
     */
    void render();

    /**
     * Returns the Map with properties of the Platform.
     *
     * @return the Map with properties
     */
    Map<Platform.PlatformProperties, Integer> getProps();

    /**
     * Updates the special properties of the Platform.
     *
     * @param xpos the x position of the platform
     * @param ypos the y position of the platform
     */
    void updateEnums(double xpos, double ypos);

    /**
     * Returns the Map with directions of the Platform.
     *
     * @return the Map with directions
     */
    Map<Platform.Directions, Integer> getDirections();

    /**
     * Sets the variable offSet to the parameter given.
     *
     * @param offSet the offSet that this.offSet has to be set to.
     */
    void setOffset(int offSet);

    /**
     * Returns the offSet of the platform.
     *
     * @return the offSet of the platform.
     */
    int getOffset();
}
