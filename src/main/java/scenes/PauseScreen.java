package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import logging.ILogger;
import progression.Mission;
import rendering.Color;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.List;

/**
 * PauseScreen implementation of a scene.
 */
/* package */ class PauseScreen implements IScene, IMouseInputObserver {

    /**
     * The X and Y location for the resume button.
     */
    private static final double RESUME_BUTTON_X = 0.55d, RESUME_BUTTON_Y = 0.75d;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger logger;
    /**
     * The resume button.
     */
    private final IButton resumeButton;
    /**
     * The sprites of the coin, animated.
     */
    private final ISprite[] coinSprites = new ISprite[10];
    /**
     * The background sprite.
     */
    private ISprite background;
    /**
     * The index of the coin animation. Must be between 0 (inclusive) and 10 (exclusive).
     */
    private double coinSpriteIndex;
    /**
     * Default margin for the HUD grid.
     */
    private static final int MARGIN = 10;
    /**
     * The distance between the missions drawn at the screen.
     */
    private static final int DISTANCE_BETWEEN_MISSIONS = 15;

    /**
     * Initialize the pause screen.
     *
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(final IServiceLocator sL) {
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Background
        background = this.serviceLocator.getSpriteFactory().getPauseCoverSprite();

        // Coins
        for (int i = 0; i < 10; i++) {
            coinSprites[i] = this.serviceLocator.getSpriteFactory().getCoinSprite(i + 1);
        }

        // Resume button
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        int resumeButtonX = (int) (sL.getConstants().getGameWidth() * RESUME_BUTTON_X);
        int resumeButtonY = (int) (sL.getConstants().getGameHeight() * RESUME_BUTTON_Y);
        resumeButton = buttonFactory.createResumeButton(resumeButtonX, resumeButtonY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(resumeButton);
        logger.info("The pause scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(resumeButton);
        logger.info("The pause scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        assert background != null;
        assert resumeButton != null;
        assert coinSprites[(int) coinSpriteIndex] != null;

        serviceLocator.getRenderer().drawSpriteHUD(background, 0, 0);

        resumeButton.render();

        ISprite coinSprite = this.coinSprites[(int) coinSpriteIndex];
        final int coinX = MARGIN + coinSprite.getHeight() / 2 - (int) (((double) coinSprite.getWidth() / (double) coinSprite.getHeight()) * (double) coinSprite.getHeight() / 2d);
        final int coinY = serviceLocator.getSpriteFactory().getScoreBarSprite().getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(coinSprite, coinX, coinY);

        final int coinTextX = MARGIN + coinSprite.getHeight() + MARGIN;
        final int coinTextY = coinY + coinSprite.getHeight() / 2;
        serviceLocator.getRenderer().drawTextHUD(coinTextX, coinTextY, Integer.toString(serviceLocator.getProgressionManager().getCoins()), Color.black);

        final List<Mission> missions = serviceLocator.getProgressionManager().getMissions();
        final int missionSpriteHeight = serviceLocator.getSpriteFactory().getAchievementSprite().getHeight();
        for (int i = 0; i < missions.size(); i++) {
            missions.get(i).render(coinY + coinSprite.getHeight() + MARGIN  + i * (missionSpriteHeight + DISTANCE_BETWEEN_MISSIONS));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        coinSpriteIndex += 0.3 * delta;
        coinSpriteIndex = coinSpriteIndex % 10;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(final int x, final int y) {
    }

}
