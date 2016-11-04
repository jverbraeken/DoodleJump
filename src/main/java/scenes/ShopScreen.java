package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
import rendering.Color;
import rendering.IRenderer;
import rendering.TextAlignment;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;

/**
 * This class is a scene that is displays when the doodle dies in a world.
 */
/* package */ final class ShopScreen implements IScene {

    /**
     * X & Y location in relation to the frame of the main menu button.
     */
    private static final double MAIN_MENU_BUTTON_X = 0.35, MAIN_MENU_BUTTON_Y = 0.03;
    /**
     * The X-position at which the buttons in the first row will be created.
     */
    private static final double BUTTON_X_START = 0.05d;
    /**
     * The X-position at which the buttons in the second row will be created.
     */
    private static final double BUTTON_X_START2 = 0.55d;
    /**
     * The Y-position at which the first button will be created.
     */
    private static final double BUTTON_Y_START = 0.35d;
    /**
     * The Y-distance between the buttons (buttons themselves including).
     */
    private static final double BUTTON_Y_OFFSET = 0.11d;
    /**
     * The X-distance between the button and the text (button included).
     */
    private static final int BUTTON_TEXT_OFFSET = 100;
    /**
     * The relative Y-position of the information about the powerups.
     */
    private static final double POWERUP_INFO_Y = 0.3d;
    /**
     * The relative X-position of the powerup level label, first column.
     */
    private static final double POWERUP_INFO_X = 0.06d;
    /**
     * The relative X-position of the powerup level label, second column.
     */
    private static final double POWERUP_INFO_X2 = 0.93d;
    /**
     * The text used to indicate the current level of the powerup.
     */
    private static final String POWERUP_INFO_LEVEL = "Level - Cost";
    /**
     * The speed with which the coin rotates.
     */
    private static final double COIN_SPEED = 0.3d;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Jetpack} upgrade button.
     */
    private static final double JETPACK_BUTTON_X = BUTTON_X_START, JETPACK_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;
    /**
     * The height of the button
     */
    private static final int JETPACK_BUTTON_HEIGHT = 80;
    /**
     * =
     * X & Y location in relation to the frame of the {@link objects.powerups.Propeller} upgrade button.
     */
    private static final double PROPELLER_BUTTON_X = BUTTON_X_START, PROPELLER_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;
    /**
     * The height of the button
     */
    private static final int PROPELLER_BUTTON_HEIGHT = 50;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeDown} upgrade button.
     */
    private static final double SIZEDOWN_BUTTON_X = BUTTON_X_START, SIZEDOWN_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;
    /**
     * The height of the button
     */
    private static final int SIZEDOWN_BUTTON_HEIGHT = 50;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeUp} upgrade button.
     */
    private static final double SIZEUP_BUTTON_X = BUTTON_X_START, SIZEUP_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 3;
    /**
     * The height of the button
     */
    private static final int SIZEUP_BUTTON_HEIGHT = 50;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Spring} upgrade button.
     */
    private static final double SPRING_BUTTON_X = BUTTON_X_START2, SPRING_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;
    /**
     * The height of the button
     */
    private static final int SPRING_BUTTON_HEIGHT = 40;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SpringShoes} upgrade button.
     */
    private static final double SPRINGSHOES_BUTTON_X = BUTTON_X_START2, SPRINGSHOES_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;
    /**
     * The height of the button
     */
    private static final int SPRINGSHOES_BUTTON_HEIGHT = 40;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Trampoline} upgrade button.
     */
    private static final double TRAMPOLINE_BUTTON_X = BUTTON_X_START2, TRAMPOLINE_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;
    /**
     * The height of the button
     */
    private static final int TRAMPOLINE_BUTTON_HEIGHT = 40;
    /**
     * The number of digits in the decimal system.
     */
    private static final int BASE_TEN = 10;

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
    private final ISprite[] coinSprites = new ISprite[BASE_TEN];
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

        background = sL.getSpriteFactory().getSprite(IRes.Sprites.background);
        bottomChooseModeScreen = sL.getSpriteFactory().getSprite(IRes.Sprites.killScreenBottom);

        IButtonFactory buttonFactory = sL.getButtonFactory();
        IButton mainMenuButton = buttonFactory.createMainMenuButton(
                MAIN_MENU_BUTTON_X,
                MAIN_MENU_BUTTON_Y);
        buttons.add(mainMenuButton);
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.jetpack,
                        JETPACK_BUTTON_X,
                        JETPACK_BUTTON_Y,
                        JETPACK_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.propeller,
                        PROPELLER_BUTTON_X,
                        PROPELLER_BUTTON_Y,
                        PROPELLER_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.sizeDown,
                        SIZEDOWN_BUTTON_X,
                        SIZEDOWN_BUTTON_Y,
                        SIZEDOWN_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.sizeUp,
                        SIZEUP_BUTTON_X,
                        SIZEUP_BUTTON_Y,
                        SIZEUP_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.spring,
                        SPRING_BUTTON_X,
                        SPRING_BUTTON_Y,
                        SPRING_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.springShoes,
                        SPRINGSHOES_BUTTON_X,
                        SPRINGSHOES_BUTTON_Y,
                        SPRINGSHOES_BUTTON_HEIGHT
                )
        );
        buttons.add(
                buttonFactory.createShopPowerupButton(
                        Powerups.trampoline,
                        TRAMPOLINE_BUTTON_X,
                        TRAMPOLINE_BUTTON_Y,
                        TRAMPOLINE_BUTTON_HEIGHT
                )
        );

        // Coins
        for (int i = 0; i < BASE_TEN; i++) {
            coinSprites[i] = this.serviceLocator.getSpriteFactory().getCoinSprite(i + 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.register();
        this.logger.info("The choose mode scene is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.deregister();
        this.logger.info("The choose mode scene is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register() {
        for (IButton button : this.buttons) {
            button.register();
        }
        this.logger.info("The score scene is now registered");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deregister() {
        for (IButton button : this.buttons) {
            button.deregister();
        }
        this.logger.info("The score scene is now deregistered");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        final IRenderer renderer = serviceLocator.getRenderer();
        final IConstants constants = serviceLocator.getConstants();
        final int width = constants.getGameWidth();
        final int height = constants.getGameHeight();
        renderer.drawSpriteHUD(this.background, new Point(0, 0));

        double y = (double) height - (double) bottomChooseModeScreen.getHeight();
        renderer.drawSpriteHUD(this.bottomChooseModeScreen, new Point(0, (int) y));

        ISprite coinSprite = this.coinSprites[(int) coinSpriteIndex];
        final int coinX = width / 2 + coinSprite.getHeight() / 2 - (int) (((double) coinSprite.getWidth() / (double) coinSprite.getHeight()) * (double) coinSprite.getHeight() / 2d);
        final int coinY = (int) (COIN_Y * height);
        renderer.drawSpriteHUD(coinSprite, new Point(coinX, coinY));

        final int coinTextX = width / 2 + coinSprite.getHeight() + COIN_TEXT_OFFSET;
        final int coinTextY = coinY + coinSprite.getHeight() / 2;
        renderer.drawTextHUD(new Point(coinTextX, coinTextY), Integer.toString(serviceLocator.getProgressionManager().getCoins()), Color.black);

        buttons.forEach(IRenderable::render);

        renderer.drawTextHUD(new Point((int) (POWERUP_INFO_X * width), (int) (POWERUP_INFO_Y * height)), POWERUP_INFO_LEVEL, TextAlignment.left, Color.black);
        renderer.drawTextHUD(new Point((int) (POWERUP_INFO_X2 * width), (int) (POWERUP_INFO_Y * height)), POWERUP_INFO_LEVEL, TextAlignment.right, Color.black);

        drawPowerupText(Powerups.propeller, PROPELLER_BUTTON_X, PROPELLER_BUTTON_Y);
        drawPowerupText(Powerups.sizeDown, SIZEDOWN_BUTTON_X, SIZEDOWN_BUTTON_Y);
        drawPowerupText(Powerups.sizeUp, SIZEUP_BUTTON_X, SIZEUP_BUTTON_Y);
        drawPowerupText(Powerups.jetpack, JETPACK_BUTTON_X, JETPACK_BUTTON_Y);
        drawPowerupText(Powerups.spring, SPRING_BUTTON_X, SPRING_BUTTON_Y);
        drawPowerupText(Powerups.springShoes, SPRINGSHOES_BUTTON_X, SPRINGSHOES_BUTTON_Y);
        drawPowerupText(Powerups.trampoline, TRAMPOLINE_BUTTON_X, TRAMPOLINE_BUTTON_Y);
    }

    /**
     * Draws the text next to a powerup-upgrade-button.
     *
     * @param powerup The type of the powerup
     * @param buttonX The x-position of the button
     * @param buttonY The y-position of the button
     */
    private void drawPowerupText(final Powerups powerup, final double buttonX, final double buttonY) {
        final IProgressionManager progressionManager = serviceLocator.getProgressionManager();
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        final IRenderer renderer = serviceLocator.getRenderer();
        final IConstants constants = serviceLocator.getConstants();
        final int width = constants.getGameWidth();
        final int height = constants.getGameHeight();

        final int level = progressionManager.getPowerupLevel(powerup);
        String string;
        if (level < powerup.getMaxLevel()) {
            final int price = powerup.getPrice(level + 1);
            string = ((level == 0) ? "X" : level) + " - " + price;
        } else {
            string = Integer.toString(level);
        }
        final int yOffset = spriteFactory.getPowerupSprite(powerup, progressionManager.getPowerupLevel(powerup) + 1).getHeight() / 2;
        renderer.drawTextHUD(new Point((int) (buttonX * width) + BUTTON_TEXT_OFFSET, (int) (buttonY * height) + yOffset), string, Color.black);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        coinSpriteIndex += COIN_SPEED * delta;
        coinSpriteIndex = coinSpriteIndex % BASE_TEN;
    }

}
