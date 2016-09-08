package scenes;

import objects.BlockFactory;
import objects.GameObject;
import objects.IBlockFactory;
import objects.doodles.Doodle;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodleFactory;
import system.Game;

import javax.swing.*;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {

    private Set<GameObject> elements;
    private IBlockFactory blockFactory;
    private Doodle doodle;
    /* package */ World(IBlockFactory blockFactory, IDoodleFactory doodleFactory) {
        this.blockFactory = blockFactory;
        elements = new TreeSet<GameObject>();
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
    public void paint() {
        for(GameObject e : elements) {
            e.paint();
        }
    }

    @Override
    public void update() {
        for(GameObject e : elements) {
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
            for(GameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            //TODO: implements New Block
            //elements.add(BlockFactory.newBlock(minY);
        }
    }
}
