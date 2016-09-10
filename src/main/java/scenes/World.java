package scenes;

import objects.BlockFactory;
import objects.IBlockFactory;
import objects.IGameObject;
import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import objects.platform.IPlatform;
import objects.platform.Platform;
import objects.platform.PlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.awt.*;
import java.util.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {
    private static transient IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();
    private IDoodle doodle;
    private int width = Game.width;
    private int height = Game.height;
    private double hSpeed;
    private final static double GRAVITY = .15;
    private IBlockFactory blockFactory;

    public int determinePlatformX(){
        Random rand = new Random();
        float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
        int xLoc = (int) (widthDeviation * width);

        return xLoc;

    }

    public int determinePlatformY(int platformNumber){
        Random rand = new Random();
        float heightDeviation = (float) (rand.nextFloat() - 0.5);
        return (int) (height + (-platformNumber * 75 + heightDeviation * 50));
    }

    /* package */ World(IBlockFactory blockFactory, IDoodle doodle) {
        this.blockFactory = blockFactory;
        this.doodle = doodle;

        elements = new HashSet<>();
        hSpeed = 0;
        elements.add(blockFactory.createBlock());
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

        checkDoodleCollision();

        updateSpeed();
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
        if(this.hSpeed < 0 && doodle.getYPos() < 0.5 * Game.height) {
            System.out.println("H" + height);
            for(IGameObject e : elements){
                e.addYPos(-hSpeed);
            }
        } else {
           doodle.addYPos(hSpeed);
        }

    }
    public void generateNewBlocks() {
        if (elements.size() < 4) {
            double minY = Game.height;
            for (IGameObject e : elements) {
                if (e.getYPos() < minY) {
                    minY = e.getYPos();
                    //TODO: new blocks
                }
            }
        }
    }

    private void checkDoodleCollision(){
        System.out.println("Dooble" + doodle.getXPos() + " " + doodle.getYPos() + " " + doodle.getWidth() + " " + doodle.getHeight());

        for(IGameObject e : elements){
            System.out.println(e.getXPos() + " " + e.getWidth());
            if(doodle.collide(e)){

                System.out.println("A");
                if(e.getClass().equals(Block.class)) {
                    System.out.println("B");
                    IBlock f = (IBlock) e;
                    for(IGameObject g : f.getElements()){
                        System.out.println("C" + g.getXPos() + " " + g.getYPos() + " " + g.getWidth() + " " + g.getHeight());
                        if(doodle.collide(g)){
                            System.out.println("HIT");
                            if(hSpeed > 0){
                            hSpeed = -8;}
                        }
                    }
                }
            }
        }
    }

}
