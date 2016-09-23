package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import system.IServiceLocator;

import java.util.Set;

/**
 * This class focusses on the implementation of Blocks.
 * These blocks contain the main bulk of the game objects.
 * This bulk contains the platforms, powerups, enemies and other interactable items.
 * These blocks are meant to pass through our frame vertically.
 * The player is meant to progress from one block to the next by jumping on things.
 * These things can be anything as specified by "bulk".
 * The choice for block was made as to make seperate sub-levels in a continuous world.
 */
public final class Block implements IBlock {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;

    /**
     * A set of all the game objects in this block.
     */
    private final Set<IGameObject> elements;

    /**
     * The highest located jumapble in this block.
     */
    private final IJumpable topJumpable;

    /* package */ Block(final IServiceLocator serviceLocator, final Set<IGameObject> elements, final IJumpable topJumpable) {
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
