package objects.blocks;

import objects.GameObject;
import objects.platform.Platform;
import system.IServiceLocator;
import system.ServiceLocator;

import java.util.ArrayList;
import java.util.Random;
import objects.IGameObject;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.platform.IPlatformFactory;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nick on 7-9-2016.
 */
public class Block extends GameObject implements IBlock, IGameObject {

    private final int jumpHeight = 100;
    private float height;
    private float width;
    private int blockNumber;
    private ArrayList<IGameObject> blockContent;
    private IServiceLocator serviceLocator;

    /**
     * Create a Block. This object will automatically create and place
     * all the object inside of it.
     * @param height - height of the screen
     * @param width - width of the screen
     * @param serviceLocator - the ServiceLocator of this game.
     * @param blockNumber - The number of the block.
     */
    public Block(float width, float height, IServiceLocator serviceLocator, int blockNumber) {
        this.height = height;
        this.width = width;
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

    private void placePlatforms() {
        //Get the diagnal length of the screen
        int max = (int)(width + height)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) height/platformAmount;

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            if(blockNumber == 0) {
                yLoc = (int) (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms));
            } else {
                yLoc = (int) (- height * (blockNumber -1) - (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));
            }

            int xLoc = (int) (widthDeviation * width);
            Platform p = new Platform(xLoc, yLoc);
            blockContent.add(p);
        }

    }

    @Override
    public void paint(Graphics g) {
        for(IGameObject e : blockContent){
            e.paint(g);
        }
    }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void move() { }

    //TODO: change to support correct implementation
    public void update() { }
}
