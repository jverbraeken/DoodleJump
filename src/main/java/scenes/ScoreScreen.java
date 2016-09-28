package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import input.IMouseInputObserver;
import logging.ILogger;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;
import system.Score;

import java.util.ArrayList;

/**
 * Score screen implementation of a scene.
 */
/* package */ class ScoreScreen implements IScene, IMouseInputObserver {

    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger LOGGER;
    /**
     * The X and Y location for the resume button.
     */
    private static final double MENU_BUTTON_X = 0.55d, MENU_BUTTON_Y = 0.80d;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The cover sprite of the main menu.
     */
    private final ISprite bottom, left, top;
    /**
     * The button on the score screen.
     */
    private final IButton menuButton;
    /**
     * A list of high scores for the game.
     */
    private final ArrayList<Score> highScores;

    /* package */ ScoreScreen(IServiceLocator sL) {
        this.serviceLocator = sL;
        this.highScores = Game.getHighScores();
        LOGGER = sL.getLoggerFactory().createLogger(ScoreScreen.class);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.bottom = spriteFactory.getScoreScreenBottom();
        this.left = spriteFactory.getScoreScreenLeft();
        this.top = spriteFactory.getScoreScreenTop();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        int menuButtonX = (int) (sL.getConstants().getGameWidth() * MENU_BUTTON_X);
        int menuButtonY = (int) (sL.getConstants().getGameHeight() * MENU_BUTTON_Y);
        this.menuButton = buttonFactory.createMainMenuButton(menuButtonX, menuButtonY);
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        IConstants constants = this.serviceLocator.getConstants();
        IRenderer renderer = this.serviceLocator.getRenderer();
        renderer.drawSpriteHUD(this.bottom, 0, this.top.getHeight() + this.left.getHeight());
        renderer.drawSpriteHUD(this.left, 0, this.top.getHeight());
        renderer.drawSpriteHUD(this.top, 0, 0);

        for (int i = 0; i < highScores.size(); i++) {
            Score score = highScores.get(i);
            int scoreX = this.left.getWidth();
            int scoreY = this.top.getHeight() + i * 24;
            renderer.drawRectangle(scoreX, scoreY, constants.getGameWidth() - scoreX, 24);
            renderer.drawText(score.getName(), scoreX, scoreY);
        }

        this.menuButton.render();
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(this.menuButton);
        LOGGER.info("The score scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(this.menuButton);
        LOGGER.info("The score scene is no longer displaying");
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
