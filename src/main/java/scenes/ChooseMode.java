package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.ArrayList;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class ChooseMode implements IScene {

    /**
     * The logger for the KillScreen class.
     */
    private final ILogger LOGGER;
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
    private final IServiceLocator sL;
    /**
     * The buttons of the choose mode screen.
     */
    private final IButton mainMenuButton, darknessModeButton, spaceModeButton, underwaterModeButton, invertModeButton, regularModeButton, storyModeButton;
    /**
     * Sprites to be displayed on the background of the killscreen.
     */
    private ISprite background;
    private final ISprite bottomKillScreen;
    /**
     * Is the kill screen active, should it be displayed.
     */
    private boolean active = false;
    /**
     * an arrayList of all the buttons.
     */
    private ArrayList<IButton> buttons;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ ChooseMode(final IServiceLocator sL) {
        assert sL != null;
        this.sL = sL;
        LOGGER = sL.getLoggerFactory().createLogger(ChooseMode.class);

        background = sL.getSpriteFactory().getBackground();
        bottomKillScreen = sL.getSpriteFactory().getKillScreenBottomSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        mainMenuButton = buttonFactory.createMainMenuButton((int) (sL.getConstants().getGameWidth() * MAIN_MENU_BUTTON_X), (int) (sL.getConstants().getGameHeight() * MAIN_MENU_BUTTON_Y));
        darknessModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * DARKNESS_MODE_X), (int) (sL.getConstants().getGameHeight() * DARKNESS_MODE_Y));
        storyModeButton = buttonFactory.createStoryModeButton((int) (sL.getConstants().getGameWidth() * STORY_MODE_X), (int) (sL.getConstants().getGameHeight() * STORY_MODE_Y));
        spaceModeButton = buttonFactory.createSpaceModeButton((int) (sL.getConstants().getGameWidth() * SPACE_MODE_X), (int) (sL.getConstants().getGameHeight() * SPACE_MODE_Y));
        underwaterModeButton = buttonFactory.createUnderwaterModeButton((int) (sL.getConstants().getGameWidth() * UNDERWATER_MODE_X), (int) (sL.getConstants().getGameHeight() * UNDERWATER_MODE_Y));
        invertModeButton = buttonFactory.createInvertModeButton((int) (sL.getConstants().getGameWidth() * INVERT_MODE_X), (int) (sL.getConstants().getGameHeight() * INVERT_MODE_Y));
        regularModeButton = buttonFactory.createRegularModeButton((int) (sL.getConstants().getGameWidth() * REGULAR_MODE_X), (int) (sL.getConstants().getGameHeight() * REGULAR_MODE_Y));
        buttons = new ArrayList();
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
        for(IButton b : buttons){
            sL.getInputManager().addObserver(b);
        }
        active = true;
        LOGGER.info("The choose mode scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        for(IButton b : buttons){
            sL.getInputManager().removeObserver(b);
        }
        active = false;
        LOGGER.info("The choose mode scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (active) {
            sL.getRenderer().drawSpriteHUD(this.background, 0, 0);
            double y = (double) sL.getConstants().getGameHeight() - (double) bottomKillScreen.getHeight();
            sL.getRenderer().drawSpriteHUD(this.bottomKillScreen, 0, (int) y);
            for(IButton b : buttons){
                b.render();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetBackground() {
        background = sL.getSpriteFactory().getBackground();
    }

}
