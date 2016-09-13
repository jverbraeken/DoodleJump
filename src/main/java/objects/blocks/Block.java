package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import objects.IGameObject;

public class Block extends AGameObject implements IBlock {
    private static IServiceLocator serviceLocator;

    private ArrayList<IGameObject> content = new ArrayList<>();
    //private int blockNumber;

    /* package */ Block(IServiceLocator serviceLocator, IPlatform lastPlatform) {
        Block.serviceLocator = serviceLocator;

        //this.blockNumber = blockNumber;
        setYPos(lastPlatform.getYPos() + 800);
        System.out.println("new block, lastp:" + lastPlatform.getYPos());

        createAndPlaceObjects(lastPlatform);
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

    @Override
    public void addYPos(double y){
        double current = this.getYPos();
        this.setYPos(current + y);

        for(IGameObject e : content){
            e.addYPos(y);
        }
    }

    private void createAndPlaceObjects(IPlatform lastPlatform) {
        placePlatforms(lastPlatform);
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

        double t = World.vSpeedLimit/World.gravityAcceleration;

        int maxY = (int) (0.5 * World.gravityAcceleration * Math.pow(t,2));

        for (int i = 0; i < platformAmount; i++) {

            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat());

            int yLast = (int) lastPlatform.getYPos();
            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));


            if(yLoc < yLast - maxY){
                yLoc = yLast - maxY;
            }

            IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
            IPlatform platform = platformFactory.createPlatform(0, yLoc);

            int xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getWidth()));
            platform.setXPos(xLoc);
            content.add(platform);

            lastPlatform = platform;
        }

        this.setYPos(lastPlatform.getYPos());
    }


}
