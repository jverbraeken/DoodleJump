package objects.powerups;

import logging.ILogger;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import progression.ISpringUsedObserver;
import progression.SpringUsedObserver;
import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the behaviour of the spring powerup.
 */
public final class Spring extends AJumpablePowerup {

    /**
     * The speed with which the spring retracts after it is used.
     */
    private final int retractSpeed;

    /**
     * A list containing the observer that want to get a notification when the doodle jumps upon a Spring.
     */
    private final List<WeakReference<ISpringUsedObserver>> springUsedObservers = new ArrayList<>();

    /**
     * The logger.
     */
    private final ILogger logger;


    /**
     * Constructs a new Spring.
     *
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ Spring(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.spring, level);
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
        this.retractSpeed = Powerups.spring.getRetractSpeed(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
        super.executeDefaultAnimation(this, retractSpeed);
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
            getLogger().info("Doodle collided with a Spring");
            doodle.collide(this);
            for (WeakReference<ISpringUsedObserver> observer : springUsedObservers) {
                if (observer.get() != null) {
                    observer.get().alertSpringUsed();
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
     * Play the sound for the Spring.
     */
    /* package */ void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playFeder();
    }

    /**
     * Lets a class observe this Spring.
     *
     * @param springUsedObserver The observer that wants to get notifications from the Spring
     */
    public void addObserver(final SpringUsedObserver springUsedObserver) {
        if (springUsedObserver == null) {
            final String error = "Cannot add a null springUsedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.springUsedObservers.add(new WeakReference<>(springUsedObserver));
    }
}
