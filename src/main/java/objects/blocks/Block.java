package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;

import java.util.Set;

/**
 * This class focuses on the implementation of Blocks.
 * These blocks contain the main bulk of the game objects.
 * This bulk contains the platforms, powerups, enemies and other interactable items.
 * These blocks are meant to pass through our frame vertically.
 * The player is meant to progress from one block to the next by jumping on things.
 * These things can be anything as specified by "bulk".
 * The choice for block was made as to make separate sub-levels in a continuous world.
 */
/* package */ final class Block implements IBlock {
    /**
     * A set of all the game objects in this block.
     */
    private final Set<IGameObject> elements;
    /**
     * The highest located jumpable in this block.
     */
    private final IJumpable topJumpable;

    /**
     * Package protected constructor so only the BlockFactory can create blocks.
     *
     * @param e  The elements for the block.
     * @param tJ The highest jumpable object.
     */
    /* package */ Block(final Set<IGameObject> e, final IJumpable tJ) {
        this.elements = e;
        this.topJumpable = tJ;
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
    public void render() {
        elements.forEach(IGameObject::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        for (IGameObject gameObject : elements) {
            gameObject.update(delta);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeElement(final IGameObject element) {
        elements.remove(element);
    }

}
