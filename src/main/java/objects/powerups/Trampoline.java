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
     * @param serviceLocator The Games service locator.
     */
    /* package */ Trampoline(IServiceLocator serviceLocator) {
        Trampoline.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getTrampolineSprite();
    }

    /** {@inheritDoc} */
    @Override
    public void animate() { }

    /** {@inheritDoc} */
    @Override
    public void move() { }

    /** {@inheritDoc} */
    @Override
    public void render() { }

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
