package rendering;

import com.google.common.util.concurrent.AtomicDouble;

import static rendering.AccelerationType.fast;

/**
 * A arcade implementation of the {@link ICamera} interface. Arcade meaning the camera will move up automtically.
 */
/* package */ final class ArcadeCamera implements ICamera {

    /**
     * The normal acceleration for the camera in arcade mode.
     */
    private static final double NORMAL_ACCELERATION = 0.0005d;
    /**
     * The fast acceleration for the camera in arcade mode.
     */
    private static final double EXTRA_ACCELERATION = 0.25d;
    /**
     * The fast acceleration for the camera in arcade mode.
     */
    private static final double EXTRA_ACCELERATION_REVERSE = 1d;
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
     * The current acceleration type of the camera.
     */
    private AccelerationType accelerationType = AccelerationType.normal;
    /**
     * Extra speed when the Doodle is outside the screen
     */
    private double extraSpeed = 0;

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
    public void setAccelerationType(final AccelerationType type) {
        this.accelerationType = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double yPos) {
        if (this.accelerationType == fast) {
            this.extraSpeed += ArcadeCamera.EXTRA_ACCELERATION;
        } else if (this.extraSpeed > 0) {
            this.extraSpeed -= ArcadeCamera.EXTRA_ACCELERATION_REVERSE;
        }

        this.speed += ArcadeCamera.NORMAL_ACCELERATION;
        this.setYPos(this.getYPos() - this.speed - this.extraSpeed);
    }

}
