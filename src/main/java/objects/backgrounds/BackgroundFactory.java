package objects.backgrounds;

import rendering.IDrawable;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

public class BackgroundFactory implements IBackgroundFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BackgroundFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BackgroundFactory());
    }

    @Override
    public IDrawable createBackground() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite background = spriteFactory.getStartMenuBackground();
        return () -> serviceLocator.getRenderer().drawSprite(background, 0, 0);
    }

}
