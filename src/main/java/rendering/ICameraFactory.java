package rendering;

import objects.doodles.IDoodle;

/**
 * This class is a factory that creates cameras.
 */
public interface ICameraFactory {

    /**
     * Creates a new static camera.
     *
     * @return A new ICamera.
     */
    ICamera createStaticCamera();

    /**
     * Creates a new Doodle camera, which operates relative to a Doodle.
     *
     * @param doodle The Doodle that should be used as reference point.
     * @return A new ICamera.
     */
    ICamera createDoodleCamera(final IDoodle doodle);

    /**
     * Creates a arcade camera.
     *
     * @return A new ICamera.
     */
    ICamera createArcadeCamera();

}
