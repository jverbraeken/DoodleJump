package objects.blocks;

import objects.IGameObject;

import java.util.Set;

/**
 * Created by joost on 6-9-16.
 */
public interface IBlock extends IGameObject{
    public Set<IGameObject> getElements();
}
