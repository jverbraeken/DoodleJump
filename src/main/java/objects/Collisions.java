package objects;

import system.IServiceLocator;

public class Collisions implements ICollisions {

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Collisions());
    }

    private Collisions() { }

    public boolean collide(IGameObject a, IGameObject b) {
        int y = a.getYPos() + a.getHeight();
        if(y > b.getYPos() && y < (b.getYPos() + b.getHeight())) {
            int x = a.getXPos() + (a.getWidth() / 2);
            if(x > b.getXPos() && x < (b.getXPos() + b.getWidth())) {
                return true;
            }
        }

        return false;
    }

}