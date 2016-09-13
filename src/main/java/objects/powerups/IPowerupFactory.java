package objects.powerups;

import objects.IGameObject;
import system.IFactory;

public interface IPowerupFactory extends IFactory {

    IGameObject createTrampoline(int x ,int y);

}
