package objects.blocks.platform;

import objects.IGameObject;
import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;

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
     * Will send to the Doodle that it is colliding with this platform.
     *
     * @param doodle the Doodle it is colliding with.
     */
    void collidesWith(IDoodle doodle);

    /**
     * Returns the Map with properties of the Platform.
     *
     * @return the Map with properties
     */
    Map getProps();

    /**
     * Updates the special properties of the Platform.
     *
     * @param xpos the x position of the platform
     * @param ypos the y position of the platform
     */
    void updateEnums(double xpos, double ypos);

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

    /**
     * Will return the Sprite of the broken platform, dependent
     * on the number of the animation. SO which phase it is in.
     *
     * @param numberOfAnimation the phase of the animation
     * @return the sprite belonging to this animation phase
     */
    ISprite getBrokenSprite(final int numberOfAnimation);

}
