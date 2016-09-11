package objects.backgrounds;

import rendering.IDrawable;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;

import java.awt.*;

public class BackgroundFactory implements IBackgroundFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BackgroundFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BackgroundFactory());
    }

    @Override
    public IDrawable createStartMenuBackground() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Image background = spriteFactory.getStartMenuBackground().getImage();
        return () -> serviceLocator.getRenderer().drawImage(background, 0, 0);
    }

}
