package objects.blocks.platform;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    private static final double boost = -16;

    /**
     * Platform constructor.
     *
     * @param serviceLocator - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     */
    /* package */ Platform(IServiceLocator serviceLocator, int x, int y) {
        super(x, y, serviceLocator.getSpriteFactory().getPlatformSprite1());
        Platform.serviceLocator = serviceLocator;
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() { return Platform.boost; }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }


    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }

}
