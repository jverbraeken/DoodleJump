package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import objects.powerups.Powerups;
import progression.IProgressionManager;
import progression.Mission;
import rendering.Color;
import rendering.IRenderer;
import rendering.TextAlignment;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * PauseScreen implementation of a scene.
 */
/* package */ class PauseScreen implements IScene {

    /**
     * The X and Y location for the resume button.
     */
    private static final double RESUME_BUTTON_X = 0.55d, RESUME_BUTTON_Y = 0.7d;

    /**
     * The X and Y location for the switch button.
     */
    private static final double SWITCH_BUTTON_X = 0.6d, SWITCH_BUTTON_Y = 0.8d;

    /**
     * The X-position at which the buttons in the first row and second row will be created respectively.
     */
    private static final double BUTTON_X_START = 0.05d, BUTTON_X_START2 = 0.55d;

    /**
     * The Y-position at which the first button will be created.
     */
    private static final double BUTTON_Y_START = 0.3d;

    /**
     * The Y-distance between the buttons (buttons themselves including).
     */
    private static final double BUTTON_Y_OFFSET = 0.11d;

    /**
     * The relative X- and Y-position of the powerup level label respectively.
     */
    private static final double POWERUP_INFO_X = 0.06d, POWERUP_INFO_Y = 0.25d;
    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Propeller} upgrade button.
     */
    private static final double PROPELLER_BUTTON_X = BUTTON_X_START, PROPELLER_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 0;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeDown} upgrade button.
     */
    private static final double SIZEDOWN_BUTTON_X = BUTTON_X_START, SIZEDOWN_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 1;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeUp} upgrade button.
     */
    private static final double SIZEUP_BUTTON_X = BUTTON_X_START, SIZEUP_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 2;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Jetpack} upgrade button.
     */
    private static final double JETPACK_BUTTON_X = BUTTON_X_START, JETPACK_BUTTON_Y = BUTTON_Y_START + BUTTON_Y_OFFSET * 3;

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
     * The X-distance between the button and the text (button included).
     */
    private static final int BUTTON_TEXT_OFFSET = 100;
    /**
     * Used to gain access to all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the PauseScreen class.
     */
    private final ILogger logger;
    /**
     * The resume button.
     */
    private final IButton resumeButton;
    /**
     * The switch button.
     */
    private final IButton switchMissionButton, switchShopButton;

    /**
     * The sprites of the coin, animated.
     */
    private final ISprite[] coinSprites = new ISprite[10];
    /**
     * The index of the coin animation. Must be between 0 (inclusive) and 10 (exclusive).
     */
    private double coinSpriteIndex;
    /**
     * Default margin for the HUD grid.
     */
    private static final int MARGIN = 10;

    /**
     * A HashMap of the powerup buttons.
     */
    private final EnumMap<Powerups, IButton> buttonMap = new EnumMap<>(Powerups.class);

    /**
     * The distance between the missions drawn at the screen.
     */
    private static final int DISTANCE_BETWEEN_MISSIONS = 15;
    /**
     * Variable to decide between the display of the shop or missions(True and False respectively).
     */
    private PauseScreenModes mode;

    /**
     * Initialize the pause screen.
     *
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(final IServiceLocator sL) {
        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Coins
        for (int i = 0; i < 10; i++) {
            coinSprites[i] = this.serviceLocator.getSpriteFactory().getCoinSprite(i + 1);
        }

        // Buttons
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        this.resumeButton = buttonFactory.createResumeButton(PauseScreen.RESUME_BUTTON_X, PauseScreen.RESUME_BUTTON_Y);
        this.switchMissionButton = buttonFactory.createSwitchToMissionButton(PauseScreen.SWITCH_BUTTON_X, PauseScreen.SWITCH_BUTTON_Y);
        this.switchShopButton = buttonFactory.createSwitchToShopButton(PauseScreen.SWITCH_BUTTON_X, PauseScreen.SWITCH_BUTTON_Y);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        mode = PauseScreenModes.mission; // when opening the pause screen, the screen will display the missions by default.
        this.resumeButton.register();
        this.logger.info("The resume button is now available");
        this.switchShopButton.register();
        this.logger.info("The switch button is now available");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.resumeButton.deregister();
        this.logger.info("The resume button is no longer available");

        if (mode == PauseScreenModes.shop) {
            this.stopShopCover();
        } else {
            this.switchShopButton.deregister();
            this.logger.info("The switch button to the shop cover is no longer available");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        assert coinSprites[(int) coinSpriteIndex] != null;
        assert resumeButton != null;

        serviceLocator.getRenderer().drawSpriteHUD(serviceLocator.getSpriteFactory().getPauseCoverSprite(mode), new Point(0, 0));

        resumeButton.render();
        ISprite coinSprite = this.coinSprites[(int) coinSpriteIndex];
        final int coinX = MARGIN + coinSprite.getHeight() / 2 - (int) (((double) coinSprite.getWidth() / (double) coinSprite.getHeight()) * (double) coinSprite.getHeight() / 2d);
        final int coinY = serviceLocator.getSpriteFactory().getScoreBarSprite().getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(coinSprite, new Point(coinX, coinY));

        final int coinTextX = MARGIN + coinSprite.getHeight() + MARGIN;
        final int coinTextY = coinY + coinSprite.getHeight() / 2;
        serviceLocator.getRenderer().drawTextHUD(new Point(coinTextX, coinTextY), Integer.toString(serviceLocator.getProgressionManager().getCoins()), Color.black);

        if (mode == PauseScreenModes.mission) {
            this.renderMissions(coinY + coinSprite.getHeight());
        } else {
            this.renderShop();
        }
    }

    /**
     * Draws the sprites and texts for the missions that are currently active.
     */
    private void renderMissions(final int y) {
        this.switchShopButton.render();
        final List<Mission> missions = serviceLocator.getProgressionManager().getMissions();
        final int missionSpriteHeight = serviceLocator.getSpriteFactory().getAchievementSprite().getHeight();
        for (int i = 0; i < missions.size(); i++) {
            missions.get(i).render(y + MARGIN + i * (missionSpriteHeight + DISTANCE_BETWEEN_MISSIONS));
        }
    }

    /**
     * Draws the sprites and texts for the shop while the game is paused.
     */
    private void renderShop() {
        assert switchMissionButton != null;

        this.switchMissionButton.render();
        for (Map.Entry<Powerups, IButton> entry : buttonMap.entrySet()) {
            entry.getValue().render();
        }
        this.drawText();
    }

    /**
     * Activates the buttons that should be observable on the shop display.
     */
    private void setShopCover() {
        this.switchMissionButton.register();
        this.logger.info("The switch button to the mission cover is now available");
        if (buttonMap.size() == 0) {
            this.createPowerupbutton();
        }
        for (Map.Entry<Powerups, IButton> entry : buttonMap.entrySet()) {
            entry.getValue().register();
        }
        this.logger.info("The powerup buttons are now available");
    }

    /**
     * Deactivates the buttons of the powerups that are displaying.
     */
    private void stopShopCover() {
        for (Map.Entry<Powerups, IButton> entry : buttonMap.entrySet()) {
            entry.getValue().deregister();
        }
        this.logger.info("The powerup buttons are no longer available");
        this.switchMissionButton.deregister();
        this.logger.info("The switch button to the mission cover is no longer available");
    }

    /**
     * {@inheritDoc}
     */
    public void switchDisplay(PauseScreenModes mode) {
        this.mode = mode;
        if (this.mode.equals(PauseScreenModes.mission)) {
            this.stopShopCover();
            this.switchShopButton.register();
            this.logger.info("The switch button to the shop cover is now available");
        } else {
            this.switchShopButton.deregister();
            this.logger.info("The switch button to the shop cover is no longer available");
            this.setShopCover();
        }
    }

    /**
     * Draws the text of the powerups.
     */
    private void drawText() {
        serviceLocator.getRenderer().drawTextHUD(
                new Point((int) (POWERUP_INFO_X * serviceLocator.getConstants().getGameWidth()),
                        (int) (POWERUP_INFO_Y * serviceLocator.getConstants().getGameHeight())),
                "Level - Cost",
                TextAlignment.left,
                Color.black);
        drawPowerupText(Powerups.propeller, PROPELLER_BUTTON_X, PROPELLER_BUTTON_Y);
        drawPowerupText(Powerups.sizeDown, SIZEDOWN_BUTTON_X, SIZEDOWN_BUTTON_Y);
        drawPowerupText(Powerups.sizeUp, SIZEUP_BUTTON_X, SIZEUP_BUTTON_Y);
        drawPowerupText(Powerups.jetpack, JETPACK_BUTTON_X, JETPACK_BUTTON_Y);
        drawPowerupText(Powerups.spring, SPRING_BUTTON_X, SPRING_BUTTON_Y);
        drawPowerupText(Powerups.springShoes, SPRINGSHOES_BUTTON_X, SPRINGSHOES_BUTTON_Y);
        drawPowerupText(Powerups.trampoline, TRAMPOLINE_BUTTON_X, TRAMPOLINE_BUTTON_Y);
    }

    /**
     * Creates the buttons for the powerps that can be unlocked or upgraded.
     */
    private void createPowerupbutton() {
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        this.buttonMap.put(Powerups.jetpack, buttonFactory.createPausePowerupButton(Powerups.jetpack, JETPACK_BUTTON_X, JETPACK_BUTTON_Y));
        this.buttonMap.put(Powerups.propeller, buttonFactory.createPausePowerupButton(Powerups.propeller, PROPELLER_BUTTON_X, PROPELLER_BUTTON_Y));
        this.buttonMap.put(Powerups.sizeDown, buttonFactory.createPausePowerupButton(Powerups.sizeDown, SIZEDOWN_BUTTON_X, SIZEDOWN_BUTTON_Y));
        this.buttonMap.put(Powerups.sizeUp, buttonFactory.createPausePowerupButton(Powerups.sizeUp, SIZEUP_BUTTON_X, SIZEUP_BUTTON_Y));
        this.buttonMap.put(Powerups.spring, buttonFactory.createPausePowerupButton(Powerups.spring, SPRING_BUTTON_X, SPRING_BUTTON_Y));
        this.buttonMap.put(Powerups.springShoes, buttonFactory.createPausePowerupButton(Powerups.springShoes, SPRINGSHOES_BUTTON_X, SPRINGSHOES_BUTTON_Y));
        this.buttonMap.put(Powerups.trampoline, buttonFactory.createPausePowerupButton(Powerups.trampoline, TRAMPOLINE_BUTTON_X, TRAMPOLINE_BUTTON_Y));
    }

    /**
     * Draws the text consisting of the current level of the powerup and the cost to activate/upgrade it
     *
     * @param powerup The type of powerup
     * @param x       The relative X-position of the text.
     * @param y       The relative Y-position of the text.
     */
    private void drawPowerupText(final Powerups powerup, final double x, final double y) {
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
        renderer.drawTextHUD(new Point((int) (x * width) + BUTTON_TEXT_OFFSET, (int) (y * height) + yOffset), string, Color.black);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateButton(final Powerups powerup, final double x, final double y) {
        buttonMap.get(powerup).deregister();
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        IButton button = buttonFactory.createPausePowerupButton(powerup, x, y);
        buttonMap.remove(powerup);
        buttonMap.put(powerup, button);
        button.register();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        coinSpriteIndex = (coinSpriteIndex + 0.3 * delta) % 10;
    }

}
