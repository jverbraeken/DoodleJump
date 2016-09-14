package objects.buttons;

import system.IFactory;

public interface IButtonFactory extends IFactory {

    IButton createPlayButton(int x, int y);
}
