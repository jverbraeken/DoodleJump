package resources.sprites;

import java.awt.Image;

public interface ISprite {
    String getName();
    Image getImage();
    int getWidth();
    int getHeight();
    double getRatio();
}
