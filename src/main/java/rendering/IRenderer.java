package rendering;

import java.awt.*;

public interface IRenderer {
    void start();

    void drawImage(Image image, int x, int y);

    void drawImage(Image image, int x, int y, int width, int height);

    void drawRectangle(int x, int y, int width, int height);

    void setGraphicsBuffer(Graphics graphics);
}
