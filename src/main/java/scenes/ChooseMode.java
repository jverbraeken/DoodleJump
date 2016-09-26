package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class ChooseMode implements IScene, IMouseInputObserver {

    /**
     * The logger for the KillScreen class.
     */
    private final ILogger LOGGER;
    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double REGULAR_MODE_X = 0.1, REGULAR_MODE_Y = 0.3;

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double UNDERWATER_MODE_X = 0.1, UNDERWATER_MODE_Y = 0.5;

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double SPACE_MODE_X = 0.1, SPACE_MODE_Y = 0.7;

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double INVERT_MODE_X = 0.6, INVERT_MODE_Y = 0.7;

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double STORY_MODE_X = 0.6, STORY_MODE_Y = 0.3;

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double DARKNESS_MODE_X = 0.6, DARKNESS_MODE_Y = 0.5;

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
    private final ISprite background, bottomKillScreen, gameOverSprite;
    /**
     * Is the kill screen active, should it be displayed.
     */
    private boolean active = false;

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
        gameOverSprite = sL.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        mainMenuButton = buttonFactory.createMainMenuButton((int) (sL.getConstants().getGameWidth() * MAIN_MENU_BUTTON_X), (int) (sL.getConstants().getGameHeight() * MAIN_MENU_BUTTON_Y));
        darknessModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * DARKNESS_MODE_X), (int) (sL.getConstants().getGameHeight() * DARKNESS_MODE_Y));
        storyModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * STORY_MODE_X), (int) (sL.getConstants().getGameHeight() * STORY_MODE_Y));
        spaceModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * SPACE_MODE_X), (int) (sL.getConstants().getGameHeight() * SPACE_MODE_Y));
        underwaterModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * UNDERWATER_MODE_X), (int) (sL.getConstants().getGameHeight() * UNDERWATER_MODE_Y));
        invertModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * INVERT_MODE_X), (int) (sL.getConstants().getGameHeight() * INVERT_MODE_Y));
        regularModeButton = buttonFactory.createDarknessModeButton((int) (sL.getConstants().getGameWidth() * REGULAR_MODE_X), (int) (sL.getConstants().getGameHeight() * REGULAR_MODE_Y));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        sL.getInputManager().addObserver(mainMenuButton);
        sL.getInputManager().addObserver(regularModeButton);
        sL.getInputManager().addObserver(spaceModeButton);
        sL.getInputManager().addObserver(underwaterModeButton);
        sL.getInputManager().addObserver(invertModeButton);
        sL.getInputManager().addObserver(storyModeButton);
        sL.getInputManager().addObserver(darknessModeButton);
        active = true;
        LOGGER.info("The choose mode scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        sL.getInputManager().removeObserver(mainMenuButton);
        sL.getInputManager().removeObserver(spaceModeButton);
        sL.getInputManager().removeObserver(underwaterModeButton);
        sL.getInputManager().removeObserver(invertModeButton);
        sL.getInputManager().removeObserver(storyModeButton);
        sL.getInputManager().removeObserver(regularModeButton);
        sL.getInputManager().removeObserver(darknessModeButton);
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
            mainMenuButton.render();
            spaceModeButton.render();
            underwaterModeButton.render();
            regularModeButton.render();
            darknessModeButton.render();
            invertModeButton.render();
            storyModeButton.render();
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
    public final void mouseClicked(final int x, final int y) {
    }

}
