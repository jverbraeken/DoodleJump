package scenes;

import input.IMouseInputObserver;
import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

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

    private final IButton playAgainButton;
    private final IButton mainMenuButton;
    private final ISprite background, bottomKillScreen, gameOverSprite;
    private static final double playAgainButtonXPercentage = 0.3d;
    private static final double playAgainButtonYPercentage = 0.6d;
    private static final double mainMenuButtonXPercentage = 0.6d;
    private static final double mainMenuButtonYPercentage = 0.7d;
    private static final double gameOverTextXPercentage = 0.1d;
    private static final double gameOverTextYPercentage = 0.3d;

    /**
     * Package constructor so only the SceneFactory creates a KillScreen.
     *
     * @param sL The service locator.
     */
    /* package */ KillScreen(IServiceLocator sL) {
        serviceLocator = sL;
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
    public void start() {
        serviceLocator.getInputManager().addObserver(playAgainButton);
        serviceLocator.getInputManager().addObserver(mainMenuButton);
        active = true;
        LOGGER.info("The kill screen scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playAgainButton);
        serviceLocator.getInputManager().removeObserver(mainMenuButton);
        active = false;
        LOGGER.info("The kill screen scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void paint() {
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
    public void update(double delta) {
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(int x, int y) {
    }

}
