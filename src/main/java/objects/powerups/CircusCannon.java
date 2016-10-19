package objects.powerups;

import objects.AGameObject;
import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * This class defines the behaviour of a Circus cannon object.
 */
public class CircusCannon extends AGameObject implements IJumpable {

    /**
     * The BOOST value for the Trampoline.
     */
    private static final double BOOST = -75;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ CircusCannon(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y - sL.getSpriteFactory().getCannonSprite().getHeight(), sL.getSpriteFactory().getCannonSprite(), Trampoline.class);
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

        return CircusCannon.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }


    /**
     * Animate the Circus Cannon.
     */
    private void animate() {
        int oldHeight = getSprite().getHeight();

        ISpriteFactory spriteFactory = getServiceLocator().getSpriteFactory();
        ISprite newSprite = spriteFactory.getCannonUsedSprite();
        setSprite(newSprite);

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }
}

