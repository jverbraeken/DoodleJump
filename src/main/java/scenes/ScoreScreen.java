package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import progression.HighScore;
import rendering.Color;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.List;

/**
 * Score screen implementation of a scene.
 */
/* package */ class ScoreScreen implements IScene {

    /**
     * The X and Y location for the resume button.
     */
    private static final double MENU_BUTTON_X = 0.55d, MENU_BUTTON_Y = 0.80d;
    /**
     * Height of a score entry.
     */
    private static final double ENTRY_HEIGHT = .05;

    /**
     * The height of an entry in the ScoreScreen.
     */
    private static int entryHeight;
    /**
     * The height of the top part of the ScoreScreen.
     */
    private static int scoreListTopY;
    /**
     * Y-offset for the scores
     */
    private static final int scoreListTopYOffset = 15;
    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger logger;
    /**
     * The cover sprite of the main menu.
     */
    private final ISprite bottom, left, top;
    /**
     * The button on the score screen.
     */
    private final IButton menuButton;

    /**
     * Package protected constructor so only the SceneFactory can create a ScoreScreen.
     *
     * @param sL The serviceLocator.
     */
    /* package */ ScoreScreen(final IServiceLocator sL) {
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(ScoreScreen.class);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.bottom = spriteFactory.getScoreScreenBottom();
        this.left = spriteFactory.getScoreScreenLeft();
        this.top = spriteFactory.getScoreScreenTop();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        this.menuButton = buttonFactory.createMainMenuButton(ScoreScreen.MENU_BUTTON_X, ScoreScreen.MENU_BUTTON_Y);

        entryHeight = (int) (serviceLocator.getConstants().getGameHeight() * ENTRY_HEIGHT);
        scoreListTopY = this.top.getHeight() + scoreListTopYOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        IConstants constants = this.serviceLocator.getConstants();
        IRenderer renderer = this.serviceLocator.getRenderer();
        renderer.clear();

        // Draw the list of scores.
        List<HighScore> highScores = serviceLocator.getProgressionManager().getHighscores();
        for (int i = 0; i < highScores.size(); i++) {
            // Entry background
            int backgroundY = scoreListTopY + (i - 1) * entryHeight;
            Color color = i % 2 == 1 ? Color.scoreEntryEven : Color.scoreEntryUneven;
            renderer.fillRectangle(0, backgroundY, constants.getGameWidth(), entryHeight, color);

            // Entry name & value
            HighScore score = highScores.get(i);
            int entryY = scoreListTopY + i * entryHeight;
            String msg = score.getName() + " - " + score.getScore();
            renderer.drawText(this.left.getWidth(), entryY, msg, Color.black);
        }

        // Draw the hud.
        renderer.drawSpriteHUD(this.bottom, 0, this.top.getHeight() + this.left.getHeight());
        renderer.drawSpriteHUD(this.left, 0, this.top.getHeight());
        renderer.drawSpriteHUD(this.top, 0, 0);

        // Draw the buttons.
        this.menuButton.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.menuButton.register();
        logger.info("The score scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.menuButton.deregister();
        logger.info("The score scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchMode() {
    }
}
