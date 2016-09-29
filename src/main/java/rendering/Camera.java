package rendering;

/**
 * Class representing the virtual camera for the game.
 */
/* package */ final class Camera implements ICamera {

    /**
     * The y position of the camera.
     */
    private static double y = 0d;

    /**
     * Package constructor to prevent instantiation from outside the package.
     */
    /* package */ Camera() { }

    /** {@inheritDoc} */
    @Override
    public void setYPos(final double yPos) {
        Camera.y = yPos;
    }

    /** {@inheritDoc} */
    @Override
    public double getYPos() {
        return Camera.y;
    }

}
