package objects.powerups;

import objects.AGameObject;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

public class Trampoline extends AGameObject implements ITrampoline {

    private static IServiceLocator serviceLocator;

    private ISprite sprite;

    /**
     * Trampoline constructor.
     * @param serviceLocator - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ Trampoline(IServiceLocator serviceLocator, int x, int y) {
        Trampoline.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getTrampolineSprite();

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
    public double getBoost() { return -20; }

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

    /** {@inheritDoc} */
    @Override
    public void used() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getTrampolineUsedSprite();
    }

}
