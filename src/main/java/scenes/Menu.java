package scenes;

import input.IKeyInputObserver;
import input.KeyCode;
import input.Keys;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;
/**
 * This class is a scene that is displays when the game is started.
 */
/* package */ class Menu implements IScene, IKeyInputObserver {
    /**
     * X position relative to the frame of the play button.
     */
    private static final double PLAY_BUTTON_X_PERCENTAGE = 0.15;
    /**
     * Y position relative to the frame of the play button.
     */
    private static final double PLAY_BUTTON_Y_PERCENTAGE = 0.25;
    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The button that starts up a new world.
     */
    private final IButton playButton;
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
        this.serviceLocator = sL;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        cover = spriteFactory.getStartCoverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playButton = buttonFactory.createPlayButton((int) (Game.WIDTH * PLAY_BUTTON_X_PERCENTAGE), (int) (Game.HEIGHT * PLAY_BUTTON_Y_PERCENTAGE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        serviceLocator.getInputManager().addObserver(playButton);
        serviceLocator.getInputManager().addObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
        serviceLocator.getInputManager().removeObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void paint() {
        serviceLocator.getRenderer().drawSprite(this.cover, 0, 0);
        playButton.render();
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
            Game.setScene(serviceLocator.getSceneFactory().newWorld());
        }
    }

}
