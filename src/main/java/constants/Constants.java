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
     * Prevent public instantiation of Constants.
     */
    private Constants() {
    }

    private final static int WIDTH = 640;
    private final static int HEIGHT = 960;

    @Override
    public int getGameWidth() {
        return WIDTH;
    }

    @Override
    public int getGameHeight() {
        return HEIGHT;
    }
}
