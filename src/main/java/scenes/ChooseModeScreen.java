package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.*;
import rendering.Color;
import resources.sprites.ISprite;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class ChooseModeScreen implements IScene {

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
    private static final double MAIN_MENU_BUTTON_X = 0.35, MAIN_MENU_BUTTON_Y = 0.13;

    /**
     * The height of the rectangle on the top and the Y location of the rank text.
     */
    private static final int TOP_RECTANGLE_HEIGHT = 65, POPUP_TEXT_Y = 10;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the ChooseModeScreen class.
     */
    private final ILogger logger;
    /**
     * The sprite on the bottom of the choose mode screen.
     */
    private final ISprite bottomChooseModeScreen;
    /**
     * Sprites of the background of the ChooseModeScreen.
     */
    private ISprite background;
    /**
     * An ArrayList of all the buttons.
     */
    private final ArrayList<IButton> buttons = new ArrayList<>();
    /**
     * The rank the player has.
     */
    private Ranks rank;

    /**
     * If the popup is active.
     */
    public static boolean activePopup = false;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ ChooseModeScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(ChooseModeScreen.class);

        background = sL.getSpriteFactory().getBackground();
        bottomChooseModeScreen = sL.getSpriteFactory().getKillScreenBottomSprite();

        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        rank = progressionManager.getRank();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        IButton mainMenuButton = buttonFactory.createMainMenuButton(
                MAIN_MENU_BUTTON_X,
                MAIN_MENU_BUTTON_Y);
        IButton darknessModeButton = buttonFactory.createDarknessModeButton(
                DARKNESS_MODE_X,
                DARKNESS_MODE_Y);
        IButton storyModeButton = buttonFactory.createStoryModeButton(
                STORY_MODE_X,
                STORY_MODE_Y);
        IButton spaceModeButton = buttonFactory.createSpaceModeButton(
                SPACE_MODE_X,
                SPACE_MODE_Y);
        IButton underwaterModeButton = buttonFactory.createUnderwaterModeButton(
                UNDERWATER_MODE_X,
                UNDERWATER_MODE_Y);
        IButton invertModeButton = buttonFactory.createInvertModeButton(
                INVERT_MODE_X,
                INVERT_MODE_Y);
        IButton regularModeButton = buttonFactory.createRegularModeButton(
                REGULAR_MODE_X,
                REGULAR_MODE_Y);
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
        renderByRank();

        if(activePopup == true) {
            renderPopup();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
    }

    /**
     * Renders the popup with a message given in the attribute.
     */
    private void renderPopup() {
        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        //Ranks rank = progressionManager.getRank();
        IConstants constants = this.serviceLocator.getConstants();
        IRenderer renderer = this.serviceLocator.getRenderer();

        renderer.fillRectangle(0, 0, constants.getGameWidth(), TOP_RECTANGLE_HEIGHT, rendering.Color.halfOpaqueWhite);
        renderer.drawText(0, POPUP_TEXT_Y, "Rank: " + rank.getName(), Color.red);
    }

    /**
     * Renders the crosses dependent on the rank.
     */
    private void renderByRank() {
        int rankLevel = rank.getLevelNumber();
        int gameWidth = this.serviceLocator.getConstants().getGameWidth();
        int gameHeight = this.serviceLocator.getConstants().getGameHeight();
        ISprite redCross = this.serviceLocator.getSpriteFactory().getRedCross();
        if (rankLevel < Game.Modes.story.getRankRequired()) {
            serviceLocator.getRenderer().drawSprite(redCross, (int) (STORY_MODE_X * gameWidth), (int) (STORY_MODE_Y * gameHeight));
        }
        if (rankLevel < Game.Modes.underwater.getRankRequired()) {
            serviceLocator.getRenderer().drawSprite(redCross, (int) (UNDERWATER_MODE_X * gameWidth), (int) (UNDERWATER_MODE_Y * gameHeight));
        }
        if (rankLevel < Game.Modes.space.getRankRequired()) {
            serviceLocator.getRenderer().drawSprite(redCross, (int) (SPACE_MODE_X * gameWidth), (int) (SPACE_MODE_Y * gameHeight));
        }
        if (rankLevel < Game.Modes.darkness.getRankRequired()) {
            serviceLocator.getRenderer().drawSprite(redCross, (int) (DARKNESS_MODE_X * gameWidth), (int) (DARKNESS_MODE_Y * gameHeight));
        }
        if (rankLevel < Game.Modes.invert.getRankRequired()) {
            serviceLocator.getRenderer().drawSprite(redCross, (int) (INVERT_MODE_X * gameWidth), (int) (INVERT_MODE_Y * gameHeight));
        }
    }

}
