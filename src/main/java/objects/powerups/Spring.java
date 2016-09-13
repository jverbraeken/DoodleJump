package objects.powerups;

import objects.AGameObject;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

public class Spring extends AGameObject implements IPowerup {

    private static IServiceLocator serviceLocator;

    private ISprite sprite;
    private double boost = -35;

    /**
     * Trampoline constructor.
     * @param serviceLocator - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Spring(IServiceLocator serviceLocator, int x, int y) {
        Spring.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getSpringSprite();

        this.setXPos(x);
        this.setYPos(y);
        this.setHeight(sprite.getHeight());
        this.setWidth(sprite.getWidth());
    }

    /** {@inheritDoc} */
    @Override
    public void animate() { }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        this.used();
        return this.boost;
    }

    /** {@inheritDoc} */
    @Override
    public void move() { }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int)this.getXPos(), (int)this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void update() { }


    /**
     * Change the Spring sprite to used.
     */
    private void used() {
        int oldHeight = this.sprite.getHeight();

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite newSprite = spriteFactory.getSpringUsedSprite();

        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);

        this.sprite = newSprite;
    }

}
