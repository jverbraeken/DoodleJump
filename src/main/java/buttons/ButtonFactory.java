package buttons;

import groovy.lang.Tuple2;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
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
     * The logger.
     */
    private final ILogger logger;
    /**
     * A copy of the game width constant, used to shorten the code.
     */
    private final int gameWidth;
    /**
     * A copy of the game height constant, used to shorten the code.
     */
    private final int gameHeight;

    /**
     * Constructs a new ButtonFactory.
     *
     * @param serviceLocator The service locator
     */
    private ButtonFactory(final IServiceLocator serviceLocator) {
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
        this.gameWidth = serviceLocator.getConstants().getGameWidth();
        this.gameHeight = serviceLocator.getConstants().getGameHeight();
    }

    /**
     * Register the platform factory into the service locator.
     *
     * @param serviceLocator the service locator.
     */
    public static void register(final IServiceLocator serviceLocator) {
        if (serviceLocator == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        ButtonFactory.serviceLocator = serviceLocator;
        ButtonFactory.serviceLocator.provide(getButtonFactory(ButtonFactory.serviceLocator));
    }

    /**
     * The synchronized getter of the singleton buttonFactory.
     *
     * @param serviceLocator the service locator.
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
        Runnable darknessMode = () -> Game.setMode(Game.Modes.darkness);
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
        Runnable invertMode = () -> Game.setMode(Game.Modes.invert);
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
        Runnable spaceMode = () -> Game.setMode(Game.Modes.space);
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
        Runnable underwaterMode = () -> Game.setMode(Game.Modes.underwater);
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
        Runnable storyMode = () -> Game.setMode(Game.Modes.story);
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, storyMode, "storyMode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton createShopPowerupButton(final Powerups powerup, final double x, final double y, final int height) {
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
        final int buttonWidth = (int) ((double) height * ((double) buttonSprite.getWidth() / (double) buttonSprite.getHeight()));
        return new Button(ButtonFactory.serviceLocator, (int) (gameWidth * x), (int) (gameHeight * y), buttonSprite, shop, "shop", new Tuple2<>(buttonWidth, height));
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
