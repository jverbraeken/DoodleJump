package buttons;

import input.IInputManager;
import input.IMouseInputObserver;
import system.IRenderable;

/**
 * This class focuses on the implementation of button.
 */
public interface IButton extends IRenderable, IMouseInputObserver {

    /**
     * Registers its button to the {@link IInputManager input manager}.
     */
    void register();

    /**
     * Deregisters its button from the {@link IInputManager input manager}.
     */
    void deregister();
}
