package objects.buttons;

import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.awt.*;

public class PlayButton implements IButton {

    private final IServiceLocator serviceLocator;

    private final ISprite sprite;
    private final int width, height;
    private final int[] topLeft = new int[2], bottomRight = new int[2];

    /* package */ PlayButton(IServiceLocator serviceLocator, int x, int y, ISprite sprite) {
        super();

        assert serviceLocator != null;
        assert sprite != null;

        this.serviceLocator = serviceLocator;
        this.sprite = sprite;
        this.width = sprite.getImage().getWidth(null);
        this.height = sprite.getImage().getHeight(null);
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
        serviceLocator.getRenderer().drawSprite(sprite, (int)(topLeft[0]), (int)(topLeft[1]), width, height);
    }

}
