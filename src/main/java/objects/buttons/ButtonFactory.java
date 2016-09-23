package objects.buttons;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * This class is a factory that creates buttons.
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

    //TODO: correct sprite to "play again" button
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
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createMainMenuButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMenuButtonSprite();
        Runnable mainMenu = () -> Game.setScene(sL.getSceneFactory().newMenu());
        return new Button(sL, x, y, buttonSprite, mainMenu, "mainMenu");
    }
}
