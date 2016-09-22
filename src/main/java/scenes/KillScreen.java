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
     * The service locator for the menu scene.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the PauseScreen class.
     */
    private static ILogger LOGGER;
    /**
     * Is the pause screen active, should it be displayed.
     */
    private boolean active = false;

    private final IButton playAgainButton;
    private final IButton mainMenuButton;
    private final ISprite background, bottomKillScreen, gameOverSprite;
    private final double playAgainButtonXPercentage = 0.3;
    private final double playAgainButtonYPercentage = 0.6;
    private final double mainMenuButtonXPercentage = 0.6;
    private final double mainMenuButtonYPercentage = 0.7;
    private final double gameOverTextXPercentage = 0.1;
    private final double gameOverTextYPercentage = 0.3;

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

    @Override
    public void update(double delta) {
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(int x, int y) {
    }

}
