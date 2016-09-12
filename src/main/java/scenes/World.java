package scenes;

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

    // The vertical speed, negative if going up and positive if going down.
    private double vSpeed = -9;
    // The fastest the doodle can go vertically.
    private double vSpeedLimit = 9;
    // How much the doodle is affected by gravity.
    private double gravityAcceleration = .15;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        IBlock lastCreatedBlock = blockFactory.createStartBlock();
        elements.add(lastCreatedBlock);

        for(int i = 1; i < 3; i++) {
            lastCreatedBlock = blockFactory.createBlock(lastCreatedBlock.getYPos());
            elements.add(lastCreatedBlock);
        }

        background = serviceLocator.getBackgroundFactory().createBackground();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.vSpeed = -9;
    }

    @Override
    /** {@inheritDoc} */
    public void start() { }

    @Override
    /** {@inheritDoc} */
    public void stop() { }

    @Override
    public void paint() {
        background.render();

        for(IGameObject e : elements) {
            e.render();
        }

        this.doodle.render();
    }

    @Override
    public void update(double delta) {

        updateObjects();
        updateSpeed();
        applySpeed();
        cleanUp();

        if (!elements.contains(doodle)){
            //TODO: implement Game Over
            //Game.endGame();
        }

        newBlocks();
    }

    public void updateSpeed(){
        for(IGameObject e : elements) {
            IBlock block = (IBlock) e;
            HashSet<IGameObject> inside = block.getContent();
            for (IGameObject item : inside) {
                if (vSpeed > 0 && this.doodle.collide(item)) {
                    vSpeed = -vSpeedLimit;
                }
            }
        }
        this.applyGravity();
    }

    public void applySpeed(){
        if(this.vSpeed < 0 && doodle.getYPos() < .5d * Game.HEIGHT) {
            for(IGameObject e : elements)
            e.addYPos(-this.vSpeed);
        } else {
            doodle.addYPos(this.vSpeed);
        }
    }

    /**
     * Applies gravity vAcceleration to the doodle.
     */
    private void applyGravity() {
        if(this.vSpeed >= -vSpeedLimit) {
            this.vSpeed += this.gravityAcceleration;
        }
    }

    public void updateObjects(){
        for(IGameObject e: elements){
            e.update();
        }
        doodle.update();
    }

    public void cleanUp(){
        HashSet<IGameObject> toRemove = new HashSet<>();
        for(IGameObject e : elements) {
            if(e.getClass().equals(Doodle.class)){
                System.out.println("Dooodle " + e.getYPos());
                if(e.getYPos() > Game.HEIGHT) {
                    toRemove.add(e);
                    elements.remove(e);
                }
            }
            else if (e instanceof IBlock){
                if(e.getYPos() > Game.HEIGHT) {
                    toRemove.add(e);
                }
            }
        }
        for(IGameObject e : toRemove) {
            elements.remove(e);
        }
    }

    public void newBlocks(){
        if (elements.size() < 3) {
            double minY = Double.MAX_VALUE;
            for(IGameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            IBlock topBlock = getTopBlock();
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock(topBlock.getYPos()));
        }
    }

    private IBlock getTopBlock() {
        IBlock topBlock = (IBlock) elements.iterator().next();
        for (IGameObject e : elements) {
            if (e.getYPos() < topBlock.getYPos()) {
                topBlock = (IBlock) e;
            }
        }
        return topBlock;
    }
}
