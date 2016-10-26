package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import rendering.IRenderer;
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
     * Package protected constructor, only allowing the SceneFactory to create a KillScreen.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ KillScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(KillScreen.class);

        this.background = sL.getSpriteFactory().getBackground();
        this.bottomKillScreen = sL.getSpriteFactory().getKillScreenBottomSprite();
        this.gameOverSprite = sL.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        this.playAgainButton = buttonFactory.createPlayAgainButton(
                KillScreen.PLAY_AGAIN_BUTTON_X,
                KillScreen.PLAY_AGAIN_BUTTON_Y);
        this.mainMenuButton = buttonFactory.createMainMenuButton(
                KillScreen.MAIN_MENU_BUTTON_X,
                KillScreen.MAIN_MENU_BUTTON_Y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        this.playAgainButton.register();
        this.mainMenuButton.register();
        this.logger.info("The kill screen scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        this.playAgainButton.deregister();
        this.mainMenuButton.deregister();
        this.logger.info("The kill screen scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        IConstants constants = this.serviceLocator.getConstants();
        IRenderer renderer = this.serviceLocator.getRenderer();

        renderer.drawSpriteHUD(this.background, 0, 0);
        renderer.drawSpriteHUD(this.gameOverSprite,
                (int) (constants.getGameWidth() * KillScreen.GAME_OVER_TEXT_X),
                (int) (constants.getGameHeight() * KillScreen.GAME_OVER_TEXT_Y));

        double y = (double) constants.getGameHeight() - (double) this.bottomKillScreen.getHeight();
        renderer.drawSpriteHUD(this.bottomKillScreen, 0, (int) y);
        this.playAgainButton.render();
        this.mainMenuButton.render();
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
    public void switchMode() {
    }
}
