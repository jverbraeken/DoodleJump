package objects.backgrounds;

import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;

import java.awt.*;

/**
 * Created by erico on 10-9-2016.
 */
public class BackgroundFactory implements IBackgroundFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BackgroundFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BackgroundFactory());
    }

    @Override
    public IBackground createStartMenuBackground() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Image background = spriteFactory.getStartMenuBackground();
        return new StartMenuBackground(background);
    }

}
