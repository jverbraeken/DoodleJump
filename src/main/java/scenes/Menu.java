package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.Color;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a scene that is displays when the game is started.
 */
public class Menu implements IScene {

    /**
     * The X-position at which the first button will be created.
     */
    private static final double BUTTON_X_START = 0.15d;
    /**
     * The Y-position at which the first button will be created.
     */
    private static final double BUTTON_Y_START = 0.25d;
    /**
     * The X-distance between the buttons (buttons themselves including).
     */
    private static final double BUTTON_X_OFFSET = 0.1d;
    /**
     * The Y-distance between the buttons (buttons themselves including).
     */
    private static final double BUTTON_Y_OFFSET = 0.11d;
    /**
     * The X and Y location for the play button.
     */
    private static final double PLAY_BUTTON_X = BUTTON_X_START + BUTTON_X_OFFSET * 0, PLAY_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;
    /**
     * The X and Y location for the play button.
     */
    private static final double SCORE_BUTTON_X = BUTTON_X_START + BUTTON_X_OFFSET * 1, SCORE_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;
    /**
     * The X and Y location for the play button.
     */
    private static final double SHOP_BUTTON_X = BUTTON_X_START + BUTTON_X_OFFSET * 2, SHOP_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;
    /**
     * The X and Y location for the play button.
     */
    private static final double MULTIPLAYER_BUTTON_X = BUTTON_X_START + BUTTON_X_OFFSET * 3, MULTIPLAYER_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 3;
    /**
     * The X and Y location for the choose mode button.
     */
    private static final double CHOOSE_MODE_X = BUTTON_X_START + BUTTON_X_OFFSET * 4, CHOOSE_MODE_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 4;
    /**
     * The X and Y location for the StartScreen platform.
     */
    private static final double PLATFORM_X = 0.1d, PLATFORM_Y = 0.78d;

    /**
     * The height of the rectangle on the top and the Y location of the rank text.
     */
    private static final int TOP_RECTANGLE_HEIGHT = 65, RANK_TEXT_Y = 50;
    /**
     * The X location for the StartScreen Doodle.
     */
    private static final double DOODLE_X = 0.1d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the Menu class.
     */
    private final ILogger logger;
    /**
     * The buttons for the main menu.
     */
    private final List<IButton> buttons = new ArrayList<>(5);
    /**
     * The Doodle for the menu.
     */
    private final IDoodle doodle;
    /**
     * The platform for the menu.
     */
    private final IPlatform platform;
    /**
     * The cover sprite of the main menu.
     */
    private ISprite cover;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ Menu(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.cover = spriteFactory.getStartCoverSprite();

        IConstants constants = sL.getConstants();
        final int gameWidth = constants.getGameWidth();
        final int gameHeight = constants.getGameHeight();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        this.buttons.add(buttonFactory.createPlayButton(
                Menu.PLAY_BUTTON_X,
                Menu.PLAY_BUTTON_Y));
        this.buttons.add(buttonFactory.createScoreButton(
                Menu.SCORE_BUTTON_X,
                Menu.SCORE_BUTTON_Y));
        this.buttons.add(buttonFactory.createMultiplayerButton(
                Menu.MULTIPLAYER_BUTTON_X,
                Menu.MULTIPLAYER_BUTTON_Y));
        this.buttons.add(buttonFactory.createShopButton(
                SHOP_BUTTON_X,
                SHOP_BUTTON_Y));
        this.buttons.add(buttonFactory.createChooseModeButton(
                Menu.CHOOSE_MODE_X,
                Menu.CHOOSE_MODE_Y));

        IDoodleFactory doodleFactory = sL.getDoodleFactory();
        this.doodle = doodleFactory.createStartScreenDoodle();
        this.doodle.setXPos(gameWidth * Menu.DOODLE_X);
        this.doodle.setVerticalSpeed(-1);

        IPlatformFactory platformFactory = sL.getPlatformFactory();
        this.platform = platformFactory.createPlatform(
                (int) (gameWidth * Menu.PLATFORM_X),
                (int) (gameHeight * Menu.PLATFORM_Y));

        this.logger = sL.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        for (IButton button : this.buttons) {
            button.register();
        }
        this.logger.info("The main menu registered itself as an observer of the input manager");
        this.logger.info("The menu scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        for (IButton button : this.buttons) {
            button.deregister();
        }
        this.logger.info("The main menu removed itself as an observer from the input manager");
        this.logger.info("The menu scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        Ranks rank = progressionManager.getRank();
        IConstants constants = this.serviceLocator.getConstants();
        IRenderer renderer = this.serviceLocator.getRenderer();


        this.serviceLocator.getRenderer().drawSpriteHUD(this.cover, new Point(0, 0));
        for (IButton button : this.buttons) {
            button.render();
        }
        this.doodle.render();
        this.platform.render();

        renderer.fillRectangle(new Point(0, 0), constants.getGameWidth(), TOP_RECTANGLE_HEIGHT, Color.halfOpaqueWhite);
        renderer.drawText(new Point(0, RANK_TEXT_Y), "Rank: " + rank.getName(), Color.black);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        this.doodle.update(delta);

        if (this.doodle.checkCollision(this.platform)) {
            this.platform.collidesWith(this.doodle);
        }
    }

}
