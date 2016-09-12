package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Random;

public class StartBlock extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private HashSet<IGameObject> content = new HashSet<>();
    private int blockNumber;

    /* package */ StartBlock(IServiceLocator serviceLocator) {
        StartBlock.serviceLocator = serviceLocator;

        this.blockNumber = 0;

        createAndPlaceObjects();
    }

    @Override
    public void render() {
        for(IGameObject e : content){
            e.render();
        }
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void update() { }


    private void createAndPlaceObjects() {
        placePlatforms();
    }

    public HashSet<IGameObject> getContent() {
        return this.content;
    }

    @Override
    public void placePlatforms() {
        int max = (int)(Game.WIDTH + Game.HEIGHT)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT/platformAmount;
        System.out.println(Game.WIDTH/2 + " - " + (int) (Game.HEIGHT/1.2));
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH/2, (int) (Game.HEIGHT/1.2));
        content.add(platform);

        for (int i = 1; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            if(blockNumber == 0) {
                yLoc = (int) (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms));
            } else {
                yLoc = (int) (-Game.HEIGHT * (blockNumber -1) - (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));
            }

            int xLoc = (int) (widthDeviation * Game.WIDTH);
            platform = platformFactory.createPlatform(xLoc, yLoc);
            content.add(platform);
        }
    }

    @Override
    public void addYPos(double y){
        for(IGameObject e : content){
            e.addYPos(y);
        }
    }

}
