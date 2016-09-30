package objects.doodles;

import objects.IJumpable;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle in the StartScreen.
 */
/* package */ class StartScreenDoodle extends Doodle {

    /**
     * Boost reduction specifically for the StartScreen Doodle.
     */
    private static final double BOOST_REDUCTION = 0.8d;

    /**
     * Doodle constructor.
     *
     * @param sL The ServiceLocator.
     */
    /* package */ StartScreenDoodle(final IServiceLocator sL) {
        super(sL);
    }

    /** {@inheritDoc} */
    @Override
    public final void keyPress(final int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public void collide(final IJumpable jumpable) {
        setVerticalSpeed(BOOST_REDUCTION * jumpable.getBoost());
    }

}
