package objects.powerups;

import logging.ILogger;
import math.ICalc;
import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import progression.ISpringUsedObserver;
import progression.ProgressionObservers;
import progression.SpringUsedObserver;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the behaviour of the spring powerup.
 */
public final class Spring extends AJumpablePowerup {

    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;

    /**
     * A list containing the observer that want to get a notification when the doodle jumps upon a Spring.
     */
    private final List<WeakReference<ISpringUsedObserver>> springUsedObservers = new ArrayList<>();

    /**
     * The logger.
     */
    private final ILogger logger;


    /**
     * Spring constructor.
     *
     * @param serviceLocator - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(final IServiceLocator serviceLocator, final int x, final int y, final ISprite sprite, final ISprite usedSprite, final int boost) {
        super(serviceLocator, x, y, boost, serviceLocator.getSpriteFactory().getPowerupSprite(Powerups.spring, serviceLocator.getProgressionManager().getPowerupLevel(Powerups.spring)), usedSprite, Spring.class);
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

        Spring self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.getDefaultSprite());
            }
        }, RETRACT_SPEED);
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
