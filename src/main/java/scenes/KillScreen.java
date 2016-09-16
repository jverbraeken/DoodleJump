package scenes;

import input.IMouseInputObserver;
import objects.backgrounds.IBackgroundFactory;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import rendering.IDrawable;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

public class KillScreen implements IScene, IMouseInputObserver {

    private final IServiceLocator serviceLocator;

    private final IButton playAgainButton;
    private final IButton mainMenuButton;
    private final IDrawable background;
    private final double playAgainButtonXPercentage = 0.4;
    private final double playAgainButtonYPercentage = 0.7;
    private final double mainMenuButtonXPercentage = 0.4;
    private final double mainMenuButtonYPercentage = 0.8;
    private final double gameOverTextXPercentage = 0.1;
    private final double gameOverTextYPercentage = 0.3;

    //TODO: add game over text;
    /* package */ KillScreen(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBackgroundFactory backgroundFactory = serviceLocator.getBackgroundFactory();
        background = backgroundFactory.createBackground();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton((int) (Game.WIDTH * playAgainButtonXPercentage), (int) (Game.HEIGHT * playAgainButtonYPercentage));
        mainMenuButton = buttonFactory.createMainMenuButton((int) (Game.WIDTH * mainMenuButtonXPercentage), (int) (Game.HEIGHT * mainMenuButtonYPercentage));

    }

    @Override
    /** {@inheritDoc} */
    public void start() {

        serviceLocator.getInputManager().addObserver(playAgainButton);
        serviceLocator.getInputManager().addObserver(mainMenuButton);
    }

    @Override
    /** {@inheritDoc} */
    public void stop() {

        serviceLocator.getInputManager().removeObserver(playAgainButton);
        serviceLocator.getInputManager().removeObserver(mainMenuButton);
    }

    @Override
    public void paint() {
        background.render();
        playAgainButton.render();
        mainMenuButton.render();
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
