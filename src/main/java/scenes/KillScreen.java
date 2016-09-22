package scenes;

import input.IMouseInputObserver;
import buttons.IButton;
import buttons.IButtonFactory;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/* package */ class KillScreen implements IScene, IMouseInputObserver {

    private final IServiceLocator serviceLocator;

    private final IButton playAgainButton;
    private final IButton mainMenuButton;
    private final ISprite background, bottomKillScreen, gameOverSprite;
    private final double playAgainButtonXPercentage = 0.3;
    private final double playAgainButtonYPercentage = 0.6;
    private final double mainMenuButtonXPercentage = 0.6;
    private final double mainMenuButtonYPercentage = 0.7;
    private final double gameOverTextXPercentage = 0.1;
    private final double gameOverTextYPercentage = 0.3;

    /* package */ KillScreen(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        background = serviceLocator.getSpriteFactory().getBackground();
        bottomKillScreen = serviceLocator.getSpriteFactory().getKillScreenBottomSprite();
        gameOverSprite = serviceLocator.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton((int) (Game.WIDTH * playAgainButtonXPercentage), (int) (Game.HEIGHT * playAgainButtonYPercentage));
        mainMenuButton = buttonFactory.createMainMenuButton((int) (Game.WIDTH * mainMenuButtonXPercentage), (int) (Game.HEIGHT * mainMenuButtonYPercentage));

    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(playAgainButton);
        serviceLocator.getInputManager().addObserver(mainMenuButton);
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playAgainButton);
        serviceLocator.getInputManager().removeObserver(mainMenuButton);
    }

    @Override
    public void paint() {
        serviceLocator.getRenderer().drawSprite(this.background, 0, 0 );
        serviceLocator.getRenderer().drawSprite(this.gameOverSprite, (int)(Game.WIDTH * gameOverTextXPercentage), (int)(Game.HEIGHT *gameOverTextYPercentage));
        double y = (double) Game.HEIGHT - (double) bottomKillScreen.getHeight();
        serviceLocator.getRenderer().drawSprite(this.bottomKillScreen, 0, (int) y);
        playAgainButton.render();
        mainMenuButton.render();
    }

    @Override
    public void update(double delta) {
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(int x, int y) {
    }
}
