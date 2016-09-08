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

import javax.swing.*;
import java.awt.*;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {

    private Set<IGameObject> elements;
    private IBlockFactory blockFactory;
    private IDoodle doodle;
    /* package */ World(IBlockFactory blockFactory, IDoodleFactory doodleFactory) {
        this.blockFactory = blockFactory;
        elements = new TreeSet<IGameObject>();
        //TODO: implements getDoodle();
        //Doodle doodle = doodleFactory.getDoodle();
        //elements.add(doodle));
        for(int i = 0; i < 3; i++) {
            //TODO: implement getBlock();
            //elements.add(blockFactory.getBlock());
        }
    }

    public void start() {

    }

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
            elements.add(blockFactory.createBlock());
        }
    }
}
