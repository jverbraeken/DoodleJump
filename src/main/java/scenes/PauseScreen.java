package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import logging.ILogger;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/**
 * PauseScreen implementation of a scene.
 */
/* package */ class PauseScreen implements IScene, IMouseInputObserver {

    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger LOGGER;
    /**
     * The X and Y location for the resume button.
     */
    private static final double RESUME_BUTTON_X = 0.55d, RESUME_BUTTON_Y = 0.75d;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The resume button.
     */
    private final IButton resumeButton;
    /**
     * The background sprite.
     */
    private final ISprite background;
    /**
     * Is the pause screen active, should it be displayed.
     */
    private boolean active = false;

    /**
     * Initialize the pause screen.
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(IServiceLocator sL) {
        serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Background
        background = serviceLocator.getSpriteFactory().getPauseCoverSprite();

        // Resume button
        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        int resumeButtonX = (int) (Game.WIDTH * RESUME_BUTTON_X);
        int resumeButtonY = (int) (Game.HEIGHT * RESUME_BUTTON_Y);
        resumeButton = buttonFactory.createResumeButton(resumeButtonX, resumeButtonY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(resumeButton);
        this.active = true;
        LOGGER.info("The pause scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(resumeButton);
        this.active = false;
        LOGGER.info("The pause scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        if(this.active) {
            serviceLocator.getRenderer().drawSprite(background, 0, 0);
            resumeButton.render();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(int x, int y) {
    }

}
