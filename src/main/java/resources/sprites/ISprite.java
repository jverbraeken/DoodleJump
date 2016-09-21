package resources.sprites;

import java.awt.*;
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public interface ISprite {
    String getName();

    Image getImage();

    int getWidth();

    int getHeight();

    double getRatio();
}
