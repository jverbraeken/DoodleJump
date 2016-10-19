package objects.powerups;

import system.IServiceLocator;

/**
 * This class describes the behaviour of the rocket launcher powerup.
 */
/* package */ public final class RocketLauncher extends ATrampoline {

    /**
     * RocketLauncher constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the rocket launcher.
     * @param y - The Y location for the rocket launcher.
     */
    /* package */ RocketLauncher(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y - sL.getSpriteFactory().getRocketLauncherSprite().getHeight(),
                -100,
                sL.getSpriteFactory().getRocketLauncherSprite(),
                sL.getSpriteFactory().getRocketLauncherUsedSprite(),
                Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound() { }

}
