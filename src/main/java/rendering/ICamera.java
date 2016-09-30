package rendering;

/**
 * Interface for the camera class.
 */
public interface ICamera {

    /**
     * Set the Y position of the camera.
     *
     * @param yPos the new Y position.
     */
    void setYPos(final double yPos);

    /**
     * Get the Y position of the camera.
     *
     * @return The Y position.
     */
    double getYPos();

}
