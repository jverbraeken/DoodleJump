package scenes;

import objects.BlockFactory;
import objects.GameObject;
import objects.IBlockFactory;
import objects.IGameObject;
import objects.doodles.Doodle;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import objects.platform.IPlatform;
import objects.platform.Platform;
import objects.platform.PlatformFactory;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by joost on 6-9-16.
 */
public class World implements IScene {
    private static transient IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();
    private IDoodle doodle;
    private int width = Game.width;
    private int height = Game.height;

    /* package */ World(IServiceLocator serviceLocator) {
        World.serviceLocator = serviceLocator;
        this.init();
        //TODO: implements getDoodle();
    }

    public void init() {

        for (int i = 0; i < 30; i ++){
            IPlatform platform = serviceLocator.getPlatformFactory().createPlatform(determinePlatformX(), determinePlatformY(i));
            elements.add(platform);
        }
//        for(int i = 0; i < 3; i++) {
//            //TODO: implement getBlock();
//            elements.add(blockFactory.createBlock());
//        }
    }

    public int determinePlatformX(){
        Random rand = new Random();
        float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
        int xLoc = (int) (widthDeviation * width);

        return xLoc;

    }

    public int determinePlatformY(int platformNumber){
        Random rand = new Random();
        float heightDeviation = (float) (rand.nextFloat() - 0.5);
        return (int) (platformNumber * 75 + heightDeviation * 50);
    }

    public void start() {

    }

    @Override
    public void paint(Graphics g) {
        for(IGameObject e : elements) {
            e.paint(g);
        }
    }

    @Override
    public void update() {
        for(IGameObject e : elements) {
            if(e.getClass().equals(Doodle.class)){
                if(e.getYPos() > Game.height) {
                    elements.remove(e);
                }
            }
        }

        if(!elements.contains(doodle)){
            //TODO: implement Game Over
            //Game.endGame();
        }

        if(elements.size() < 30) {
            double minY = Double.MAX_VALUE;
            for(IGameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            //TODO: implements new platform
            IPlatform platform = serviceLocator.getPlatformFactory().createPlatform(determinePlatformX(),
                    determinePlatformY(elements.size()));
            elements.add(platform);
        }
    }
}
