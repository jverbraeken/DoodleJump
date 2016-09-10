package objects.buttons;

import objects.GameObject;
import rendering.IRenderer;
import rendering.Renderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by erico on 10-9-2016.
 */
public class PlayButton extends Button implements IButton {

    private Image buttonImage;
    private int xPos, yPos;

    public PlayButton(int x, int y, Image buttonImage) {
        super();
        this.buttonImage = buttonImage;
        this.setXPos(x);
        this.setYPos(y);
    }

    @Override
    public boolean collide(GameObject that) {
        return false;
    }

    @Override
    public int getXPos() {
        return this.xPos;
    }

    @Override
    public int getYPos() {
        return this.yPos;
    }

    @Override
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;

    }

    @Override
    public double[] getHitBox() {
        return new double[0];
    }

    @Override
    public Object getSprite() {
        return null;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(this.buttonImage, this.getXPos(), this.getYPos(), null);
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }

}
