package scenes;

import objects.IBlockFactory;
import objects.IGameObject;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import system.Game;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {

    private Set<IGameObject> elements;
    private IBlockFactory blockFactory;
    private IDoodle doodle;
    private double hSpeed;
    private final static double GRAVITY = .15;

    /* package */ World(IBlockFactory blockFactory, IDoodle doodle) {
        this.blockFactory = blockFactory;
        this.doodle = doodle;

        elements = new HashSet<>();
        hSpeed = 0;
        elements.add(blockFactory.newBlock(Game.height));
    }

    public void start() { }

    @Override
    public void paint(Graphics g) {
        for(IGameObject e : elements) {
            e.paint(g);
        }
        doodle.paint(g);
    }

    @Override
    public void update() {

        clearRedundantBlocks();

        if (!checkAlive()) {
            //TODO: implement Game Over
            //Game.endGame();
        }
        generateNewBlocks();

        updateSpeed();
    }

    public void generateNewBlocks(){
        if (elements.size() < 4) {
            double minY = Game.height;
            for (IGameObject e : elements) {
                if (e.getYPos() < minY) {
                    minY = e.getYPos();
                }
            }
            //TODO: implements New Block
            elements.add(blockFactory.newBlock(minY));
        }
    }

    public boolean checkAlive(){
        return doodle.isAlive();
    }

    public void clearRedundantBlocks(){
        for (IGameObject e : elements) {
           if (e.getYPos() > Game.height) {
                    elements.remove(e);
           }
        }
    }

    public void updateSpeed(){
        hSpeed = hSpeed + GRAVITY;

        if(this.hSpeed < 0 && doodle.getYPos() > (1/2d) * Game.height) {
            for(IGameObject e : elements){
                e.addYPos(hSpeed);
            }
        } else {
           doodle.addYPos(hSpeed);
        }

    }
}
