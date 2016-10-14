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
    private static transient IServiceLocator serviceLocator;

    /**
     * The singleton button factory.
     * Constructed using synchronization.
     */
    private static IButtonFactory buttonFactory;

    /**
     * Register the platform factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        ButtonFactory.serviceLocator = sL;
        ButtonFactory.serviceLocator.provide(getButtonFactory());
    }

    /**
     * The synchronized getter of the singleton buttonfactory.
     * @return the button factory
     */
    public static synchronized IButtonFactory getButtonFactory() {
        if (buttonFactory == null) {
           buttonFactory = new ButtonFactory();
        }
        return buttonFactory;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        Runnable playAction = () -> Game.setScene(serviceLocator.getSceneFactory().createSinglePlayerWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAction, "play");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMultiplayerButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMultiplayerButtonSprite();
        Runnable playAction = () -> Game.setScene(serviceLocator.getSceneFactory().createTwoPlayerWorld());
        return new Button(serviceLocator, x, y, buttonSprite, playAction, "multiplayer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createResumeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getResumeButtonSprite();
        Runnable resumeAction = () -> Game.setPaused(false);
        return new Button(serviceLocator, x, y, buttonSprite, resumeAction, "resume");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayAgainButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayAgainButtonSprite();
        Runnable playAgainAction = () -> {
            if (Game.getPlayerMode() == Game.PlayerModes.single) {
                Game.setScene(serviceLocator.getSceneFactory().createSinglePlayerWorld());
            } else {
                Game.setScene(serviceLocator.getSceneFactory().createTwoPlayerWorld());
            }
        };
        return new Button(serviceLocator, x, y, buttonSprite, playAgainAction, "playAgain");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMainMenuButton(final int x, final int y) {
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
    public IButton createScoreButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getScoreButtonSprite();
        Runnable scoreScreen = () -> Game.setScene(serviceLocator.getSceneFactory().createScoreScreen());
        return new Button(serviceLocator, x, y, buttonSprite, scoreScreen, "scores");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createChooseModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getChooseModeButtonSprite();
        Runnable chooseMode = () -> Game.setScene(serviceLocator.getSceneFactory().newChooseMode());
        return new Button(serviceLocator, x, y, buttonSprite, chooseMode, "chooseMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createRegularModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getRegularModeButton();
        Runnable regularMode = () -> Game.setMode(Game.Modes.regular);
        return new Button(serviceLocator, x, y, buttonSprite, regularMode, "regularMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createDarknessModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getDarknessModeButton();
        Runnable darknessMode = () -> Game.setMode(Game.Modes.darkness);
        return new Button(serviceLocator, x, y, buttonSprite, darknessMode, "darknessMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createInvertModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getInvertModeButton();
        Runnable invertMode = () -> Game.setMode(Game.Modes.invert);
        return new Button(serviceLocator, x, y, buttonSprite, invertMode, "invertMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createSpaceModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getSpaceModeButton();
        Runnable spaceMode = () -> Game.setMode(Game.Modes.space);
        return new Button(serviceLocator, x, y, buttonSprite, spaceMode, "spaceMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createUnderwaterModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getUnderwaterModeButton();
        Runnable underwaterMode = () -> Game.setMode(Game.Modes.underwater);
        return new Button(serviceLocator, x, y, buttonSprite, underwaterMode, "underwaterMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createStoryModeButton(final int x, final int y) {
        assert serviceLocator != null;
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getStoryModeButton();
        Runnable storyMode = () -> Game.setMode(Game.Modes.story);
        return new Button(serviceLocator, x, y, buttonSprite, storyMode, "storyMode");
    }

}
