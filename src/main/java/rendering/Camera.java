package rendering;

/**
 * A standard implementation of the {@link ICamera} interface.
 */
/* package */ final class Camera implements ICamera {

    /**
     * The y-coordinate of the camera. When the doodle jumps high enough, this value decreases.
     */
    private static double y = 0d;

    /**
     * Prevent instantiation from outside the package.
     */
    /* package */ Camera() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double y) {
        Camera.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return Camera.y;
    }

}
