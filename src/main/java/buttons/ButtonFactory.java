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
    //TODO: correct sprite
    @Override
    public IButton createChooseModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getChooseModeButtonSprite();
        Runnable chooseMode = () -> Game.setScene(sL.getSceneFactory().newChooseMode());
        return new Button(sL, x, y, buttonSprite, chooseMode, "chooseMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createRegularModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getRegularModeButton();
        Runnable regularMode = () -> Game.setMode("REGULAR");
        return new Button(sL, x, y, buttonSprite, regularMode, "regularMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createDarknessModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getDarknessModeButton();
        Runnable darknessMode = () -> Game.setMode("DARKNESS");
        return new Button(sL, x, y, buttonSprite, darknessMode, "darknessMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createInvertModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getInvertModeButton();
        Runnable invertMode = () -> Game.setMode("INVERT");
        return new Button(sL, x, y, buttonSprite, invertMode, "invertMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createSpaceModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getSpaceModeButton();
        Runnable spaceMode = () -> Game.setMode("SPACE");
        return new Button(sL, x, y, buttonSprite, spaceMode, "spaceMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createUnderwaterModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getUnderwaterModeButton();
        Runnable underwaterMode = () -> Game.setMode("UNDERWATER");
        return new Button(sL, x, y, buttonSprite, underwaterMode, "underwaterMode");
    }

    /**
     * {@inheritDoc}
     */
    //TODO: correct sprite to "main menu" button
    @Override
    public IButton createStoryModeButton(final int x, final int y) {
        assert sL != null;
        ISpriteFactory spriteFactory = sL.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getStoryModeButton();
        Runnable storyMode = () -> Game.setMode("STORY");
        return new Button(sL, x, y, buttonSprite, storyMode, "storyMode");
    }
}
