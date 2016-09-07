package objects.blocks;

import objects.GameObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nick on 7-9-2016.
 */
public class Block extends GameObject implements IBlock{
    private final int jumpHeight = 100;
    private float height;
    private float width;
    private ArrayList<ObjectLocation> blockContent;

    public Block(float height, float width ) {
        this.height = height;
        this.width = width;
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
        int max = (int)(width + height)/200;
        int min = 4;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;

        for (int i = 0; i < platformAmount; i++) {

        }

    }
    //TODO: change to use Graphics (swing?)
    public void paint() {

    }

    //TODO: change to support correct implementation
    public void animate() {

    }

    //TODO: change to support correct implementation
    public void move() {

    }

    //TODO: change to support correct implementation
    public void update() {

    }
}
