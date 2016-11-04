package scenes;

import buttons.IButton;
import rendering.Color;
import resources.IRes;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.Point;

/**
 * Class representing a popup in the game.
 */
public final class Popup implements IRenderable {

    /**
     * Text location relative to width and height of the screen.
     */
    private static final double POPUP_TEXT_X = 0.07, POPUP_TEXT_Y = 0.17;
    /**
     * Ok button location relative to width and height of the screen.
     */
    private static final double POPUP_OK_X = 0.65, POPUP_OK_Y = 0.35;

    /**
     * The serviceLocator of this program instance.
     */
    private IServiceLocator serviceLocator;
    /**
     * The message the Popup displays.
     */
    private String message;
    /**
     * The ok-button of a popup.
     */
    private IButton okButton;

    /**
     * Create a new popup instance.
     * @param msg The message the Popup displays
     */
    public Popup(final IServiceLocator serviceLocator, final String msg) {
        this.serviceLocator = serviceLocator;
        this.message = msg;

        okButton = this.serviceLocator.getButtonFactory().createOkPopupButton(POPUP_OK_X, POPUP_OK_Y, this);
        okButton.register();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.serviceLocator.getRenderer().drawSprite(this.serviceLocator.getSpriteFactory().getSprite(IRes.Sprites.popupBackground), new Point(0, 0));
        int width = this.serviceLocator.getConstants().getGameWidth();
        int height = this.serviceLocator.getConstants().getGameHeight();
        this.serviceLocator.getRenderer().drawText(new Point((int) (POPUP_TEXT_X * width), (int) (POPUP_TEXT_Y * height)), message, Color.black);
        okButton.render();
    }

}