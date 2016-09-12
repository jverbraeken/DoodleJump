package scenes;

import input.IMouseInputObserver;
import objects.backgrounds.IBackgroundFactory;
import objects.buttons.IButtonFactory;
import objects.buttons.PlayButton;
import rendering.IDrawable;
import system.Game;
import system.IServiceLocator;

public class Menu implements IScene, IMouseInputObserver {

    private final IServiceLocator serviceLocator;

    private final PlayButton playButton;
    private final IDrawable background;
    private final double playButtonXPercentage = 0.1;
    private final double playButtonYPercentage = 0.3;

    /* package */ Menu(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBackgroundFactory backgroundFactory = serviceLocator.getBackgroundFactory();
        background = backgroundFactory.createBackground();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playButton = buttonFactory.createPlayButton((int) (Game.WIDTH * playButtonXPercentage), (int) (Game.HEIGHT * playButtonYPercentage));
    }

    @Override
    /** {@inheritDoc} */
    public void start() {
        serviceLocator.getInputManager().addObserver(playButton);
    }

    @Override
    /** {@inheritDoc} */
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
    }

    @Override
    public void paint() {
        background.paint();
        playButton.paint();
    }

    @Override
    public void update(double delta) {
    }

    @Override
    /** {@inheritDoc} */
    public void mouseClicked(int x, int y) {
        System.out.println("X: " + x + ", Y: " + y);
    }
}
