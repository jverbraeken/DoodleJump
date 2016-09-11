package objects;

import system.IServiceLocator;

public class Collisions implements ICollisions {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Collisions.serviceLocator = serviceLocator;
        serviceLocator.provide(new Collisions());
    }

    private Collisions() { }

    public boolean collide(IGameObject a, IGameObject b) {
        if(a.getYPos() == b.getYPos()) {
            return true;
        }

        return false;
    }

}