package scenes;

import system.IFactory;

public interface ISceneFactory extends IFactory {

    IScene createMainMenu();
    World newWorld();
    IScene createKillScreen();
    IScene createPauseScreen();

}
