package objects.buttons;

import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class ButtonFactory implements IButtonFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        ButtonFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new ButtonFactory());
    }

    private static final double playButtonWidthPercentage = 0.35;

    public PlayButton newPlayButton(int x, int y) {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ISprite buttonSprite = spriteFactory.getPlayButtonSprite();
        int width = (int) (Game.WIDTH * playButtonWidthPercentage);
        return new PlayButton(serviceLocator, x, y, width, (int) (width * buttonSprite.getRatio()), buttonSprite.getImage());
    }
}
