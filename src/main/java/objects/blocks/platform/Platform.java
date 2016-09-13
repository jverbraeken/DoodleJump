package objects.blocks.platform;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;
    private ISprite sprite;

    /* package */ Platform(IServiceLocator serviceLocator, int x, int y) {
        super();

        Platform.serviceLocator = serviceLocator;

        this.setXPos(x);
        this.setYPos(y);
        this.sprite = serviceLocator.getSpriteFactory().getPlatformSprite1();
        this.setHeight(sprite.getHeight());
        this.setWidth(sprite.getWidth());
    }

    @Override
    public void animate() { }

    @Override
    public void move() { }

    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int)this.getXPos(), (int)this.getYPos());
    }

    @Override
    public void update() { }

}
