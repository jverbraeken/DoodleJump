package objects.powerups;

import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the behaviour of the trampoline powerup.
 */
/* package */ public final class Trampoline extends APowerup implements IJumpablePowerup {

    /**
     * The BOOST value for the Trampoline.
     */
    private static final double BOOST = -50;
    /**
     * The speed with which the springs retracts after it is being used.
     */
    private static final int RETRACT_SPEED = 250;

    /**
     * The default sprite for the Trampoline.
     */
    private static ISprite defaultSprite;
    /**
     * The used sprite for the Trampoline.
     */
    private static ISprite usedSprite;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x  - The X location for the trampoline.
     * @param y  - The Y location for the trampoline.
     */
    /* package */ Trampoline(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getTrampolineSprite(), Trampoline.class);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        Trampoline.defaultSprite = spriteFactory.getTrampolineSprite();
        Trampoline.usedSprite = spriteFactory.getTrampolineUsedSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        this.animate();
        this.playSound();

        return Trampoline.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Play the sound for the Trampoline.
     */
    private void playSound() {
        IAudioManager audioManager = getServiceLocator().getAudioManager();
        audioManager.playTrampoline();
    }

    /**
     * Animate the Trampoline.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();
        int newHeight = Trampoline.usedSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(Trampoline.usedSprite);

        Trampoline self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(Trampoline.defaultSprite);
            }
        }, Trampoline.RETRACT_SPEED);
    }

}
