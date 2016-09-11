package objects.buttons;

import system.Game;
import system.IServiceLocator;

import java.awt.*;

public class PlayButton implements IButton {

    private final IServiceLocator serviceLocator;

    private final Image image;
    private final int width, height;
    private final int[] topLeft = new int[2], bottomRight = new int[2];

    /* package */ PlayButton(IServiceLocator serviceLocator, int x, int y, Image image) {
        super();

        assert serviceLocator != null;
        assert image != null;

        this.serviceLocator = serviceLocator;
        this.image = image;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.topLeft[0] = x;
        this.topLeft[1] = y;
        this.bottomRight[0] = x + width;
        this.bottomRight[1] = y + height;
    }

    @Override
    public void mouseClicked(int x, int y) {
        assert x >= 0 && y >= 0;
        if(x > topLeft[0] && x < bottomRight[0]) {
            if(y > topLeft[1] && y < bottomRight[1]) {
                Game.setScene(serviceLocator.getSceneFactory().newWorld());
            }
        }
    }

    @Override
    public void paint() {
        serviceLocator.getRenderer().drawImage(image, topLeft[0], topLeft[1], width, height);
    }

}
