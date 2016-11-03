package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.HighScore;
import rendering.Color;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Score screen implementation of a scene.
 */
/* package */ final class ScoreScreen implements IScene {

    /**
     * The X and Y location for the resume button.
     */
    private static final double MENU_BUTTON_X = 0.55d, MENU_BUTTON_Y = 0.80d;
    /**
     * Height of a score entry.
     */
    private static final double ENTRY_HEIGHT = .05;
    /**
     * Y-offset for the scores.
     */
    private static final int SCORE_LIST_TOP_Y_OFFSET = 15;
    /**
     * The height of an entry in the ScoreScreen.
     */
    private static AtomicInteger entryHeight = new AtomicInteger();
    /**
     * The height of the top part of the ScoreScreen.
     */
    private static AtomicInteger scoreListTopY = new AtomicInteger();
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
        this.bottom = spriteFactory.getSprite(IRes.Sprites.scoreScreenBottom);
        this.left = spriteFactory.getSprite(IRes.Sprites.scoreScreenLeft);
        this.top = spriteFactory.getSprite(IRes.Sprites.scoreScreenTop);

        IButtonFactory buttonFactory = sL.getButtonFactory();
        this.menuButton = buttonFactory.createMainMenuButton(ScoreScreen.MENU_BUTTON_X, ScoreScreen.MENU_BUTTON_Y);

        entryHeight.set((int) (serviceLocator.getConstants().getGameHeight() * ENTRY_HEIGHT));
        scoreListTopY.set(this.top.getHeight() + SCORE_LIST_TOP_Y_OFFSET);
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
            int backgroundY = scoreListTopY.get() + (i - 1) * entryHeight.get();
            Color color = Math.abs(i) % 2 == 1 ? Color.scoreEntryEven : Color.scoreEntryUneven;
            renderer.fillRectangle(new Point(0, backgroundY), constants.getGameWidth(), entryHeight.get(), color);

            // Entry name & value
            HighScore score = highScores.get(i);
            int entryY = scoreListTopY.get() + i * entryHeight.get();
            String msg = score.getName() + " - " + score.getScore();
            renderer.drawText(new Point(this.left.getWidth(), entryY), msg, Color.black);
        }

        // Draw the hud.
        renderer.drawSpriteHUD(this.bottom, new Point(0, this.top.getHeight() + this.left.getHeight()));
        renderer.drawSpriteHUD(this.left, new Point(0, this.top.getHeight()));
        renderer.drawSpriteHUD(this.top, new Point(0, 0));

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
    public void switchDisplay(PauseScreenModes mode) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateButton(final Powerups powerup, final double x, final double y) {
    }

}
