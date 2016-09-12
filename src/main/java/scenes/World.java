package scenes;

import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.IGameObject;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import rendering.IDrawable;
import system.Game;
import system.IServiceLocator;

import java.util.*;

public class World implements IScene {

    private final IServiceLocator serviceLocator;
    private Set<IGameObject> elements = new HashSet<>();
    private final IDrawable background;

    private IDoodle doodle;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        elements.add(blockFactory.createStartBlock());
        for(int i = 1; i < 3; i++) {
            elements.add(blockFactory.createBlock());
        }

        background = serviceLocator.getBackgroundFactory().createBackground();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
    }

    @Override
    /** {@inheritDoc} */
    public void start() { }

    @Override
    /** {@inheritDoc} */
    public void stop() { }

    @Override
    public void paint() {
        background.paint();

        for(IGameObject e : elements) {
            e.paint();

            IBlock block = (IBlock) e;
            HashSet<IGameObject> platforms = block.getContent();
            for(IGameObject platform : platforms) {
                this.doodle.collide(platform);
            }
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
