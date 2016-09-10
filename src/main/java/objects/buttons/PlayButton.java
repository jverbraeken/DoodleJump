package objects.buttons;

import objects.GameObject;
import rendering.IRenderer;
import rendering.Renderer;
import system.IServiceLocator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <b>Immutable</b>
 */
public class PlayButton extends Button implements IButton {

    private final IServiceLocator serviceLocator;

    private final Image buttonImage;
    private final int xPos, yPos, width, height;
    /** The cached values of xPos + width and yPos + height */
    private final int xxPos, yyPos;

    /* package */ PlayButton(IServiceLocator serviceLocator, int x, int y, int width, int height, Image buttonImage) {
        super();

        assert serviceLocator != null;
        assert buttonImage != null;

        this.serviceLocator = serviceLocator;
        this.buttonImage = buttonImage;
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        this.xxPos = x + width;
        this.yyPos = y + height;
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

    //TODO this should be an immutable object -> MovableGameObject superclass??? More methods don't make sense...
    public void setXPos(int x) {}
    public void setYPos(int y) {}

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
        assert g != null;
        g.drawImage(this.buttonImage, xPos, yPos, width, height, null);
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }

    @Override
    public void mouseClicked(int x, int y) {
        assert x >= 0 && y >= 0;
        if (x > xPos && y > yPos && x <= xxPos && y <= yyPos) {
            System.out.println("Play button clicked!");
            serviceLocator.getRenderer().setScene(serviceLocator.getSceneFactory().newWorld());
        }
    }
}
