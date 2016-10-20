package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class defines the behaviour of a Circus cannon object.
 */
public class CircusCannon extends ATrampoline {

    /**
     * The BOOST value for the circus cannon.
     */
    private static double BOOST = -75;

    /**
     * CircusCannon constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ CircusCannon(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y - sL.getSpriteFactory().getCannonSprite().getHeight(),
                BOOST,
                sL.getSpriteFactory().getCannonSprite(),
                sL.getSpriteFactory().getCannonUsedSprite(),
                Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void playSound() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

}

