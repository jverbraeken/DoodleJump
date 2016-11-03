package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.Color;
import rendering.IRenderer;
import rendering.TextAlignment;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ final class KillScreen implements IScene {

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
     * X & Y location in relation to the frame of the experience text.
     */
    private static final double EXP_TEXT_X = 0.6, EXP_TEXT_Y = 0.55;
    /**
     * X & Y location in relation to the frame of the Rank text.
     */
    private static final double RANK_TEXT_X = 0.04, RANK_TEXT_Y = 0.85;
    /**
     * X & Y location in relation to the frame of the Rank text.
     */
    private static final double SCORE_TEXT_X = 0.2, SCORE_TEXT_Y = 0.13;
    /**
     * Maximum font size experience text.
     */
    private static final int MAX_EXP_FONT_SIZE_DIFFERENCE = 20;
    /**
     * The initial font size of the exp text.
     */
    private static final int INITIAL_EXP_FONTSIZE = 50;
    /**
     * Devides the score by this number.
     */
    private static final int SCORE_COUNT_TIME_CONSTANT = 100;
    /**
     * The speed, and side, the exp is rotating to.
     */
    private int expFontSizeSpeed = 1;
    /**
     * The font size of exp text.
     */
    private int expFontSize = 50;

    /**
     * The exp counted up to the actual experience count.
     */
    private int expCount = 0;
    /**
     * Amount extra when counting.
     */
    private double countUpAmount = 0;
    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The score reached by the player.
     */
    private int score;
    /**
     * The total exp earned by the player.
     */
    private int totalExperience;
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
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ KillScreen(final IServiceLocator sL, final int score, final int experience) {
        assert sL != null;
        this.serviceLocator = sL;
        this.score = score;
        totalExperience = experience;
        countUpAmount = (double) totalExperience / (double) SCORE_COUNT_TIME_CONSTANT;
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
    public void start() {
        this.playAgainButton.register();
        this.mainMenuButton.register();
        this.logger.info("The kill screen scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
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

        renderer.drawSpriteHUD(this.background, new Point(0, 0));
        renderer.drawSpriteHUD(this.gameOverSprite,
                new Point((int) (constants.getGameWidth() * KillScreen.GAME_OVER_TEXT_X),
                        (int) (constants.getGameHeight() * KillScreen.GAME_OVER_TEXT_Y)));

        double y = (double) constants.getGameHeight() - (double) this.bottomKillScreen.getHeight();
        renderer.drawSpriteHUD(this.bottomKillScreen, new Point(0, (int) y));
        this.playAgainButton.render();
        this.mainMenuButton.render();

        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        Ranks rank = progressionManager.getRank();
        renderer.drawTextNoAjustments(new Point(
                        (int) (constants.getGameWidth() * KillScreen.SCORE_TEXT_X),
                        (int) (constants.getGameHeight() * KillScreen.SCORE_TEXT_Y)),
                "Score: " + score, TextAlignment.left, Color.black);
        renderer.drawTextNoAjustments(new Point(
                        (int) (constants.getGameWidth() * KillScreen.RANK_TEXT_X),
                        (int) (constants.getGameHeight() * KillScreen.RANK_TEXT_Y)),
                "Rank: " + rank.getName(), TextAlignment.left, Color.black);
        renderer.drawTextExtraOptions(new Point(
                        (int) (constants.getGameWidth() * KillScreen.EXP_TEXT_X),
                        (int) (constants.getGameHeight() * KillScreen.EXP_TEXT_Y)),
                "+" + expCount + " exp", Color.darkBlue, 0, expFontSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        if (expCount < totalExperience) {
            expCount += countUpAmount;
        }
        updateExpFontSize();
    }

    /**
     * Updates the font size of the Experience text.
     */
    private void updateExpFontSize() {
        expFontSize += expFontSizeSpeed;
        if (expFontSize > INITIAL_EXP_FONTSIZE + MAX_EXP_FONT_SIZE_DIFFERENCE || expFontSize < INITIAL_EXP_FONTSIZE - MAX_EXP_FONT_SIZE_DIFFERENCE) {
            expFontSizeSpeed = -expFontSizeSpeed;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void switchDisplay(PauseScreenModes mode) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateButton(final Powerups powerup, final double x, final double y) {
    }

}
