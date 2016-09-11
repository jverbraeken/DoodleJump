package objects.backgrounds;

import rendering.IDrawable;
import system.IFactory;

public interface IBackgroundFactory extends IFactory {

    IDrawable createStartMenuBackground();

}
