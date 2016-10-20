package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * PauseScreen implementation of a scene.
 */
/* package */ class PauseScreen implements IScene {

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
     * The background sprite.
     */
    private ISprite background;

    /**
     * Initialize the pause screen.
     *
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(final IServiceLocator sL) {
        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Background
        this.background = this.serviceLocator.getSpriteFactory().getPauseCoverSprite();

        // Resume button
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        int resumeButtonX = (int) (sL.getConstants().getGameWidth() * PauseScreen.RESUME_BUTTON_X);
        int resumeButtonY = (int) (sL.getConstants().getGameHeight() * PauseScreen.RESUME_BUTTON_Y);
        this.resumeButton = buttonFactory.createResumeButton(resumeButtonX, resumeButtonY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.serviceLocator.getInputManager().addObserver(this.resumeButton);
        this.logger.info("The pause scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.serviceLocator.getInputManager().removeObserver(this.resumeButton);
        this.logger.info("The pause scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);
        this.resumeButton.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
    }

}
