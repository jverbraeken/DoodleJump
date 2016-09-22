package buttons;

import system.IFactory;

public interface IButtonFactory extends IFactory {

    IButton createMainMenuButton(int x, int y);
    IButton createPlayAgainButton(int x, int y);
    IButton createPlayButton(int x, int y);
    IButton createResumeButton(int x, int y);
}
