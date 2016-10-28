package objects.powerups;

import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ final class Spring extends ASpring {

    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;


    /**
     * Spring constructor.
     *
     * @param sL - The Games service locator.
     */
    /* package */ Spring(final IServiceLocator sL, final Point p, final ISprite[] sprites, final int boost) {
        super(sL, p, boost, RETRACT_SPEED, sprites, Spring.class);
    }

}
