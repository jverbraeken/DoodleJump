package objects.buttons;

import system.IFactory;

public interface IButtonFactory extends IFactory {

    PlayButton newPlayButton(int x, int y);

}
