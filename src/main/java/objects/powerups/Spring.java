package objects.powerups;

import objects.AGameObject;
import objects.doodles.IDoodle;
import progression.ProgressionObservers;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ final class Spring extends ASpring {

    /**
     * The BOOST value for the ATrampoline.
     */
    private static final double BOOST = -35;

    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;


    /**
     * Spring constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(final IServiceLocator sL, final int x, final int y, final ISprite sprite, final ISprite usedSprite) {
        super(sL, x, y, BOOST, RETRACT_SPEED, sprite, usedSprite, Spring.class);
    }

}
