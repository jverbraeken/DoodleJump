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
     * X & Y location in relation to the frame of the regular mode button.
     */
    private static final double REGULAR_MODE_X = 0.2, REGULAR_MODE_Y = 0.25;
    /**
     * X & Y location in relation to the frame of the underwater mode button.
     */
    private static final double UNDERWATER_MODE_X = 0.2, UNDERWATER_MODE_Y = 0.45;
    /**
     * X & Y location in relation to the frame of the space mode button.
     */
    private static final double SPACE_MODE_X = 0.2, SPACE_MODE_Y = 0.65;
    /**
     * X & Y location in relation to the frame of the invert mode button.
     */
    private static final double INVERT_MODE_X = 0.6, INVERT_MODE_Y = 0.65;
    /**
     * X & Y location in relation to the frame of the story mode button.
     */
    private static final double STORY_MODE_X = 0.6, STORY_MODE_Y = 0.25;
    /**
     * X & Y location in relation to the frame of the darkness mode button.
     */
    private static final double DARKNESS_MODE_X = 0.6, DARKNESS_MODE_Y = 0.45;
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
     * Is the choose mode screen active, should it be displayed.
     */
    private boolean active = false;

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
        IButton darknessModeButton = buttonFactory.createDarknessModeButton(
                (int) (sL.getConstants().getGameWidth() * DARKNESS_MODE_X),
                (int) (sL.getConstants().getGameHeight() * DARKNESS_MODE_Y));
        IButton storyModeButton = buttonFactory.createStoryModeButton(
                (int) (sL.getConstants().getGameWidth() * STORY_MODE_X),
                (int) (sL.getConstants().getGameHeight() * STORY_MODE_Y));
        IButton spaceModeButton = buttonFactory.createSpaceModeButton(
                (int) (sL.getConstants().getGameWidth() * SPACE_MODE_X),
                (int) (sL.getConstants().getGameHeight() * SPACE_MODE_Y));
        IButton underwaterModeButton = buttonFactory.createUnderwaterModeButton(
                (int) (sL.getConstants().getGameWidth() * UNDERWATER_MODE_X),
                (int) (sL.getConstants().getGameHeight() * UNDERWATER_MODE_Y));
        IButton invertModeButton = buttonFactory.createInvertModeButton(
                (int) (sL.getConstants().getGameWidth() * INVERT_MODE_X),
                (int) (sL.getConstants().getGameHeight() * INVERT_MODE_Y));
        IButton regularModeButton = buttonFactory.createRegularModeButton(
                (int) (sL.getConstants().getGameWidth() * REGULAR_MODE_X),
                (int) (sL.getConstants().getGameHeight() * REGULAR_MODE_Y));
        buttons.add(mainMenuButton);
        buttons.add(darknessModeButton);
        buttons.add(storyModeButton);
        buttons.add(spaceModeButton);
        buttons.add(underwaterModeButton);
        buttons.add(invertModeButton);
        buttons.add(regularModeButton);

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
