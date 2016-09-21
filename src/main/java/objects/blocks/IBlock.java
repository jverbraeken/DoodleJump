package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import system.IRenderable;
import system.IUpdatable;

import java.util.ArrayList;
import java.util.Set;

public interface IBlock extends IRenderable, IUpdatable {

    /**
     * Get the elements of the block.
     *
     * @return The elements of the block.
     */
    Set<IGameObject> getElements();

    /**
     * @return The highest situated {@link IJumpable jumpable} element in the block
     */
    IJumpable getTopJumpable();

    /**
     * Adds a new {@link IJumpable jumpable} game object to the block.
     */
    void addElement(IJumpable object);

    /**
     * Adds a new {@link IGameObject game object} to the block.
     */
    void addElement(IGameObject object);

    void addYPos(double y);
}
