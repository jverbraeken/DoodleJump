package scenes;

import objects.blocks.IBlockFactory;
import objects.IGameObject;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import system.Game;
import system.IServiceLocator;

import java.awt.*;
import java.util.*;

public class World implements IScene {

    private final IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();
    private IDoodle doodle;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        for(int i = 0; i < 3; i++) {
            elements.add(blockFactory.createBlock());
        }

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.newDoodle();
    }

    @Override
    /** {@inheritDoc} */
    public void start() { }

    @Override
    /** {@inheritDoc} */
    public void stop() { }

    @Override
    public void paint() {
        for(IGameObject e : elements) {
            e.paint();
        }

        this.doodle.paint();
    }

    @Override
    public void update(double delta) {
        for(IGameObject e : elements) {
            if(e.getClass().equals(Doodle.class)){
                if(e.getYPos() > Game.HEIGHT) {
                    elements.remove(e);
                }
            }
        }

        if (!elements.contains(doodle)){
            //TODO: implement Game Over
            //Game.endGame();
        }

        if (elements.size() < 4) {
            double minY = Double.MAX_VALUE;
            for(IGameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock());
        }
    }
}
