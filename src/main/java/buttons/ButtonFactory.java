package buttons;

import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
import progression.Ranks;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;

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
     * The logger.
     */
    private final ILogger logger;
    private final int gameWidth;
    private final int gameHeight;

    private ButtonFactory(IServiceLocator serviceLocator) {
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
        this.gameWidth = serviceLocator.getConstants().getGameWidth();
        this.gameHeight = serviceLocator.getConstants().getGameHeight();
    }

    /**
     * Register the platform factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        ButtonFactory.serviceLocator = sL;
        ButtonFactory.serviceLocator.provide(getButtonFactory(serviceLocator));
    }

    /**
     * The synchronized getter of the singleton buttonFactory.
     *
     * @return the button factory
     */
    private static synchronized IButtonFactory getButtonFactory(final IServiceLocator serviceLocator) {
        if (ButtonFactory.buttonFactory == null) {
            ButtonFactory.buttonFactory = new ButtonFactory(serviceLocator);
        }
        return ButtonFactory.buttonFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        Runnable playAction = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createSinglePlayerWorld());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, playAction, "play");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMultiplayerButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMultiplayerButtonSprite();
        Runnable playAction = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createTwoPlayerWorld());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, playAction, "multiplayer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createResumeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getResumeButtonSprite();
        Runnable resumeAction = () -> Game.setPaused(false);
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, resumeAction, "resume");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPlayAgainButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayAgainButtonSprite();
        Runnable playAgainAction = () -> {
            if (Game.getPlayerMode() == Game.PlayerModes.single) {
                Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createSinglePlayerWorld());
            } else {
                Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createTwoPlayerWorld());
            }
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, playAgainAction, "playAgain");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createShopButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getShopButtonSprite();
        Runnable toShop = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createShopScreen());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, toShop, "shop");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createMainMenuButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getMenuButtonSprite();
        Runnable mainMenu = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createMainMenu());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, mainMenu, "mainMenu");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createScoreButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getScoreButtonSprite();
        Runnable scoreScreen = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createScoreScreen());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, scoreScreen, "scores");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createChooseModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getChooseModeButtonSprite();
        Runnable chooseMode = () -> Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().newChooseMode());
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, chooseMode, "chooseMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createRegularModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getRegularModeButton();
        Runnable regularMode = () -> Game.setMode(Game.Modes.regular);
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, regularMode, "regularMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createDarknessModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getDarknessModeButton();
        Runnable darknessMode = () -> {
            if(serviceLocator.getProgressionManager().getRank().getLevelNumber() >= Game.Modes.darkness.getRankRequired())
                Game.setMode(Game.Modes.darkness);
            else
                new scenes.Popup("test");
                //scenes.Popup.CreatePopup("test");
//                JOptionPane.showMessageDialog(Game.frame, "The rank: " +
//                        Ranks.getRankByLevelNumber(Game.Modes.darkness.getRankRequired()).getName() +
//                        " is required to play this gamemode.");
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, darknessMode, "darknessMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createInvertModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getInvertModeButton();
        Runnable invertMode = () -> {
            if(serviceLocator.getProgressionManager().getRank().getLevelNumber() >= Game.Modes.invert.getRankRequired())
                Game.setMode(Game.Modes.invert);
            else
                JOptionPane.showMessageDialog(Game.frame, "The rank: " +
                        Ranks.getRankByLevelNumber(Game.Modes.invert.getRankRequired()).getName() +
                        " is required to play this gamemode.");
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, invertMode, "invertMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createSpaceModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getSpaceModeButton();
        Runnable spaceMode = () -> {
            if(serviceLocator.getProgressionManager().getRank().getLevelNumber() >= Game.Modes.space.getRankRequired())
                Game.setMode(Game.Modes.space);
            else
                JOptionPane.showMessageDialog(Game.frame, "The rank: " +
                        Ranks.getRankByLevelNumber(Game.Modes.space.getRankRequired()).getName() +
                        " is required to play this gamemode.");
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, spaceMode, "spaceMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createUnderwaterModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getUnderwaterModeButton();
        Runnable underwaterMode = () -> {
            if(serviceLocator.getProgressionManager().getRank().getLevelNumber() >= Game.Modes.underwater.getRankRequired())
                Game.setMode(Game.Modes.underwater);
            else
                JOptionPane.showMessageDialog(Game.frame, "The rank: " +
                        Ranks.getRankByLevelNumber(Game.Modes.underwater.getRankRequired()).getName() +
                        " is required to play this gamemode.");
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, underwaterMode, "underwaterMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createStoryModeButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getStoryModeButton();
        Runnable storyMode = () -> {
            if (serviceLocator.getProgressionManager().getRank().getLevelNumber() >= Game.Modes.story.getRankRequired())
                Game.setMode(Game.Modes.story);
            else
                JOptionPane.showMessageDialog(Game.frame, "The rank: " +
                        Ranks.getRankByLevelNumber(Game.Modes.story.getRankRequired()).getName() +
                        " is required to play this gamemode.");
        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, storyMode, "storyMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createShopPowerupButton(final Powerups powerup, final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;

        if (powerup == null) {
            final String error = "There cannot a button be created for a null powerup";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        final IProgressionManager progressionManager = serviceLocator.getProgressionManager();
        final int currentPowerupLevel = progressionManager.getPowerupLevel(powerup);

        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPowerupSprite(powerup, currentPowerupLevel + 1);
        Runnable shop = () -> {
            final int powerupLevel = progressionManager.getPowerupLevel(powerup);
            if (powerupLevel < powerup.getMaxLevel()) {
                final int price = powerup.getPrice(powerupLevel + 1);
                if (progressionManager.getCoins() >= price) {
                    progressionManager.decreaseCoins(price);
                    progressionManager.increasePowerupLevel(powerup);
                    Game.setScene(ButtonFactory.serviceLocator.getSceneFactory().createShopScreen());
                }
            }

        };
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, shop, "shop");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createPauseButton(final double x, final double y) {
        assert ButtonFactory.serviceLocator != null;
        ISpriteFactory spriteFactory = ButtonFactory.serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPauseButtonSprite();
        Runnable pause = () -> Game.setPaused(true);
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, pause, "pause");
    }

}
