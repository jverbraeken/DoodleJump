package rendering;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * A standard implementation of the {@link ICamera} interface.
 */
/* package */ final class Camera implements ICamera {

    /**
     * The y-coordinate of the camera. When the doodle jumps high enough, this value decreases.
     */
    private static AtomicDouble y = new AtomicDouble(0d);

    /**
     * Package constructor to prevent instantiation from outside the package.
     */
    /* package */ Camera() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return Camera.y.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double yPos) {
        Camera.y.set(yPos);
    }

}
