package objects.powerups;

import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
/* package */ public final class Trampoline extends ATrampoline {

    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;


    /**
     * Trampoline constructor.
     *
     * @param sL         The Games service locator.
     * @param point          The location for the trampoline
     * @param sprite     The sprite that's used for the Trampoline
     * @param usedSprite The sprite that's used when the Doodle has jumped upon it
     * @param boost      The boost the Doodle gets when it jumps upon it
     */
    /* package */ Trampoline(final IServiceLocator sL, final Point point, final ISprite sprite, final ISprite usedSprite, final int boost) {
        super(sL,
                point,
                boost,
                sprite,
                usedSprite,
                Trampoline.class);
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
