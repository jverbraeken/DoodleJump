package objects.doodles;

import input.IInputManager;
import objects.AGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.MovementBehavior;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the doodle in the startscreen.
 */
/* package */ class StartScreenDoodle extends Doodle {

    /**
     * Boost reduction specifically for the startscreen Doodle.
     */
    private static final double BOOST_REDUCTION = 0.8d;

    /**
     * Doodle constructor.
     * @param sL The service locator
     */
    /* package */ StartScreenDoodle(final IServiceLocator sL) {
        super(sL);
    }

    /** {@inheritDoc} */
    @Override
    public final void keyPress(final int keyCode) {
    }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) {
    }

    /** {@inheritDoc} */
    @Override
    public void collide(IJumpable jumpable) {
        setVerticalSpeed(BOOST_REDUCTION * jumpable.getBoost());
    }

}
