package objects.doodles;

import logging.ILogger;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.IServiceLocator;

/**
 * This is a factory creating all doodles.
 */
public final class DoodleFactory implements IDoodleFactory {

    /**
     * Initial vertical speed for the Doodle.
     */
    private static final int DOODLE_INITIAL_SPEED = -9;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Logger for the DoodleFactory.
     */
    private ILogger logger;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private DoodleFactory() {
        this.logger = DoodleFactory.serviceLocator.getLoggerFactory().createLogger(DoodleFactory.class);
    }

    /**
     * Register the doodle factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        DoodleFactory.serviceLocator = sL;
        DoodleFactory.serviceLocator.provide(new DoodleFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createDoodle(final World world, final DoodleColors color) {
        this.logger.info("A new Doodle has been created");
        IDoodle doodle = new Doodle(DoodleFactory.serviceLocator, this.getSprites(color), world);
        doodle.setVerticalSpeed(DoodleFactory.DOODLE_INITIAL_SPEED);
        return doodle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createStartScreenDoodle() {
        this.logger.info("A new StartScreenDoodle has been created");

        ISpriteFactory spriteFactory = DoodleFactory.serviceLocator.getSpriteFactory();
        ISprite[] sprites = spriteFactory.getGreenDoodleSprites();
        return new StartScreenDoodle(sprites, DoodleFactory.serviceLocator);
    }

    /**
     * Get the correct sprites for the Doodle given its colors.
     *
     * @param   color           The color of the Doodle.
     * @return                  An array of sprites.
     */
    private ISprite[] getSprites(final DoodleColors color) {
        ISpriteFactory spriteFactory = DoodleFactory.serviceLocator.getSpriteFactory();
        switch (color) {
            case blue:
                return spriteFactory.getBlueDoodleSprites();
            case green:
                return spriteFactory.getGreenDoodleSprites();
            case red:
                return spriteFactory.getRedDoodleSprites();
            default:
                return spriteFactory.getGreenDoodleSprites();

        }
    }

}
