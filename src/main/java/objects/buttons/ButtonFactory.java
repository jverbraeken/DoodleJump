package objects.buttons;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class ButtonFactory implements IButtonFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        ButtonFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new ButtonFactory());
    }

    /** {@inheritDoc} */
    @Override
    public IButton createPlayButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        Runnable playAction = () -> Game.setScene(serviceLocator.getSceneFactory().newWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAction, "play");
    }

    /** {@inheritDoc} */
    @Override
    public IButton createResumeButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getResumeButtonSprite();
        Runnable resumeAction = () -> Game.setPaused(false);
        return new Button(serviceLocator, x, y, buttonSprite, resumeAction, "resume");
    }

    /** {@inheritDoc} */
    //TODO: correct sprite to "play again" button
    @Override
    public IButton createPlayAgainButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayAgainButtonSprite();
        Runnable playAgainAction = () -> Game.setScene(serviceLocator.getSceneFactory().newWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAgainAction, "playAgain");
    }

    /** {@inheritDoc} */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createMainMenuButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMenuButtonSprite();
        Runnable mainMenu = () -> Game.setScene(serviceLocator.getSceneFactory().newMenu());
        return new Button(serviceLocator, x, y, buttonSprite, mainMenu, "mainMenu");
    }
}
