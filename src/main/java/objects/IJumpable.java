package objects;

/**
 * This class describes a jumpable object.
 */
public interface IJumpable extends IGameObject {

    /**
     * Get the boost provided by the jumpable.
     * @return The boost.
     */
    double getBoost();

}