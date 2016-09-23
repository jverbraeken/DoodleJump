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
    private final IServiceLocator sL;
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
        this.sL = sL;

       background = sL.getSpriteFactory().getBackground();
        bottomKillScreen = sL.getSpriteFactory().getKillScreenBottomSprite();
        gameOverSprite = sL.getSpriteFactory().getGameOverSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        playAgainButton = buttonFactory.createPlayAgainButton((int) (sL.getConstants().getGameWidth() * playAgainButtonXPercentage), (int) (sL.getConstants().getGameHeight() * playAgainButtonYPercentage));
        mainMenuButton = buttonFactory.createMainMenuButton((int) (sL.getConstants().getGameWidth() * mainMenuButtonXPercentage), (int) (sL.getConstants().getGameHeight() * mainMenuButtonYPercentage));

    }

    @Override
    /** {@inheritDoc} */
    public final void start() {

        sL.getInputManager().addObserver(playAgainButton);
        sL.getInputManager().addObserver(mainMenuButton);
    }

    @Override
    /** {@inheritDoc} */
    public final void stop() {

        sL.getInputManager().removeObserver(playAgainButton);
        sL.getInputManager().removeObserver(mainMenuButton);
    }

    @Override
    public void render() {
        sL.getRenderer().drawSpriteHUD(this.background, 0, 0 );
        sL.getRenderer().drawSpriteHUD(this.gameOverSprite, (int)(sL.getConstants().getGameWidth() * gameOverTextXPercentage), (int)(sL.getConstants().getGameHeight() *gameOverTextYPercentage));
        double y = (double) sL.getConstants().getGameHeight() - (double) bottomKillScreen.getHeight();
        sL.getRenderer().drawSpriteHUD(this.bottomKillScreen, 0, (int) y);
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
