package scenes;

import input.IMouseInputObserver;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
public class KillScreen implements IScene, IMouseInputObserver {

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The button that starts a new world.
     */
    private final IButton playAgainButton;
    /**
     * The button that sends you back to the main menu.
     */
    private final IButton mainMenuButton;
    /**
     * Sprites to be displayed on the background of the killscreen.
     */
    private final ISprite background, bottomKillScreen, gameOverSprite;
    /**
     * X location in relation to the frame of the play again button.
     */
    private final double playAgainButtonXPercentage = 0.3;
    /**
     * Y location in relation to the frame of the play again button.
     */
    private final double playAgainButtonYPercentage = 0.6;
    /**
     * X location in relation to the frame of the main menu button.
     */
    private final double mainMenuButtonXPercentage = 0.6;
    /**
     * Y location in relation to the frame of the main menu button.
     */
    private final double mainMenuButtonYPercentage = 0.7;
    /**
     * X location in relation to the frame of the game over text.
     */
    private final double gameOverTextXPercentage = 0.1;
    /**
     * Y location in relation to the frame of the game over text.
     */
    private final double gameOverTextYPercentage = 0.3;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ KillScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;

       background = serviceLocator.getSpriteFactory().getBackground();
        bottomKillScreen = serviceLocator.getSpriteFactory().getKillScreenBottomSprite();
        gameOverSprite = serviceLocator.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton((int) (serviceLocator.getConstants().getGameWidth() * playAgainButtonXPercentage), (int) (serviceLocator.getConstants().getGameHeight() * playAgainButtonYPercentage));
        mainMenuButton = buttonFactory.createMainMenuButton((int) (serviceLocator.getConstants().getGameWidth() * mainMenuButtonXPercentage), (int) (serviceLocator.getConstants().getGameHeight() * mainMenuButtonYPercentage));

    }

    @Override
    /** {@inheritDoc} */
    public final void start() {

        serviceLocator.getInputManager().addObserver(playAgainButton);
        serviceLocator.getInputManager().addObserver(mainMenuButton);
    }

    @Override
    /** {@inheritDoc} */
    public final void stop() {

        serviceLocator.getInputManager().removeObserver(playAgainButton);
        serviceLocator.getInputManager().removeObserver(mainMenuButton);
    }

    @Override
    public void render() {
        serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0 );
        serviceLocator.getRenderer().drawSpriteHUD(this.gameOverSprite, (int)(serviceLocator.getConstants().getGameWidth() * gameOverTextXPercentage), (int)(serviceLocator.getConstants().getGameHeight() *gameOverTextYPercentage));
        double y = (double) serviceLocator.getConstants().getGameHeight() - (double) bottomKillScreen.getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(this.bottomKillScreen, 0, (int) y);
        playAgainButton.render();
        mainMenuButton.render();
    }

    @Override
    public void update(final double delta) {
    }

    @Override
    /** {@inheritDoc} */
    public void mouseClicked(final int x, final int y) {
    }
}
