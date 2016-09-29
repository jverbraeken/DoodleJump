package rendering;

/**
 * Provides a public interface for camera implementations.
 */
public interface ICamera {

    /**
     * Sets the y-position of the camera to the value specified.
     *
     * @param yPos The new y-value of the camera
     */
    void setYPos(final double yPos);

    /**
     * Get the Y position of the camera.
     *
     * @return The Y position.
     */
    double getYPos();

}
