package rendering;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * A arcade implementation of the {@link ICamera} interface. Arcade meaning the camera will move up automtically.
 */
/* package */ final class ArcadeCamera implements ICamera {

    /**
     * The acceleration for the camera in arcade mode.
     */
    private static final double ACCELERATION = 0.0005d;
    /**
     * The initial speed for the camera in arcade mode.
     */
    private static final double INITIAL_SPEED = 3d;

    /**
     * The y-coordinate of the camera.
     */
    private static AtomicDouble y = new AtomicDouble(0d);
    /**
     * The speed of the camera.
     */
    private double speed = INITIAL_SPEED;

    /**
     * Package constructor to prevent instantiation from outside the package.
     */
    /* package */ ArcadeCamera() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return ArcadeCamera.y.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double yPos) {
        ArcadeCamera.y.set(yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double yPos) {
        this.speed += ArcadeCamera.ACCELERATION;
        this.setYPos(this.getYPos() - this.speed);
    }

}
