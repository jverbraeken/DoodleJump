package rendering;

import resources.sprites.ISprite;

import java.awt.*;

public interface IRenderer {
    void start();

    void drawSprite(ISprite image, int x, int y);

    void drawSprite(ISprite image, int x, int y, int width, int height);

    void drawRectangle(int x, int y, int width, int height);

    void setGraphicsBuffer(Graphics graphics);

    /**
     * @return The camera the Renderer is using
     */
    ICamera getCamera();
}
