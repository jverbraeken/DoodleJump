package objects.powerups;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
/* package */ public final class Trampoline extends ATrampoline {

    /**
     * The BOOST value for the ATrampoline.
     */
    private static final double BOOST = -50;

    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;


    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Trampoline(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y,
                BOOST,
                sL.getSpriteFactory().getPowerupSprite(Powerups.trampoline, 1),
                sL.getSpriteFactory().getTrampolineUsedSprite(),
                Trampoline.class);
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
            getLogger().info("Doodle collided with a Trampoline");
            doodle.collide(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playTrampoline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
        int oldHeight = getSprite().getHeight();
        int newHeight = this.getUsedSprite().getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(this.getUsedSprite());

        ATrampoline self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.getDefaultSprite());
            }
        }, Trampoline.RETRACT_SPEED);
    }

}
