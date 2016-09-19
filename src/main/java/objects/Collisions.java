package objects;

import system.IServiceLocator;

public class Collisions implements ICollisions {

    private Collisions() {
    }

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Collisions());
    }

    public boolean collide(IGameObject a, IGameObject b) {
        double y = a.getYPos() + a.getHeight();
        if (y > b.getYPos() && y < (b.getYPos() + b.getHeight())) {
            double x = a.getXPos() + (a.getWidth() / 2);
            if (x > b.getXPos() && x < (b.getXPos() + b.getWidth())) {
                return true;
            }
        }

        return false;
    }

}