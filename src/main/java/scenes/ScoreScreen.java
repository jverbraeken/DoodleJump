package scenes;

import input.IMouseInputObserver;
import logging.ILogger;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * Score screen implementation of a scene.
 */
/* package */ class ScoreScreen implements IScene, IMouseInputObserver {

    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The cover sprite of the main menu.
     */
    private final ISprite bottom, left, top;

    /* package */ ScoreScreen(IServiceLocator sL) {
        this.serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(ScoreScreen.class);

        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        this.bottom = spriteFactory.getScoreScreenBottom();
        this.left = spriteFactory.getScoreScreenLeft();
        this.top = spriteFactory.getScoreScreenTop();
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        IRenderer renderer = this.serviceLocator.getRenderer();
        renderer.drawSpriteHUD(this.bottom, 0, this.top.getHeight() + this.left.getHeight());
        renderer.drawSpriteHUD(this.left, 0, this.top.getHeight());
        renderer.drawSpriteHUD(this.top, 0, 0);
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        LOGGER.info("The score scene is now displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        LOGGER.info("The score scene is no longer displaying");
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) {
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(int x, int y) {
    }

}
