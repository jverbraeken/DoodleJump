package scenes;

import input.IMouseInputObserver;
import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class KillScreen implements IScene, IMouseInputObserver {

    /**
     * The logger for the KillScreen class.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * Is the kill screen active, should it be displayed.
     */
    private boolean active = false;

    /**
     * The button that starts a new world.
     */
    private final IButton playAgainButton;
    /**
     * The button that sends you back to the main menu.
     */
    private final IButton mainMenuButton;
    /**
     * Sprites to be displayed on the background of the killscreen.
     */
    private final ISprite background, bottomKillScreen, gameOverSprite;
    /**
     * X location in relation to the frame of the play again button.
     */
    private final double playAgainButtonXPercentage = 0.3;
    /**
     * Y location in relation to the frame of the play again button.
     */
    private final double playAgainButtonYPercentage = 0.6;
    /**
     * X location in relation to the frame of the main menu button.
     */
    private final double mainMenuButtonXPercentage = 0.6;
    /**
     * Y location in relation to the frame of the main menu button.
     */
    private final double mainMenuButtonYPercentage = 0.7;
    /**
     * X location in relation to the frame of the game over text.
     */
    private final double gameOverTextXPercentage = 0.1;
    /**
     * Y location in relation to the frame of the game over text.
     */
    private final double gameOverTextYPercentage = 0.3;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ KillScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(KillScreen.class);

        background = serviceLocator.getSpriteFactory().getBackground();
        bottomKillScreen = serviceLocator.getSpriteFactory().getKillScreenBottomSprite();
        gameOverSprite = serviceLocator.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton((int) (Game.WIDTH * playAgainButtonXPercentage), (int) (Game.HEIGHT * playAgainButtonYPercentage));
        mainMenuButton = buttonFactory.createMainMenuButton((int) (Game.WIDTH * mainMenuButtonXPercentage), (int) (Game.HEIGHT * mainMenuButtonYPercentage));

    }

    /** {@inheritDoc} */
    @Override
    public final void start() {
        serviceLocator.getInputManager().addObserver(playAgainButton);
        serviceLocator.getInputManager().addObserver(mainMenuButton);
        active = true;
        LOGGER.info("The kill screen scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public final void stop() {
        serviceLocator.getInputManager().removeObserver(playAgainButton);
        serviceLocator.getInputManager().removeObserver(mainMenuButton);
        active = false;
        LOGGER.info("The kill screen scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public final void paint() {
        if (active) {
            IRenderer renderer = serviceLocator.getRenderer();

            // Render background
            renderer.drawSprite(this.background, 0, 0 );
            renderer.drawSprite(this.bottomKillScreen, 0, Game.HEIGHT - bottomKillScreen.getHeight());

            // Render misc
            int gameOverX = (int) (Game.WIDTH * gameOverTextXPercentage);
            int gameOverY = (int) (Game.HEIGHT * gameOverTextYPercentage);
            renderer.drawSprite(this.gameOverSprite, gameOverX, gameOverY);

            // Render buttons
            playAgainButton.render();
            mainMenuButton.render();
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
    }

    /** {@inheritDoc} */
    public final void mouseClicked(final int x, final int y) {
    }

}
