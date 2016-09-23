package buttons;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/* package */ class Button implements IButton {

    /**
     * The LOGGER for the button.
     */
    private final ILogger LOGGER;
    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The sprite of the button.
     */
    private final ISprite sprite;
    /**
     * The width & height of the button.
     */
    private final int width, height;
    /**
     * Coordinates for the top left and bottom right of the button.
     */
    private final int[] topLeft = new int[2], bottomRight = new int[2];
    /**
     * The action to perform when the button has been clicked.
     */
    private final Runnable action;
    /**
     * The name of the button.
     */
    private final String name;

    /**
     * Package visible constructor to only let the ButtonFactory create a Button.
     *
     * @param serviceLocator A reference to the service locator.
     * @param x The x coordinate for the button.
     * @param y The y coordinate for the button.
     * @param sprite The sprite for the button.
     * @param action The action to perform when the button is clicked.
     * @param name The name for the button.
     */
    /* package */ Button(IServiceLocator serviceLocator, int x, int y, ISprite sprite, Runnable action, String name) {
        super();

        assert serviceLocator != null;
        assert sprite != null;

        this.serviceLocator = serviceLocator;
        this.LOGGER = serviceLocator.getLoggerFactory().createLogger(Button.class);
        this.sprite = sprite;
        this.width = sprite.getImage().getWidth(null);
        this.height = sprite.getImage().getHeight(null);
        this.topLeft[0] = x;
        this.topLeft[1] = y;
        this.bottomRight[0] = x + width;
        this.bottomRight[1] = y + height;
        this.action = action;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(sprite, topLeft[0], topLeft[1], width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(int x, int y) {
        assert x >= 0 && y >= 0;

        if (x > topLeft[0] && x < bottomRight[0]) {
            if(y > topLeft[1] && y < bottomRight[1]) {
                LOGGER.info("Button clicked: \"" + name + "\"");
                action.run();
            }
        }
    }

}
