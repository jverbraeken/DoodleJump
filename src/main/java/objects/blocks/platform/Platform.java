package objects.blocks.platform;

import objects.AGameObject;
import system.Game;
import system.IServiceLocator;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    /* package */ Platform(IServiceLocator serviceLocator, int x, int y) {
        super();

        Platform.serviceLocator = serviceLocator;

        this.setXPos(x);
        //this.setXPos(.5* Game.WIDTH+30);
        this.setYPos(y);
        this.setHeight(20);
        this.setWidth(50);
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void paint() {
        serviceLocator.getRenderer().drawRectangle((int)this.getXPos(), (int)this.getYPos(), this.getWidth(), this.getHeight());
    }

    @Override
    public void update() { }

}
