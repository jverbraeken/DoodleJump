package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import input.IMouseInputObserver;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/* package */ class PauseScreen implements IScene, IMouseInputObserver {

    /**
     * The service locator for the pause screen.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The X and Y location for the resume button.
     */
    private final double RESUME_BUTTON_X = 0.55, RESUME_BUTTON_Y = 0.75;
    /**
     * The resume button.
     */
    private final IButton resumeButton;
    /**
     * The background sprite.
     */
    private final ISprite background;

    /**
     * Initialize the pause screen.
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(IServiceLocator sL) {
        serviceLocator = sL;

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(resumeButton);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        double scaling = (double) Game.WIDTH / (double) background.getWidth();
        int backgroundWidth = (int) (background.getWidth() * scaling);
        int backgroundHeight = (int) (background.getHeight() * scaling);
        serviceLocator.getRenderer().drawSprite(background, 0, 0, backgroundWidth, backgroundHeight);
        resumeButton.render();
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
