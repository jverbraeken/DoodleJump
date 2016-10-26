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

import java.util.ArrayList;
import java.util.List;

/**
 * PauseScreen implementation of a scene.
 */
/* package */ class PauseScreen implements IScene {

    /**
     * The X and Y location for the resume button.
     */
    private static final double RESUME_BUTTON_X = 0.55d, RESUME_BUTTON_Y = 0.7d;

    /**
     * The X and Y location for the resume button.
     */
    private static final double SHOP_BUTTON_X = 0.6d, SHOP_BUTTON_Y = 0.8d;

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
     * The relative X- and Y-position of the powerup level label.
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
     * The resume button.
     */
    private final IButton switchButton;
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
     * An ArrayList of all the buttons.
     */
    private final ArrayList<IButton> powerupButtons = new ArrayList<>();
    /**
     * The background sprite.
     */
    private ISprite[] background;
    /**
     * The distance between the missions drawn at the screen.
     */
    private static final int DISTANCE_BETWEEN_MISSIONS = 15;
    /**
     * Variable to decide between the display of the shop or missions.
     */
    private boolean mode;

    /**
     * Initialize the pause screen.
     *
     * @param sL The games service locator.
     */
    /* package */ PauseScreen(final IServiceLocator sL) {
        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(PauseScreen.class);

        // Background
        this.background = this.serviceLocator.getSpriteFactory().getPauseCoverSprite();

        // Coins
        for (int i = 0; i < 10; i++) {
            coinSprites[i] = this.serviceLocator.getSpriteFactory().getCoinSprite(i + 1);
        }

        // Button
        IButtonFactory buttonFactory = this.serviceLocator.getButtonFactory();
        this.resumeButton = buttonFactory.createResumeButton(PauseScreen.RESUME_BUTTON_X, PauseScreen.RESUME_BUTTON_Y);
        this.switchButton = buttonFactory.createSwitchButton(PauseScreen.SHOP_BUTTON_X, PauseScreen.SHOP_BUTTON_Y);
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.jetpack, JETPACK_BUTTON_X, JETPACK_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.propeller, PROPELLER_BUTTON_X, PROPELLER_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.sizeDown, SIZEDOWN_BUTTON_X, SIZEDOWN_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.sizeUp, SIZEUP_BUTTON_X, SIZEUP_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.spring, SPRING_BUTTON_X, SPRING_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.springShoes, SPRINGSHOES_BUTTON_X, SPRINGSHOES_BUTTON_Y));
        this.powerupButtons.add(buttonFactory.createShopPowerupButton(Powerups.trampoline, TRAMPOLINE_BUTTON_X, TRAMPOLINE_BUTTON_Y));


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        mode = false; // when opening the pause screen, the screen will display the missions by default.
        this.resumeButton.register();
        this.logger.info("The pause scene is now displaying");
        this.switchButton.register();
        this.logger.info("The shop scene is now displaying");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.resumeButton.deregister();
        this.logger.info("The resume button is no longer displaying");
        this.switchButton.deregister();
        this.logger.info("The pause scene is no longer displaying");
        if(mode) {
            this.stopShopCover();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        assert background != null;
        assert coinSprites[(int) coinSpriteIndex] != null;
        assert resumeButton != null;
        assert switchButton != null;

        serviceLocator.getRenderer().drawSpriteHUD(this.getBackgroundSprite(mode), 0, 0);
        resumeButton.render();
        switchButton.render();
        ISprite coinSprite = this.coinSprites[(int) coinSpriteIndex];
        final int coinX = MARGIN + coinSprite.getHeight() / 2 - (int) (((double) coinSprite.getWidth() / (double) coinSprite.getHeight()) * (double) coinSprite.getHeight() / 2d);
        final int coinY = serviceLocator.getSpriteFactory().getScoreBarSprite().getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(coinSprite, coinX, coinY);

        final int coinTextX = MARGIN + coinSprite.getHeight() + MARGIN;
        final int coinTextY = coinY + coinSprite.getHeight() / 2;
        serviceLocator.getRenderer().drawTextHUD(coinTextX, coinTextY, Integer.toString(serviceLocator.getProgressionManager().getCoins()), Color.black);

        if(!mode) {
            this.renderMissions(coinY + coinSprite.getHeight());
        }
        else {
            this.renderShop();
        }
    }

    /**
     * Draws the sprites and texts for the missions that are currently active.
     */
    private void renderMissions(final int y) {
        this.stopShopCover();

        final List<Mission> missions = serviceLocator.getProgressionManager().getMissions();
        final int missionSpriteHeight = serviceLocator.getSpriteFactory().getAchievementSprite().getHeight();
        for (int i = 0; i < missions.size(); i++) {
            missions.get(i).render(y + MARGIN  + i * (missionSpriteHeight + DISTANCE_BETWEEN_MISSIONS));
        }
    }

    /**
     * Draws the sprites and texts for the shop while the game is paused.
     */
    private void renderShop() {
        assert powerupButtons != null;

        powerupButtons.forEach(IButton::register);
        powerupButtons.forEach(IButton::render);

        this.drawText();
    }

    /**
     * Deactivates the buttons of the powerups that are displaying.
     */
    private void stopShopCover() {
        powerupButtons.forEach(IButton::deregister);
        this.logger.info("The trampoline is no longer available");
    }

    private ISprite getBackgroundSprite(boolean mode) {
        if(mode) {
            return background[1];
        } else {
            return background[0];
        }
    }

    /**
     * {@inheritDoc}
     */
    public void switchMode() {
        this.mode = !mode;
    }

    private void drawText() {
        serviceLocator.getRenderer().drawTextHUD(
                (int) (POWERUP_INFO_X * serviceLocator.getConstants().getGameWidth()),
                (int) (POWERUP_INFO_Y * serviceLocator.getConstants().getGameHeight()),
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

    private void drawPowerupText(final Powerups powerup, final double jetpackButtonX, final double jetpackButtonY) {
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
        }
        else {
            string = Integer.toString(level);
        }
        final int yOffset = spriteFactory.getPowerupSprite(powerup, progressionManager.getPowerupLevel(powerup) + 1).getHeight() / 2;
        renderer.drawTextHUD((int) (jetpackButtonX * width) + BUTTON_TEXT_OFFSET, (int) (jetpackButtonY * height) + yOffset, string, Color.black);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        coinSpriteIndex = (coinSpriteIndex + 0.3 * delta) % 10;
    }

}
