package objects.powerups;

import objects.AGameObject;
import objects.doodles.IDoodle;
import progression.ProgressionObservers;
import resources.audio.IAudioManager;
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
    /* package */ Spring(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, BOOST, RETRACT_SPEED, sL.getSpriteFactory().getSpringSprite(), sL.getSpriteFactory().getSpringUsedSprite(), Spring.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (doodle.getVerticalSpeed() > 0 && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            getLogger().info("Doodle collided with a Spring");
            doodle.collide(this);
        }
    }

    /**
     * Play the sound for the Spring.
     */
    void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playFeder();
    }

}
