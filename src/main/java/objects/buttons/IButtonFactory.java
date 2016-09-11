package objects.buttons;

import system.IFactory;

public interface IButtonFactory extends IFactory {

    PlayButton createPlayButton(int x, int y);

}
