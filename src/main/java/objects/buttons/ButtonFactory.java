package objects.buttons;

import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by erico on 10-9-2016.
 */
public class ButtonFactory implements IButtonFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        ButtonFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new ButtonFactory());
    }

    public PlayButton createPlayButton(int x, int y) {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Image buttonImage = spriteFactory.getPlayButton();
        return new PlayButton(x, y, buttonImage);
    }

}
