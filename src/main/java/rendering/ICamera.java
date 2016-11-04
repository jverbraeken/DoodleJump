package rendering;

import system.IUpdatable;

/**
 * Provides a public interface for camera implementations.
 */
public interface ICamera extends IUpdatable {

    /**
     * Get the Y position of the camera.
     *
     * @return The Y position.
     */
    double getYPos();

    /**
     * Sets the y-position of the camera to the value specified.
     *
     * @param yPos The new y-value of the camera
     */
    void setYPos(final double yPos);

    /**
     * Sets the acceleration type of the camera to the specified value.
     *
     * @param type The new acceleration type for the camera
     */
    void setAccelerationType(final AccelerationType type);

}
