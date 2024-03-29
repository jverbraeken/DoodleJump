package objects.doodles;

import input.Keys;
import objects.IJumpable;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle in the StartScreen.
 */
/* package */ final class StartScreenDoodle extends Doodle {

    /**
     * Boost reduction specifically for the StartScreen Doodle.
     */
    private static final double BOOST_REDUCTION = 0.8d;

    /**
     * Doodle constructor.
     *
     * @param sL The ServiceLocator.
     */
    /* package */ StartScreenDoodle(final ISprite[] sprites, final IServiceLocator sL) {
        super(sL, sprites, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collide(final IJumpable jumpable) {
        this.setVerticalSpeed(StartScreenDoodle.BOOST_REDUCTION * jumpable.getBoost());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPress(final Keys key) {
        // Prevents the StartDoodle from moving
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyRelease(final Keys key) {
        // Prevents the StartDoodle from moving
    }

}
