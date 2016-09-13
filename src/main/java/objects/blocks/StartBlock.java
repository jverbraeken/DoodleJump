package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class StartBlock extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private ArrayList<IGameObject> content = new ArrayList<>();
    private int blockNumber;

    /* package */ StartBlock(IServiceLocator serviceLocator) {
        StartBlock.serviceLocator = serviceLocator;

        this.blockNumber = 0;
        setYPos(0);

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
        placePlatforms(null);
    }

    public ArrayList<IGameObject> getContent() {
        return this.content;
    }

    @Override
    public void placePlatforms(IPlatform lastPlatform) {
        int max = (int)(Game.WIDTH + Game.HEIGHT)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT/platformAmount;

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH/2, (int) (Game.HEIGHT/1.2));
        lastPlatform = platform;
        content.add(platform);

        double t = World.vSpeedLimit/World.gravityAcceleration;

        int maxY = (int) (0.5 * World.gravityAcceleration * Math.pow(t,2));

        for (int i = 1; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);

            int yLast = (int) lastPlatform.getYPos();

            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

            if(yLoc < yLast - maxY){
                yLoc = yLast - maxY;
            }

            int xLoc = (int) (widthDeviation * Game.WIDTH);
            platform = platformFactory.createPlatform(xLoc, yLoc);
            content.add(platform);
            lastPlatform = platform;

        }
        this.setYPos(lastPlatform.getYPos());
    }

    @Override
    public void addYPos(double y){
        double current = this.getYPos();
        this.setYPos(current + y);

        for(IGameObject e : content){
            e.addYPos(y);
        }
    }

}
