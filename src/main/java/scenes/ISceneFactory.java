package scenes;

import system.Factory;

public interface ISceneFactory extends Factory {
    Menu getMenu();
    World getNewWorld();
}
