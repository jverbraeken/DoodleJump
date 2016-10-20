package objects.powerups;

import system.IServiceLocator;

/**
 * This class defines the behaviour of a Circus cannon object.
 */
public class CircusCannon extends ATrampoline {


    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    @SuppressWarnings("magicnumber")
    /* package */ CircusCannon(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y - sL.getSpriteFactory().getCannonSprite().getHeight(),
                -75,
                sL.getSpriteFactory().getCannonSprite(),
                sL.getSpriteFactory().getCannonUsedSprite(),
                Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void playSound() { }

}

