package objects.buttons;

import objects.AGameObject;
import rendering.IRenderer;
import rendering.Renderer;
import system.Game;
import system.IServiceLocator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <b>Immutable</b>
 */
public class PlayButton implements IButton {

    private final IServiceLocator serviceLocator;

    private final Image buttonImage;
    private int xPos, yPos, width, height;
    /** The cached values of xPos, yPos + height/2, xPos + width and yPos + height */
    private final int clickXPos, clickYPos, clickXXPos, clickYYpos;

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
        this.clickXPos = x;
        this.clickYPos = y + height/2;
        this.clickXXPos = clickXPos + width;
        this.clickYYpos = clickYPos + height;
    }

    @Override
    public boolean collide(AGameObject that) {
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
    public void setXPos(int x) {
        this.xPos = x;
    }
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
    public void paint() {
        serviceLocator.getRenderer().drawImage(this.buttonImage, xPos, yPos, width, height);
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }

    @Override
    public void mouseClicked(int x, int y) {

        System.out.println("Play button received mouse click!");
        assert x >= 0 && y >= 0;
        if (x > clickXPos && y > clickYPos && x <= clickXXPos && y <= clickYYpos) {
            System.out.println("Play button clicked!");
            Game.setScene(serviceLocator.getSceneFactory().newWorld());
        }
    }
}
