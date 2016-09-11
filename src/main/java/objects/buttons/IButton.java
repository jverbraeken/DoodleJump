package objects.buttons;

import input.IMouseInputObserver;
import objects.IGameObject;

/**
 * Created by joost on 6-9-16.
 */
public interface IButton extends IMouseInputObserver {

    public void paint();

}
