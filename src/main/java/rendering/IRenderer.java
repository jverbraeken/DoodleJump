package rendering;

import scenes.IScene;

/**
 * Created by joost on 6-9-16.
 */
public interface IRenderer {

    /**
     * Sets the current scene to currentScene
     * @param currentScene The new scene that must be visible to the user. Cannot be null
     */
    void setScene(IScene currentScene);
    void start();
}
