package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import objects.powerups.ITrampoline;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Random;
import objects.IGameObject;

public class Block extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private HashSet<IGameObject> content = new HashSet<>();
    //private int blockNumber;

    /* package */ Block(IServiceLocator serviceLocator, double lastPlatformHeight) {
        Block.serviceLocator = serviceLocator;

        //this.blockNumber = blockNumber;
        setYPos(lastPlatformHeight - 800);

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

    @Override
    public void addYPos(double y){
        double current = this.getYPos();
        this.setYPos(current + y);

        for(IGameObject e : content){
            e.addYPos(y);
        }
    }

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

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            yLoc = (int) (getYPos() + (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));


            IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
            IPlatform platform = platformFactory.createPlatform(0, yLoc);

            int xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getWidth()));
            platform.setXPos(xLoc);
            content.add(platform);

            //TODO: Move powerup generation somewhere else
            //TODO: User better calculations
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            IGameObject trampoline = powerupFactory.createTrampoline(xLoc + 20, yLoc - platform.getHeight() + 5);
            content.add(trampoline);
        }
    }

}
