package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IRenderable;
import system.IServiceLocator;

import java.util.ArrayList;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class ShopScreen implements IScene {

    /**
     * X & Y location in relation to the frame of the main menu button.
     */
    private static final double MAIN_MENU_BUTTON_X = 0.35, MAIN_MENU_BUTTON_Y = 0.1;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the ChooseMode class.
     */
    private final ILogger logger;
    /**
     * The sprite on the bottom of the choose mode screen.
     */
    private final ISprite bottomChooseModeScreen;
    /**
     * Sprites of the background of the ChooseMode.
     */
    private ISprite background;
    /**
     * An ArrayList of all the buttons.
     */
    private final ArrayList<IButton> buttons = new ArrayList<>();

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ ShopScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(ShopScreen.class);

        background = sL.getSpriteFactory().getBackground();
        bottomChooseModeScreen = sL.getSpriteFactory().getKillScreenBottomSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        IButton mainMenuButton = buttonFactory.createMainMenuButton(
                (int) (sL.getConstants().getGameWidth() * MAIN_MENU_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * MAIN_MENU_BUTTON_Y));
        buttons.add(mainMenuButton);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        for (IButton button : buttons) {
            button.register();
        }

        active = true;
        logger.info("The choose mode scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        for (IButton button : buttons) {
            button.deregister();
        }

        active = false;
        logger.info("The choose mode scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);
        double y = (double) serviceLocator.getConstants().getGameHeight() - (double) bottomChooseModeScreen.getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(this.bottomChooseModeScreen, 0, (int) y);
        buttons.forEach(IRenderable::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
    }

}
