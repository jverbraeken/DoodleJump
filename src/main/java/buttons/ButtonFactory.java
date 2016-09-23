package buttons;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

/**
 * Standard implementation of the ButtonFactory. Used to create buttons.
 */
public class ButtonFactory implements IButtonFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        ButtonFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new ButtonFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMainMenuButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMenuButtonSprite();
        Runnable mainMenu = () -> Game.setScene(serviceLocator.getSceneFactory().createMainMenu());
        return new Button(serviceLocator, x, y, buttonSprite, mainMenu, "mainMenu");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayAgainButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayAgainButtonSprite();
        Runnable playAgainAction = () -> Game.setScene(serviceLocator.getSceneFactory().newWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAgainAction, "playAgain");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        Runnable playAction = () -> Game.setScene(serviceLocator.getSceneFactory().newWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAction, "play");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createResumeButton(int x, int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getResumeButtonSprite();
        Runnable resumeAction = () -> Game.setPaused(false);
        return new Button(serviceLocator, x, y, buttonSprite, resumeAction, "resume");
    }

}
