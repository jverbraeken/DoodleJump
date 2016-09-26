package objects.enemies;

import logging.ILogger;
import objects.IGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Standard implementation of the EnemyBuilder. Used to generate enemies.
 */
public final class EnemyBuilder implements IEnemyBuilder {

    /**
     * The logger for the EnemyBuilder class.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        EnemyBuilder.sL = sL;
        EnemyBuilder.sL.provide(new EnemyBuilder());
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private EnemyBuilder() {
        LOGGER = sL.getLoggerFactory().createLogger(EnemyBuilder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createEnemy(final int x, final int y, final ISprite sprite) {
        LOGGER.info("A new Enemy has been created: x = " + x + ", y = " + y + " sprite = " + sprite.toString());
        return new Enemy(x, y, sprite);
    }

}
