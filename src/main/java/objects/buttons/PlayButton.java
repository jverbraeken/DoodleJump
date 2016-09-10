package objects.buttons;

import objects.GameObject;

import java.awt.*;

/**
 * Created by erico on 10-9-2016.
 */
public class PlayButton extends GameObject implements IButton {

    private Image buttonImage;

    public PlayButton(Image buttonImage) {
        this.buttonImage = buttonImage;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(this.buttonImage, 10, 10, null);
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }

}
