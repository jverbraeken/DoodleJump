package rendering;

/**
 * This class is a factory that creates cameras.
 */
public interface ICameraFactory {

    /**
     * Creates a default camera.
     *
     * @return A new ICamera.
     */
    ICamera createDefaultCamera();

    /**
     * Creates a arcade camera.
     *
     *
     * @return A new ICamera.
     */
    ICamera createArcadeCamera();

}
