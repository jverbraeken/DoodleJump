package objects.blocks;

import objects.GameObject;
import objects.IGameObject;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.platform.IPlatformFactory;
import system.Game;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nick on 7-9-2016.
 */
public class Block extends GameObject implements IBlock{

    private Set<IGameObject> elements;

    //TODO: make new block with parameters
    public Block(double maxY) {
        elements = new TreeSet<IGameObject>();
        this.setYPos(maxY - 100);
    }
    //TODO: change to use Graphics (swing?)
    @Override
    public void paint(Graphics g) {
        for(IGameObject e : elements){
            e.paint(g);
        }
        g.drawRect((int)getXPos(), (int)getYPos(), Game.width, 100);
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
