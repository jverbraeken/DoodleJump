package buttons;

import logging.Console;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/* package */ class Button implements IButton {

    private final IServiceLocator serviceLocator;

    private final ISprite sprite;
    private final int width, height;
    private final int[] topLeft = new int[2], bottomRight = new int[2];
    private final Runnable action;
    private final String name;
    private final ILogger logger;

    /**
     * TODO: ADD JAVADOC
     */
    /* package */ Button(IServiceLocator serviceLocator, int x, int y, ISprite sprite, Runnable action, String name) {
        super();

        assert serviceLocator != null;
        assert sprite != null;

        this.serviceLocator = serviceLocator;
        this.logger = serviceLocator.getLoggerFactory().createLogger(Button.class);
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

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(sprite, topLeft[0], topLeft[1], width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(int x, int y) {
        assert x >= 0 && y >= 0;

        if (x > topLeft[0] && x < bottomRight[0]) {
            if(y > topLeft[1] && y < bottomRight[1]) {
                logger.info("Button clicked: \"" + name + "\"");
                action.run();
            }
        }
    }

}
