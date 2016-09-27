package rendering;

/**
 * Provides a public interface for camera implementations.
 */
public interface ICamera {

    /**
     * Sets the y-position of the camera to the value specified.
     * @param y The new y-value of the camera
     */
    void setYPos(final double y);

    /**
     * @return The y-position of the camera
     */
    double getYPos();

}
