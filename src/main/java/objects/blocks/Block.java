package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

public final class Block implements IBlock {

    private static IServiceLocator serviceLocator;
    private final Set<IGameObject> elements;
    private final IJumpable topJumpable;

    /* package */ Block(IServiceLocator serviceLocator, Set<IGameObject> elements, IJumpable topJumpable) {
        Block.serviceLocator = serviceLocator;
        this.elements = elements;
        this.topJumpable = topJumpable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IGameObject> getElements() {
        return this.elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IJumpable getTopJumpable() {
        return topJumpable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        elements.forEach(IGameObject::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(double delta) {

    }
}
