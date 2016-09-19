package scenes;

import system.IFactory;
public interface ISceneFactory extends IFactory {

    IScene newMenu();
    IScene newWorld();
    IScene newKillScreen();

}
