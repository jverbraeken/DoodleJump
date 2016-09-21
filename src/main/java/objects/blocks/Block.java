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
    private final Set<IGameObject> elements = new HashSet<>();
    private IJumpable topJumpable;

    /* package */ Block(IServiceLocator serviceLocator) {
        Block.serviceLocator = serviceLocator;
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
    public void addYPos(double y) {
        for (IGameObject e : elements) {
            e.addYPos(y);
        }
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
        cleanUpPlatforms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addElement(IJumpable jumpable) {
        this.elements.add(jumpable);
        this.topJumpable = jumpable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addElement(IGameObject object) {
        this.elements.add(object);
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block. When there is a collision, delete the platform
     * from the list.
     *
     * @param platform The IPlatform that has to be checked for collision.
     */
    private void platformCollideCheck(IPlatform platform) {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : elements) {
            if (platform.checkCollission(e)) {
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            elements.remove(e);
        }
    }

    /**
     * Checks for all the Platforms if they are under over the height
     * of the screen, if that's the case, delete that Platforms.
     */
    private void cleanUpPlatforms() {
        Set<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : elements) {
            if (e.getYPos() - 50 > Game.HEIGHT) {
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            elements.remove(e);
        }
    }
}
