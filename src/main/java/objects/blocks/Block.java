package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.Random;
import objects.IGameObject;

public class Block extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private ArrayList<IGameObject> content = new ArrayList<>();
    private int blockNumber;

    /* package */ Block(IServiceLocator serviceLocator, int blockNumber) {
        Block.serviceLocator = serviceLocator;

        this.blockNumber = blockNumber;

        createAndPlaceObjects();
    }

    @Override
    public void paint() {
        for(IGameObject e : content){
            e.paint();
        }
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }

    @Override
    public void addYPos(double y){
        for(IGameObject e : content){
            e.addYPos(y);}
    }

    private void createAndPlaceObjects() {
        placePlatforms();
    }

    public ArrayList<IGameObject> getContent() {
        return this.content;
    }

    private void placePlatforms() {
        int max = (int)(Game.WIDTH + Game.HEIGHT)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT/platformAmount;

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            if(blockNumber == 0) {
                yLoc = (int) (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms));
            } else {
                yLoc = (int) (-Game.HEIGHT * (blockNumber -1) - (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));
            }

            int xLoc = (int) (widthDeviation * Game.WIDTH);
            IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
            IPlatform platform = platformFactory.createPlatform(xLoc, yLoc);
            content.add(platform);
        }
    }

}
