package buttons;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * Standard implementation of the ButtonFactory. Used to create buttons.
 */
public final class ButtonFactory implements IButtonFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

    /**
     * Register the platform factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        ButtonFactory.sL = sL;
        ButtonFactory.sL.provide(new ButtonFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        Runnable playAction = () -> Game.setScene(sL.getSceneFactory().newWorld());
        return new Button(sL, x, y, buttonSprite, playAction, "play");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createResumeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getResumeButtonSprite();
        Runnable resumeAction = () -> Game.setPaused(false);
        return new Button(sL, x, y, buttonSprite, resumeAction, "resume");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayAgainButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayAgainButtonSprite();
        Runnable playAgainAction = () -> Game.setScene(sL.getSceneFactory().newWorld());
        return new Button(sL, x, y, buttonSprite, playAgainAction, "playAgain");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMainMenuButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMenuButtonSprite();
        Runnable mainMenu = () -> Game.setScene(sL.getSceneFactory().createMainMenu());
        return new Button(sL, x, y, buttonSprite, mainMenu, "mainMenu");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createScoreButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getScoreButtonSprite();
        Runnable scoreScreen = () -> Game.setScene(sL.getSceneFactory().createScoreScreen());
        return new Button(sL, x, y, buttonSprite, scoreScreen, "scores");
    }

}
