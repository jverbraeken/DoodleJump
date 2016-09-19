package scenes;

import system.IFactory;

public interface ISceneFactory extends IFactory {

    Menu newMenu();

    World newWorld();

    KillScreen newKillScreen();

}
