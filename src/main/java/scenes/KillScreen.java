package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.Color;
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
     * X & Y location in relation to the frame of the experience text.
     */
    private static final double EXP_TEXT_X = 0.6, EXP_TEXT_Y = 0.55;
    /**
     * X & Y location in relation to the frame of the Rank text.
     */
    private static final double RANK_TEXT_X = 0.05, RANK_TEXT_Y = 0.05;
    /**
     * X & Y location in relation to the frame of the Rank text.
     */
    private static final double SCORE_TEXT_X = 0.1, SCORE_TEXT_Y = 0.3;
    /**
     * Maximum and initial rotation of the experience text. And the maximum
     * and initial font size of the experience text.
     */
    private static final int MAX_EXP_FONT_SIZE_DIFFERENCE = 20, INITIAL_EXP_ROTATION = 0, INITIAL_EXP_FONTSIZE = 50;
    /**
     * Devides the score by this number.
     */
    private static final int SCORE_COUNT_TIME_CONSTANT = 120;
    /**
     * Maximum rotation in radians of the Exp text.
     */
    private static final double MAX_EXP_ROTATION = 0.2;
    /**
     * The speed, and side, the exp is rotating to.
     */
    private int expFontSizeSpeed = 1;
    /**
     * The speed, and side, the exp is rotating to.
     */
    private double expRotationSpeed = 0.017;
    /**
     * The font size of exp text.
     */
    private int expFontSize = 50;
    /**
     * The rotation of the exp text.
     */
    private double expRotation = 0;

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
    /* package */ KillScreen(final IServiceLocator sL, final int score) {
        assert sL != null;
        this.serviceLocator = sL;
        this.score = score;
        countUpAmount = score/SCORE_COUNT_TIME_CONSTANT;
        System.out.println(countUpAmount);
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

        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        Ranks rank = progressionManager.getRank();
        renderer.drawText(
                (int) (constants.getGameWidth() * KillScreen.RANK_TEXT_X),
                (int) (constants.getGameHeight() * KillScreen.RANK_TEXT_Y),
                "Rank: " + rank.getName(), Color.black);
        renderer.drawTextExtraOptions(
                (int) (constants.getGameWidth() * KillScreen.EXP_TEXT_X),
                (int) (constants.getGameHeight() * KillScreen.EXP_TEXT_Y),
                "+" + expCount + " exp", Color.darkBlue, expRotation, expFontSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        if (expCount < score) {
            expCount += countUpAmount;
        }
        updateExpFontSize();
        //updateExpRotation();
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
     * Updates the rotation of the Experience text.
     */
    private void updateExpRotation() {
        expRotation += expRotationSpeed;
        if (expRotation > INITIAL_EXP_ROTATION + MAX_EXP_ROTATION || expRotation < INITIAL_EXP_ROTATION - MAX_EXP_ROTATION) {
            expRotationSpeed = -expRotationSpeed;
        }
    }

}
