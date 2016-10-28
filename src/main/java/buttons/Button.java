package buttons;

import groovy.lang.Tuple2;
import logging.ILogger;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This IMMUTABLE class focuses on the implementation of button.
 */
/* package */ final class Button implements IButton {

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the Button class.
     */
    private final ILogger logger;
    /**
     * The sprite of the button.
     */
    private final ISprite sprite;
    /**
     * The width and height of the button.
     */
    private final int width, height;
    /**
     * The hitbox of the button.
     */
    private final int[] topLeft = new int[2], bottomRight = new int[2];
    /**
     * The action when the button is pressed.
     */
    private final Runnable action;
    /**
     * The name of the button.
     */
    private final String name;

    /**
     * Constructor of a new button.
     *
     * @param sL the service locator.
     * @param x  the x position of the button
     * @param y  the y position of the button
     * @param s  the sprite of the button
     * @param a  the action when the button is pressed
     * @param n  the name of the button
     */
    /* package */ Button(final IServiceLocator sL, final int x, final int y,
                         final ISprite s, final Runnable a, final String n) {
        super();

        assert sL != null;
        assert s != null;

        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(Button.class);
        this.sprite = s;
        this.width = s.getImage().getWidth(null);
        this.height = s.getImage().getHeight(null);
        this.topLeft[0] = x;
        this.topLeft[1] = y;
        this.bottomRight[0] = x + width;
        this.bottomRight[1] = y + height;
        this.action = a;
        this.name = n;
    }

    /**
     * Constructor of a new button with a custom width and height.
     *
     * @param sL         the service locator.
     * @param x          the x position of the button
     * @param y          the y position of the button
     * @param s          the sprite of the button
     * @param a          the action when the button is pressed
     * @param n          the name of the button
     * @param dimensions The width and height of the button
     */
    /* package */ Button(final IServiceLocator sL, final int x, final int y,
                         final ISprite s, final Runnable a, final String n,
                         final Tuple2<Integer, Integer> dimensions) {
        super();

        assert sL != null;
        assert s != null;

        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(Button.class);
        this.sprite = s;
        this.topLeft[0] = x;
        this.topLeft[1] = y;
        this.bottomRight[0] = x + dimensions.getFirst();
        this.bottomRight[1] = y + dimensions.getSecond();
        this.action = a;
        this.name = n;
        this.width = dimensions.getFirst();
        this.height = dimensions.getSecond();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.serviceLocator.getRenderer().drawSpriteHUD(
                this.sprite, new Point(this.topLeft[0], this.topLeft[1]), this.width, this.height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(final int x, final int y) {
        assert x >= 0 && y >= 0;

        if (x > this.topLeft[0] && x < this.bottomRight[0] && y > this.topLeft[1] && y < this.bottomRight[1]) {
            this.logger.info("Button clicked: \"" + this.name + "\"");
            Game.schedule(this.action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register() {
        this.serviceLocator.getInputManager().addObserver(this);
        this.logger.info("The button \"" + this.name + "\" registered itself as an observer of the input manager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deregister() {
        this.serviceLocator.getInputManager().removeObserver(this);
        this.logger.info("The button \"" + this.name + "\" removed itself as an observer from the input manager");
    }

}
