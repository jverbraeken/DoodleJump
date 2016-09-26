package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IKeyInputObserver;
import input.KeyCode;
import input.Keys;
import logging.ILogger;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * This class is a scene that is displays when the game is started.
 */
public class Menu implements IScene, IKeyInputObserver {

    /**
     * The logger for the Menu class.
     */
    private final ILogger LOGGER;
    /**
     * The X and Y location for the play button.
     */
    private static final double PLAY_BUTTON_X = 0.15d, PLAY_BUTTON_Y = 0.25d;


    /**
     * The X and Y location for the choose mode button.
     */
    private static final double CHOOSE_MODE_X = 0.6d, CHOOSE_MODE_Y= 0.65d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator sL;
    /**
     * The button that starts up a new world.
     */
    private final IButton playButton;
    /**
     * The button that starts up a scene to choose mode.
     */
    private final IButton chooseModeButton;
    /**
     * The cover sprite of the main menu.
     */
    private final ISprite cover;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ Menu(final IServiceLocator sL) {
        assert sL != null;
        this.sL = sL;

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        cover = spriteFactory.getStartCoverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        playButton = buttonFactory.createPlayButton(
                (int) (sL.getConstants().getGameWidth() * PLAY_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * PLAY_BUTTON_Y));
        chooseModeButton = buttonFactory.createChooseModeButton(
                (int) (sL.getConstants().getGameWidth() * CHOOSE_MODE_X),
                (int) (sL.getConstants().getGameHeight() * CHOOSE_MODE_Y));

        this.LOGGER = sL.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        sL.getInputManager().addObserver(playButton);
        sL.getInputManager().addObserver(chooseModeButton);
        sL.getInputManager().addObserver(this);
        LOGGER.info("The menu scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        sL.getInputManager().removeObserver(playButton);
        sL.getInputManager().removeObserver(chooseModeButton);
        sL.getInputManager().removeObserver(this);
        LOGGER.info("The menu scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        sL.getRenderer().drawSpriteHUD(this.cover, 0, 0);
        playButton.render();
        chooseModeButton.render();
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
    public void keyPress(final int keyCode) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final int keyCode) {
        if (KeyCode.getKeyCode(Keys.enter) == keyCode || KeyCode.getKeyCode(Keys.space) == keyCode) {
            Game.setScene(sL.getSceneFactory().newWorld());
        }
    }

}