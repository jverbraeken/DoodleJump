package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class KillScreen implements IScene {

    /**
     * X & Y location in relation to the frame of the play again button.
     */
    private static final double PLAY_AGAIN_BUTTON_X = 0.3, PLAY_AGAIN_BUTTON_Y = 0.6;
    /**
     * X & Y location in relation to the frame of the main menu button.
     */
    private static final double MAIN_MENU_BUTTON_X = 0.6, MAIN_MENU_BUTTON_Y = 0.7;
    /**
     * X & Y location in relation to the frame of the game over text.
     */
    private static final double GAME_OVER_TEXT_X = 0.1, GAME_OVER_TEXT_Y = 0.3;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the KillScreen class.
     */
    private final ILogger logger;
    /**
     * The button that starts a new world.
     */
    private final IButton playAgainButton;
    /**
     * The button that sends you back to the main menu.
     */
    private final IButton mainMenuButton;
    /**
     * Sprite for the bottom of the kill screen.
     */
    private final ISprite bottomKillScreen;
    /**
     * Sprite for the game over text.
     */
    private final ISprite gameOverSprite;
    /**
     * Sprites to be displayed on the background of the KillScreen.
     */
    private ISprite background;
    /**
     * Is the kill screen active, should it be displayed.
     */
    private boolean active = false;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ KillScreen(final IServiceLocator sL, final String gameType) {
        assert sL != null;
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(KillScreen.class);

        background = sL.getSpriteFactory().getBackground();
        bottomKillScreen = sL.getSpriteFactory().getKillScreenBottomSprite();
        gameOverSprite = sL.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton(
                (int) (sL.getConstants().getGameWidth() * PLAY_AGAIN_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * PLAY_AGAIN_BUTTON_Y),
                gameType);
        mainMenuButton = buttonFactory.createMainMenuButton(
                (int) (sL.getConstants().getGameWidth() * MAIN_MENU_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * MAIN_MENU_BUTTON_Y));

    }

    /** {@inheritDoc} */
    @Override
    public final void start() {
        playAgainButton.register();
        mainMenuButton.register();

        active = true;
        logger.info("The kill screen scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public final void stop() {
        playAgainButton.deregister();
        mainMenuButton.deregister();

        active = false;
        logger.info("The kill screen scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        if (active) {
            serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);
            serviceLocator.getRenderer().drawSpriteHUD(
                    this.gameOverSprite,
                    (int) (serviceLocator.getConstants().getGameWidth() * GAME_OVER_TEXT_X),
                    (int) (serviceLocator.getConstants().getGameHeight() * GAME_OVER_TEXT_Y));

            double y = (double) serviceLocator.getConstants().getGameHeight() - (double) bottomKillScreen.getHeight();
            serviceLocator.getRenderer().drawSpriteHUD(this.bottomKillScreen, 0, (int) y);
            playAgainButton.render();
            mainMenuButton.render();
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) { }

}
