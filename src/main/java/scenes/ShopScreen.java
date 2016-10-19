package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
import rendering.Color;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IRenderable;
import system.IServiceLocator;

import java.util.ArrayList;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ class ShopScreen implements IScene {

    /**
     * X & Y location in relation to the frame of the main menu button.
     */
    private static final double MAIN_MENU_BUTTON_X = 0.35, MAIN_MENU_BUTTON_Y = 0.03;


    /**
     * The X-position at which the buttons in the first row will be created.
     */
    private static final double BUTTON_X_START = 0.15d;
    /**
     * The X-position at which the buttons in the second row will be created.
     */
    private static final double BUTTON_X_START2 = 0.55d;
    /**
     * The Y-position at which the first button will be created.
     */
    private static final double BUTTON_Y_START = 0.4d;
    /**
     * The Y-distance between the buttons (buttons themselves including).
     */
    private static final double BUTTON_Y_OFFSET = 0.11d;
    /**
     * The X-distance between the button and the text (button included).
     */
    private static final int BUTTON_TEXT_OFFSET = 100;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Jetpack} upgrade button.
     */
    private static final double JETPACK_BUTTON_X = BUTTON_X_START, JETPACK_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Propeller} upgrade button.
     */
    private static final double PROPELLER_BUTTON_X = BUTTON_X_START, PROPELLER_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeDown} upgrade button.
     */
    private static final double SIZEDOWN_BUTTON_X = BUTTON_X_START, SIZEDOWN_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeUp} upgrade button.
     */
    private static final double SIZEUP_BUTTON_X = BUTTON_X_START, SIZEUP_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 3;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Spring} upgrade button.
     */
    private static final double SPRING_BUTTON_X = BUTTON_X_START2, SPRING_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SpringShoes} upgrade button.
     */
    private static final double SPRINGSHOES_BUTTON_X = BUTTON_X_START2, SPRINGSHOES_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Trampoline} upgrade button.
     */
    private static final double TRAMPOLINE_BUTTON_X = BUTTON_X_START2, TRAMPOLINE_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;

    /**
     * The relative Y-position of the coin.
     */
    private static final double COIN_Y = 0.15;
    /**
     * The distance between the coin and the text.
     */
    private static final int COIN_TEXT_OFFSET = 10;

    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the ChooseMode class.
     */
    private final ILogger logger;
    /**
     * The sprite on the bottom of the choose mode screen.
     */
    private final ISprite bottomChooseModeScreen;
    /**
     * An ArrayList of all the buttons.
     */
    private final ArrayList<IButton> buttons = new ArrayList<>();
    /**
     * The sprites of the coin, animated.
     */
    private final ISprite[] coinSprites = new ISprite[10];
    /**
     * Sprites of the background of the ChooseMode.
     */
    private ISprite background;
    /**
     * The index of the coin animation. Must be between 0 (inclusive) and 10 (exclusive).
     */
    private double coinSpriteIndex;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ ShopScreen(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(ShopScreen.class);

        background = sL.getSpriteFactory().getBackground();
        bottomChooseModeScreen = sL.getSpriteFactory().getKillScreenBottomSprite();

        IButtonFactory buttonFactory = sL.getButtonFactory();
        IButton mainMenuButton = buttonFactory.createMainMenuButton(
                (int) (sL.getConstants().getGameWidth() * MAIN_MENU_BUTTON_X),
                (int) (sL.getConstants().getGameHeight() * MAIN_MENU_BUTTON_Y));
        buttons.add(mainMenuButton);
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.JETPACK,
                        (int) (sL.getConstants().getGameWidth() * JETPACK_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * JETPACK_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.PROPELLER,
                        (int) (sL.getConstants().getGameWidth() * PROPELLER_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * PROPELLER_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.SIZEDOWN,
                        (int) (sL.getConstants().getGameWidth() * SIZEDOWN_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * SIZEDOWN_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.SIZEUP,
                        (int) (sL.getConstants().getGameWidth() * SIZEUP_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * SIZEUP_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.SPRING,
                        (int) (sL.getConstants().getGameWidth() * SPRING_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * SPRING_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.SPRINGSHOES,
                        (int) (sL.getConstants().getGameWidth() * SPRINGSHOES_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * SPRINGSHOES_BUTTON_Y)
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.TRAMPOLINE,
                        (int) (sL.getConstants().getGameWidth() * TRAMPOLINE_BUTTON_X),
                        (int) (sL.getConstants().getGameHeight() * TRAMPOLINE_BUTTON_Y)
                )
        );

        // Coins
        for (int i = 0; i < 10; i++) {
            coinSprites[i] = this.serviceLocator.getSpriteFactory().getCoinSprite(i + 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        for (IButton button : buttons) {
            button.register();
        }
        logger.info("The choose mode scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        for (IButton button : buttons) {
            button.deregister();
        }
        logger.info("The choose mode scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        final IRenderer renderer = serviceLocator.getRenderer();
        final IProgressionManager progressionManager = serviceLocator.getProgressionManager();
        final IConstants constants = serviceLocator.getConstants();
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        final int width = constants.getGameWidth();
        final int height = constants.getGameHeight();
        renderer.drawSpriteHUD(this.background, 0, 0);

        double y = (double) height - (double) bottomChooseModeScreen.getHeight();
        renderer.drawSpriteHUD(this.bottomChooseModeScreen, 0, (int) y);

        ISprite coinSprite = this.coinSprites[(int) coinSpriteIndex];
        final int coinX = width / 2 + coinSprite.getHeight() / 2 - (int) (((double) coinSprite.getWidth() / (double) coinSprite.getHeight()) * (double) coinSprite.getHeight() / 2d);
        final int coinY = (int) (COIN_Y * height);
        renderer.drawSpriteHUD(coinSprite, coinX, coinY);

        final int coinTextX = width / 2 + coinSprite.getHeight() + COIN_TEXT_OFFSET;
        final int coinTextY = coinY + coinSprite.getHeight() / 2;
        renderer.drawTextHUD(coinTextX, coinTextY, Integer.toString(serviceLocator.getProgressionManager().getCoins()), Color.black);

        buttons.forEach(IRenderable::render);
        // TODO ultra lelijke code
        if (progressionManager.getPowerupLevel(Powerups.JETPACK) < Powerups.JETPACK.getMaxLevel() - 1) {
            final int price = Powerups.JETPACK.getPrice(progressionManager.getPowerupLevel(Powerups.JETPACK) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.JETPACK, progressionManager.getPowerupLevel(Powerups.JETPACK)).getHeight() / 2;
            renderer.drawTextHUD((int) (JETPACK_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (JETPACK_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.PROPELLER) < Powerups.PROPELLER.getMaxLevel() - 1) {
            final int price = Powerups.PROPELLER.getPrice(progressionManager.getPowerupLevel(Powerups.PROPELLER) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (PROPELLER_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (PROPELLER_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.SIZEDOWN) < Powerups.SIZEDOWN.getMaxLevel() - 1) {
            final int price = Powerups.SIZEDOWN.getPrice(progressionManager.getPowerupLevel(Powerups.SIZEDOWN) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (SIZEDOWN_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (SIZEDOWN_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.SIZEUP) < Powerups.SIZEUP.getMaxLevel() - 1) {
            final int price = Powerups.SIZEUP.getPrice(progressionManager.getPowerupLevel(Powerups.SIZEUP) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (SIZEUP_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (SIZEUP_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.SPRING) < Powerups.SPRING.getMaxLevel() - 1) {
            final int price = Powerups.SPRING.getPrice(progressionManager.getPowerupLevel(Powerups.SPRING) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (SPRING_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (SPRING_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.SPRINGSHOES) < Powerups.SPRINGSHOES.getMaxLevel() - 1) {
            final int price = Powerups.SPRINGSHOES.getPrice(progressionManager.getPowerupLevel(Powerups.SPRINGSHOES) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (SPRINGSHOES_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (SPRINGSHOES_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
        if (progressionManager.getPowerupLevel(Powerups.TRAMPOLINE) < Powerups.TRAMPOLINE.getMaxLevel() - 1) {
            final int price = Powerups.TRAMPOLINE.getPrice(progressionManager.getPowerupLevel(Powerups.JETPACK) + 1);
            final int yOffset = spriteFactory.getPowerupSprite(Powerups.SPRING, progressionManager.getPowerupLevel(Powerups.SPRING)).getHeight();
            renderer.drawTextHUD((int) (TRAMPOLINE_BUTTON_X * width) + BUTTON_TEXT_OFFSET, (int) (TRAMPOLINE_BUTTON_Y * height) + yOffset, Integer.toString(price), Color.black);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        coinSpriteIndex += 0.3 * delta;
        coinSpriteIndex = coinSpriteIndex % 10;
    }

}
