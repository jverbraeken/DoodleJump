package objects.powerups;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
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

    /**
     * {@inheritDoc}
     */
    @Override
    void animate() {
        int oldHeight = getSprite().getHeight();

        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        ISprite newSprite = spriteFactory.getCannonUsedSprite();
        setSprite(newSprite);

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }
}

