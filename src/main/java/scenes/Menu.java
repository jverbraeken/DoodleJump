package scenes;

import objects.IGameObject;
import objects.buttons.IButtonFactory;
import objects.buttons.PlayButton;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by joost on 6-9-16.
 */
public class Menu implements IScene {

    private IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();

    /* package */ Menu(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        PlayButton playButton = buttonFactory.getPlayButton();
        elements.add(playButton);
    }

    @Override
    public void start() { }

    @Override
    public void paint(Graphics g){
        for(IGameObject e : elements) {
            e.paint(g);
        }
    }

    @Override
    public void update() { }
}
