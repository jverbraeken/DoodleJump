package scenes;

import objects.BlockFactory;
import objects.GameObject;
import objects.IBlockFactory;
import objects.IGameObject;
import objects.doodles.Doodle;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {

    private IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();
    private IDoodle doodle;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        for(int i = 0; i < 3; i++) {
            elements.add(blockFactory.createBlock());
        }
    }

    @Override
    public void start() { }

    @Override
    public void paint(Graphics g) {
        for(IGameObject e : elements) {
            e.paint(g);
        }
    }

    @Override
    public void update() {
        for(IGameObject e : elements) {
            if(e.getClass().equals(Doodle.class)){
                if(e.getYPos() > Game.height) {
                    elements.remove(e);
                }
            }
        }

        if(!elements.contains(doodle)){
            //TODO: implement Game Over
            //Game.endGame();
        }

        if(elements.size() < 4) {
            double minY = Double.MAX_VALUE;
            for(IGameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock());
        }
    }
}
