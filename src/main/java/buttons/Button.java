package buttons;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class focuses on the implementation of button.
 */
/* package */ class Button implements IButton {

    /**
     * The logger for the Button class.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator sL;
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
    /* package */ Button(IServiceLocator sL, int x, int y, ISprite s, Runnable a, String n) {
        super();

        assert sL != null;
        assert s != null;

        this.sL = sL;
        this.LOGGER = sL.getLoggerFactory().createLogger(Button.class);
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
     * {@inheritDoc}
     */
    @Override
    public void render() {
        sL.getRenderer().drawSpriteHUD(sprite, topLeft[0], topLeft[1], width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void mouseClicked(final int x, final int y) {
        assert x >= 0 && y >= 0;

        if (x > topLeft[0] && x < bottomRight[0] && y > topLeft[1] && y < bottomRight[1]) {
            LOGGER.info("Button clicked: \"" + name + "\"");
            action.run();
        }
    }
}
