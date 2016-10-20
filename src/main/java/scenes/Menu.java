package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a scene that is displays when the game is started.
 */
public class Menu implements IScene {

    /**
     * The X and Y location for the play button.
     */
    private static final double PLAY_BUTTON_X = 0.15d, PLAY_BUTTON_Y = 0.25d;
    /**
     * The X and Y location for the play button.
     */
    private static final double SCORE_BUTTON_X = 0.28d, SCORE_BUTTON_Y = 0.36d;
    /**
     * The X and Y location for the play button.
     */
    private static final double MULTIPLAYER_BUTTON_X = 0.41d, MULTIPLAYER_BUTTON_Y = 0.47d;
    /**
     * The X and Y location for the choose mode button.
     */
    private static final double CHOOSE_MODE_X = 0.6d, CHOOSE_MODE_Y = 0.65d;
    /**
     * The X and Y location for the StartScreen platform.
     */
    private static final double PLATFORM_X = 0.1d, PLATFORM_Y = 0.78d;
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
    private final List<IButton> buttons = new ArrayList<>(4);
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
        int gameWidth = constants.getGameWidth();
        int gameHeight = constants.getGameHeight();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        this.buttons.add(buttonFactory.createPlayButton(
                (int) (gameWidth * Menu.PLAY_BUTTON_X),
                (int) (gameHeight * Menu.PLAY_BUTTON_Y)));
        this.buttons.add(buttonFactory.createScoreButton(
                (int) (gameWidth * Menu.SCORE_BUTTON_X),
                (int) (gameHeight * Menu.SCORE_BUTTON_Y)));
        this.buttons.add(buttonFactory.createMultiplayerButton(
                (int) (gameWidth * Menu.MULTIPLAYER_BUTTON_X),
                (int) (gameHeight * Menu.MULTIPLAYER_BUTTON_Y)));
        this.buttons.add(buttonFactory.createChooseModeButton(
                (int) (gameWidth * Menu.CHOOSE_MODE_X),
                (int) (gameHeight * Menu.CHOOSE_MODE_Y)));

        IDoodleFactory doodleFactory = sL.getDoodleFactory();
        this.doodle = doodleFactory.createStartScreenDoodle();
        this.doodle.setXPos((int) (gameWidth * Menu.DOODLE_X));
        this.doodle.setVerticalSpeed(-1);

        IPlatformFactory platformFactory = sL.getPlatformFactory();
        this.platform = platformFactory.createPlatform(
                (int) (gameWidth * Menu.PLATFORM_X),
                (int) (gameHeight * Menu.PLATFORM_Y) );

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
        this.serviceLocator.getRenderer().drawSpriteHUD(this.cover, 0, 0);
        for (IButton button : this.buttons) {
            button.render();
        }
        this.doodle.render();
        this.platform.render();
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
