package scenes;
import buttons.IButton;
import rendering.Color;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public final class Popup implements IRenderable {

    /**
     * The message the Popup displays.
     */
    private String message;

    /**
     * The serviceLocator of this program instance.
     */
    private IServiceLocator serviceLocator;

    /**
     * Text location relative to width and height of the screen.
     */
    private static final double POPUP_TEXT_X = 0.1, POPUP_TEXT_Y = 0.15;
    /**
     * Ok button location relative to width and height of the screen.
     */
    private static final double POPUP_OK_X = 0.65, POPUP_OK_Y = 0.35;

    private IButton okButton;

    /**
     * Create a new popup instance.
     * @param msg The message the Popup displays
     */
    public Popup(final IServiceLocator serviceLocator, final String msg) {
        this.serviceLocator = serviceLocator;
        this.message = msg;
        int width = this.serviceLocator.getConstants().getGameWidth();
        int height = this.serviceLocator.getConstants().getGameHeight();
        System.out.println(POPUP_OK_X * width + " - " + POPUP_OK_Y * height);
        //okButton = this.serviceLocator.getButtonFactory().createMultiplayerButton(0, 0);

        okButton = this.serviceLocator.getButtonFactory().createOkPopupButton(POPUP_OK_X, POPUP_OK_Y, this);
    }

    @Override
    public void render() {
        this.serviceLocator.getRenderer().drawSprite(this.serviceLocator.getSpriteFactory().getPopupBackground(), 0, 0);
        int width = this.serviceLocator.getConstants().getGameWidth();
        int height = this.serviceLocator.getConstants().getGameHeight();
        this.serviceLocator.getRenderer().drawText((int) (POPUP_TEXT_X * width), (int) (POPUP_TEXT_Y * height), message, Color.black);
        okButton.render();
        //this.serviceLocator.getButtonFactory().createOkPopupButton(0, 0, this);
    }
}