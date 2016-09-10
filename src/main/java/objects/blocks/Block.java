package objects.blocks;

import objects.GameObject;
import objects.platform.IPlatform;
import objects.platform.Platform;
import system.IServiceLocator;
import system.ServiceLocator;

import java.util.*;

import objects.IGameObject;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.platform.IPlatformFactory;
import system.Game;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public class Block extends GameObject implements IBlock, IGameObject {

    private final int jumpHeight = 100;
    private int blockNumber;
    private ArrayList<IGameObject> blockContent;
    private Set<IGameObject> elements = new HashSet<>();;
    private IServiceLocator serviceLocator;

    /**
     * Create a Block. This object will automatically create and place
     * all the object inside of it.
     * @param height - height of the screen
     * @param width - width of the screen
     * @param serviceLocator - the ServiceLocator of this game.
     * @param blockNumber - The number of the block.
     */
    public Block(double width, double height, IServiceLocator serviceLocator, int blockNumber) {
        super.setWidth(100);
        super.setHeight(100);
        super.setHeight(height);
        super.setWidth(width);
        this.serviceLocator = serviceLocator;
        this.blockNumber = blockNumber;
        blockContent = new ArrayList<IGameObject>();
        createAndPlaceObjects();
    }

    /**
     * The Block contains objects, for example Platforms. Here
     * these objects are created and their locations ar initiated.
     */
    private void createAndPlaceObjects() {
        placePlatforms();
    }
    //TODO: make new block with parameters
    public Block(double maxY) {
        elements = new TreeSet<IGameObject>();
        this.setYPos(maxY - 100);
    }

    private void placePlatforms() {
        //Get the diagonal length of the screen
        int max = (int)(getWidth() + getHeight())/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) getHeight()/platformAmount;

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.0 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            if(blockNumber == 0) {
                yLoc = (int) (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms));
            } else {
                yLoc = (int) (- getHeight() * (blockNumber -1) - (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));
            }

            int xLoc = (int) (widthDeviation * getWidth());
            IPlatform p = new Platform(xLoc, yLoc);
            elements.add(p);
        }

    }

    @Override
    public void paint(Graphics g) {
        for(IGameObject e : elements){
            e.paint(g);
        }
        g.drawRect((int)getXPos(), (int)getYPos(), Game.width, 100);
    }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void move() { }

    //TODO: change to support correct implementation
    public void update() { }

    public Set<IGameObject> getElements(){
        return elements;
    }

    /**
     * Add a value to the Y position.
     *
     * @param   extra    The value to add.
     */
    @Override
    public void addYPos(double extra) {
        double current = this.getYPos();
        this.setYPos(current + extra);
        for(IGameObject e : elements){
            e.addYPos(extra);
        }
    }
}
