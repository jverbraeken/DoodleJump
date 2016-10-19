package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import logging.ILogger;
import objects.powerups.Powerups;
import resources.sprites.ISprite;
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
    private static final double MAIN_MENU_BUTTON_X = 0.35, MAIN_MENU_BUTTON_Y = 0.1;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Jetpack} upgrade button.
     */
    private static final double JETPACK_BUTTON_X = 0.1, JETPACK_BUTTON_Y = 0.25;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Propeller} upgrade button.
     */
    private static final double PROPELLER_BUTTON_X = 0.1, PROPELLER_BUTTON_Y = 0.35;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeDown} upgrade button.
     */
    private static final double SIZEDOWN_BUTTON_X = 0.1, SIZEDOWN_BUTTON_Y = 0.45;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SizeUp} upgrade button.
     */
    private static final double SIZEUP_BUTTON_X = 0.1, SIZEUP_BUTTON_Y = 0.55;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Spring} upgrade button.
     */
    private static final double SPRING_BUTTON_X = 0.6, SPRING_BUTTON_Y = 0.25;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.SpringShoes} upgrade button.
     */
    private static final double SPRINGSHOES_BUTTON_X = 0.6, SPRINGSHOES_BUTTON_Y = 0.35;

    /**
     * X & Y location in relation to the frame of the {@link objects.powerups.Trampoline} upgrade button.
     */
    private static final double TRAMPOLINE_BUTTON_X = 0.6, TRAMPOLINE_BUTTON_Y = 0.45;

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
     * Sprites of the background of the ChooseMode.
     */
    private ISprite background;
    /**
     * An ArrayList of all the buttons.
     */
    private final ArrayList<IButton> buttons = new ArrayList<>();

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
        serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);
        double y = (double) serviceLocator.getConstants().getGameHeight() - (double) bottomChooseModeScreen.getHeight();
        serviceLocator.getRenderer().drawSpriteHUD(this.bottomChooseModeScreen, 0, (int) y);
        buttons.forEach(IRenderable::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
    }

}
