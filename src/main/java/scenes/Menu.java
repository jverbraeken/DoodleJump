package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IKeyInputObserver;
import input.Keys;
import logging.ILogger;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a scene that is displays when the game is started.
 */
public class Menu implements IScene, IKeyInputObserver {

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
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ Menu(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        cover = spriteFactory.getStartCoverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        buttons.add(buttonFactory.createPlayButton(
                (int) (sL.getConstants().getGameWidth() * PLAY_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * PLAY_BUTTON_Y)));
        buttons.add(buttonFactory.createScoreButton(
                (int) (sL.getConstants().getGameWidth() * SCORE_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * SCORE_BUTTON_Y)));
        buttons.add(buttonFactory.createMultiplayerButton(
                (int) (sL.getConstants().getGameWidth() * MULTIPLAYER_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * MULTIPLAYER_BUTTON_Y)));
        buttons.add(buttonFactory.createChooseModeButton(
                (int) (sL.getConstants().getGameWidth() * CHOOSE_MODE_X),
                (int) (sL.getConstants().getGameHeight() * CHOOSE_MODE_Y)));

        IDoodleFactory doodleFactory = sL.getDoodleFactory();
        this.doodle = doodleFactory.createStartScreenDoodle();
        this.doodle.setXPos((int) (sL.getConstants().getGameWidth() * DOODLE_X));
        this.doodle.setVerticalSpeed(-1);

        IPlatformFactory platformFactory = sL.getPlatformFactory();
        platform = platformFactory.createPlatform(
                (int) (sL.getConstants().getGameWidth() * PLATFORM_X),
                (int) (sL.getConstants().getGameHeight() * PLATFORM_Y)
        );

        this.logger = sL.getLoggerFactory().createLogger(this.getClass());
    }

    /** {@inheritDoc} */
    @Override
    public final void start() {
        for (IButton button : buttons) {
            button.register();
        }
        serviceLocator.getInputManager().addObserver(this);
        logger.info("The main menu registered itself as an observer of the input manager");
        logger.info("The menu scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public final void stop() {
        for (IButton button : buttons) {
            button.deregister();
        }
        serviceLocator.getInputManager().removeObserver(this);
        logger.info("The main menu removed itself as an observer from the input manager");
        logger.info("The menu scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public final void render() {
        serviceLocator.getRenderer().drawSpriteHUD(this.cover, 0, 0);
        for (IButton button : buttons) {
            button.render();
        }
        doodle.render();
        platform.render();
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
        doodle.update(delta);

        double doodleY = doodle.getYPos() + this.doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] * doodle.getLegsHeight();
        if (doodle.checkCollision(platform) && doodleY < platform.getYPos()) {
            platform.collidesWith(this.doodle);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void keyPress(final Keys key) { }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final Keys key) {
        if (key == Keys.enter || key == Keys.space) {
            Game.setScene(serviceLocator.getSceneFactory().createWorld());
        }
    }

}
