package constants;

import objects.blocks.BlockFactory;
import system.IServiceLocator;

public class Constants implements IConstants {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Constants.serviceLocator = serviceLocator;
        serviceLocator.provide(new Constants());
    }

    /**
     * The width of the frame of the game
     */
    private final static int WIDTH = 640;
    /**
     * The height of the frame of the game
     */
    private final static int HEIGHT = 960;
    /**
     * How much the doodle is affected by gravity.
     */
    private static final double gravityAcceleration = .5;
    /**
     * The height the dodle jumps will be multiplied with this value to obtain the score that the player will get
     * each frame.
     */
    private final static double scoreMultiplier = 0.15;


    /**
     * Prevent public instantiation of Constants.
     */
    private Constants() {
    }

    /** {@inheritDoc} */
    @Override
    public int getGameWidth() {
        return WIDTH;
    }

    /** {@inheritDoc} */
    @Override
    public int getGameHeight() {
        return HEIGHT;
    }

    /** {@inheritDoc} */
    @Override
    public double getGravityAcceleration() {
        return gravityAcceleration;
    }

    /** {@inheritDoc} */
    @Override
    public double getScoreMultiplier() {
        return scoreMultiplier;
    }

}
