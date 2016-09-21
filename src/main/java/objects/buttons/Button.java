package objects.buttons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class focuses on the implementation of button.
 */
public class Button implements IButton {

    /**
     * The logger of the game.
     */
    private final Logger logger = LoggerFactory.getLogger(Button.class);
    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The sprite of the button.
     */
    private final ISprite sprite;
    /**
     * The height and width of the button.
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
     * @param sL the service locator.
     * @param x the x position of the button
     * @param y the y position of the button
     * @param s the sprite of the button
     * @param a the action when the button is pressed
     * @param n the name of the button
     */
    Button(final IServiceLocator sL, final int x, final int y, final ISprite s, final Runnable a, final String n) {
        super();

        assert sL != null;
        assert s != null;

        this.serviceLocator = sL;
        this.sprite = s;
        this.width = sprite.getImage().getWidth(null);
        this.height = sprite.getImage().getHeight(null);
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
    public final void render() {
        serviceLocator.getRenderer().drawSprite(sprite, topLeft[0], topLeft[1], width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void mouseClicked(final int x, final int y) {
        assert x >= 0 && y >= 0;

        if (x > topLeft[0] && x < bottomRight[0]) {
            if (y > topLeft[1] && y < bottomRight[1]) {
                logger.info("Button clicked: \"" + name + "\"");
                action.run();
            }
        }
    }
}
