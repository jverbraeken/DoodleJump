package objects.powerups;

import logging.ILogger;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import progression.ITrampolineJumpedObserver;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.awt.Point;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
public final class Trampoline extends AJumpablePowerup {

    /**
     * The speed with which the trampoline retracts after it is used.
     */
    private final int retractSpeed;

    /**
     * A list containing the observer that want to get a notification when the doodle jumps upon a Trampoline.
     */
    private final List<WeakReference<ITrampolineJumpedObserver>> trampolineUsedObservers = new ArrayList<>();

    /**
     * The logger.
     */
    private final ILogger logger;


    /**
     * Constructs a new Trampoline.
     *
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ Trampoline(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.trampoline, level);
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
        this.retractSpeed = Powerups.trampoline.getRetractSpeed(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
        super.executeDefaultAnimation(retractSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // We supress the warning here because we do check for the null value before calling alertSpringUsed, but
    // because there's a bug in FindBugs (ironically :) ) the warning is generated
    @edu.umd.cs.findbugs.annotations.SuppressWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (doodle.getVerticalSpeed() > 0 && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            getLogger().info("Doodle collided with a Trampoline");
            doodle.collide(this);
            for (WeakReference<ITrampolineJumpedObserver> observer : trampolineUsedObservers) {
                if (observer.get() != null) {
                    observer.get().alertTrampolineJumped();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }

    /**
     * Play the sound for the Trampoline.
     */
    /* package */ void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.play(AudioManager.Sound.TRAMPOLINE);
    }

    /**
     * Lets a class observe this Trampoline.
     *
     * @param trampolineUsedObserver The observer that wants to get notifications from the Trampoline
     */
    public void addObserver(final ITrampolineJumpedObserver trampolineUsedObserver) {
        if (trampolineUsedObserver == null) {
            final String error = "Cannot add a null trampolineUsedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.trampolineUsedObservers.add(new WeakReference<>(trampolineUsedObserver));
    }
}
