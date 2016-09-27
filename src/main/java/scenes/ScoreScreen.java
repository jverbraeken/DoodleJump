package scenes;

import input.IMouseInputObserver;
import logging.ILogger;
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

    /* package */ ScoreScreen(IServiceLocator sL) {
        this.serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(ScoreScreen.class);
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
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
