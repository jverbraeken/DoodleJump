package objects.powerups;

import logging.ILogger;
import objects.AGameObject;
import objects.doodles.IDoodle;
import progression.ISpringUsedObserver;
import progression.SpringUsedObserver;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the abstract functionality of ASprings.
 */
public abstract class ASpring extends AJumpablePowerup {

    /**
     * The speed with which the ASpring retracts after it is being used.
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
     * The constructor of the ASpring object.
     * @param serviceLocator           The locator providing services to the powerup
     * @param point        The coordinates of the powerup
     * @param boost        The value of the boost of the powerup
     * @param retractSpeed  The speed with which the spring retracts
     * @param sprites      The sprites, must be 2.
     * @param powerup      The class of the powerup
     */
    /* package */ ASpring(final IServiceLocator serviceLocator,
                          final Point point,
                          final double boost,
                          final int retractSpeed,
                          final ISprite[] sprites,
                          final Class<?> powerup) {
        super(serviceLocator, point, boost, sprites, powerup);
        this.retractSpeed = retractSpeed;
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
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

        ASpring self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.getDefaultSprite());
            }
        }, retractSpeed);
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
            for (WeakReference<ISpringUsedObserver> observer : springUsedObservers) {
                if (observer.get() != null) {
                    observer.get().alertSpringUsed();
                }
            }
        }
    }

    /**
     * Play the sound for the Spring.
     */
    void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playFeder();
    }

    public void addObserver(final SpringUsedObserver springUsedObserver) {
        if (springUsedObserver == null) {
            final String error = "Cannot add a null springUsedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.springUsedObservers.add(new WeakReference<>(springUsedObserver));
    }
}
