package objects.buttons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class Button implements IButton {

    private final IServiceLocator serviceLocator;

    private final ISprite sprite;
    private final int width, height;
    private final int[] topLeft = new int[2], bottomRight = new int[2];
    private final Runnable action;
    private final String name;
    private static final Logger logger = LoggerFactory.getLogger(Button.class);

    /* package */ Button(IServiceLocator serviceLocator, int x, int y, ISprite sprite, Runnable action, String name) {
        super();

        assert serviceLocator != null;
        assert sprite != null;

        this.serviceLocator = serviceLocator;
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
    public void mouseClicked(int x, int y) {
        assert x >= 0 && y >= 0;

        if(x > topLeft[0] && x < bottomRight[0]) {
            if(y > topLeft[1] && y < bottomRight[1]) {
                logger.info("Button clicked: \"" + name + "\"");
                action.run();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(sprite, topLeft[0], topLeft[1], width, height);
    }

}
