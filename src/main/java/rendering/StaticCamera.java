package rendering;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * The standard implementation of the {@link ICamera} interface.
 */
/* package */ class StaticCamera implements ICamera {

    /**
     * The y-coordinate of the camera. When the doodle jumps high enough, this value decreases.
     */
    private static AtomicDouble y = new AtomicDouble(0d);

    /**
     * Package constructor to prevent instantiation from outside the package.
     */
    /* package */ StaticCamera() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double yPos) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccelerationType(AccelerationType type) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return StaticCamera.y.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
    }

}
