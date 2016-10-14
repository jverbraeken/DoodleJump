package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * An implementation of the {@link ICamera} interface that uses a Doodle as reference point.
 */
/* package */ final class DoodleCamera implements ICamera {

    /**
     * The maximum height of a Doodle on the screen when playing alone.
     */
    private static final double DOODLE_THRESHOLD = 3 / 7d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The y-coordinate of the camera. When the doodle jumps high enough, this value decreases.
     */
    private static AtomicDouble y = new AtomicDouble(0d);
    /**
     * The Doodle the camera uses as reference point.
     */
    private final IDoodle doodle;

    /**
     * Package constructor to prevent instantiation from outside the package.
     *
     * @param sL The serviceLocator for the Game.
     * @param d The Doodle that should be used as reference point.
     */
    /* package */ DoodleCamera(final IServiceLocator sL, final IDoodle d) {
        this.serviceLocator = sL;
        this.doodle = d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return DoodleCamera.y.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double yPos) {
        DoodleCamera.y.set(yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
         int height = serviceLocator.getConstants().getGameHeight();
         double yThreshold = this.getYPos() + height * DOODLE_THRESHOLD;
         if (this.doodle.getYPos() < yThreshold) {
             this.setYPos(doodle.getYPos() - height * DOODLE_THRESHOLD);
         }
    }

}
