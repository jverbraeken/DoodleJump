package objects.powerups;

import logging.ILogger;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import progression.ITrampolineJumpedObserver;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
/* package */ public final class Trampoline extends AJumpablePowerup {

    /**
     * The speed with which the trampoline retracts after it is used.
     */
    private static final int RETRACT_SPEED = 250;

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
     * @param serviceLocator The Game's service locator
     * @param x              The X location for the trampoline
     * @param y              The Y location for the trampoline
     * @param level          The level of the powerup
     * @param usedSprite     The sprite that's drawn when the powerup is used
     * @param boost          The vertical speed boost the {@link objects.doodles.IDoodle Doodle} gets after hitting the Trampoline
     */
    /* package */ Trampoline(final IServiceLocator serviceLocator, final int x, final int y, final int level, final ISprite usedSprite, final int boost) {
        super(serviceLocator, x, y, boost, serviceLocator.getSpriteFactory().getPowerupSprite(Powerups.trampoline, level), usedSprite, Trampoline.class);
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
        super.executeDefaultAnimation(this, RETRACT_SPEED);
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
        audioManager.playFeder();
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
