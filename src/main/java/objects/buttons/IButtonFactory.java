package objects.buttons;

import system.IFactory;

public interface IButtonFactory extends IFactory {

    IButton createPlayButton(int x, int y);
    IButton createResumeButton(int x, int y);
    IButton createMainMenuButton(int x, int y);
    IButton createPlayAgainButton(int x, int y);
}
