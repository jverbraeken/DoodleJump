package objects;

/**
 * Interface for jumpable GameObjects.
 */
public interface IJumpable extends IGameObject {

    /**
     * Get the boost of the jumpable object.
     *
     * @return A double representing the boost provided by the object.
     */
    double getBoost();

}
