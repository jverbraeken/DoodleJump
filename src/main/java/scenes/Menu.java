package scenes;

import input.IKeyInputObserver;
import input.KeyCode;
import input.Keys;
import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * Menu implementation of a scene. Used as main menu.
 */
/* package */ class Menu implements IScene, IKeyInputObserver {

    /**
     * The logger for the Menu class.
     */
    private final ILogger LOGGER;
    /**
     * The X and Y location for the play button.
     */
    private static final double PLAY_BUTTON_X = 0.15d, PLAY_BUTTON_Y = 0.25d;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The play button.
     */
    private final IButton playButton;
    /**
     * The background sprite.
     */
    private final ISprite background;
    /**
     * Is the main menu active, should it be displayed.
     */
    private boolean active = false;

    /* package */ Menu(IServiceLocator sL) {
        serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(Menu.class);

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        background = spriteFactory.getStartCoverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playButton = buttonFactory.createPlayButton((int) (Game.WIDTH * PLAY_BUTTON_X), (int) (Game.HEIGHT * PLAY_BUTTON_Y));
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(playButton);
        active = true;
        LOGGER.info("The menu scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
        active = false;
        LOGGER.info("The menu scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void paint() {
        if (active) {
            serviceLocator.getRenderer().drawSprite(this.background, 0, 0);
            playButton.render();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) { }

    /** {@inheritDoc} */
    @Override
    public void keyPress(int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public void keyRelease(int keyCode) {
        if (KeyCode.getKeyCode(Keys.enter) == keyCode || KeyCode.getKeyCode(Keys.space) == keyCode) {
            Game.setScene(serviceLocator.getSceneFactory().newWorld());
        }
    }

}
