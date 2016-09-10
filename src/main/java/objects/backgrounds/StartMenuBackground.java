package objects.backgrounds;

import objects.GameObject;

import java.awt.*;

/**
 * Created by erico on 10-9-2016.
 */
public class StartMenuBackground extends GameObject implements IBackground {

    private Image image;

    StartMenuBackground(Image image) {
        this.image = image;
    }

    @Override
    /** @{inheritDoc} */
    public void paint(Graphics g) {
        g.drawImage(this.image, 0, 0, null);
    }

    @Override
    /** @{inheritDoc} */
    public void animate() { }

    @Override
    /** @{inheritDoc} */
    public void move() { }

    @Override
    /** @{inheritDoc} */
    public void update() { }

}
