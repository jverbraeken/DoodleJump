package objects.powerups;

import objects.AGameObject;
import objects.doodles.IDoodle;
import progression.ProgressionObservers;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ final class Spring extends APowerup implements IJumpablePowerup {

    /**
     * The BOOST value for the Spring.
     */
    private static final double BOOST = -35;
    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;

    /**
     * The default sprite for the Spring.
     */
    private static ISprite defaultSprite;
    /**
     * The used sprite for the Spring.
     */
    private static ISprite usedSprite;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSpringSprite(), Spring.class);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        Spring.defaultSprite = spriteFactory.getSpringSprite();
        Spring.usedSprite = spriteFactory.getSpringUsedSprite();
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
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        this.animate();
        this.playSound();
        getServiceLocator().getProgressionManager().alertObservers(ProgressionObservers.spring);

        return Spring.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Spring.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playFeder();
    }

    /**
     * Animate the Spring.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();
        int newHeight = Spring.usedSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(Spring.usedSprite);

        Spring self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(Spring.defaultSprite);
            }
        }, Spring.RETRACT_SPEED);
    }

}
