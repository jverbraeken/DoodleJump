package scenes;

import input.IMouseInputObserver;
import objects.backgrounds.IBackgroundFactory;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import rendering.IDrawable;
import system.Game;
import system.IServiceLocator;

public class Menu implements IScene {

    private final IServiceLocator serviceLocator;

    private final IButton playButton;
    private final IDrawable background;
    private static final double playButtonXPercentage = 0.1;
    private static final double playButtonYPercentage = 0.3;

    /* package */ Menu(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBackgroundFactory backgroundFactory = serviceLocator.getBackgroundFactory();
        background = backgroundFactory.createBackground();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playButton = buttonFactory.createPlayButton((int) (Game.WIDTH * playButtonXPercentage), (int) (Game.HEIGHT * playButtonYPercentage));
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(playButton);
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
    }

    /** {@inheritDoc} */
    @Override
    public void paint() {
        background.render();
        playButton.render();
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) { }

}
