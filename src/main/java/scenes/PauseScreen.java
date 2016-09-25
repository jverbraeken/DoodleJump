package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import logging.ILogger;
import resources.sprites.ISprite;
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
    private final IServiceLocator sL;
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
     *
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(IServiceLocator sL) {
        this.sL = sL;
        LOGGER = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Background
        background = this.sL.getSpriteFactory().getPauseCoverSprite();

        // Resume button
        IButtonFactory buttonFactory = this.sL.getButtonFactory();
        int resumeButtonX = (int) (sL.getConstants().getGameWidth() * RESUME_BUTTON_X);
        int resumeButtonY = (int) (sL.getConstants().getGameHeight() * RESUME_BUTTON_Y);
        resumeButton = buttonFactory.createResumeButton(resumeButtonX, resumeButtonY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        sL.getInputManager().addObserver(resumeButton);
        this.active = true;
        LOGGER.info("The pause scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        sL.getInputManager().removeObserver(resumeButton);
        this.active = false;
        LOGGER.info("The pause scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (active) {
            sL.getRenderer().drawSprite(background, 0, 0);
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