package buttons;

import input.IMouseInputObserver;
import system.IRenderable;

/**
 * This class focuses on the implementation of button.
 */
public interface IButton extends IRenderable, IMouseInputObserver {

    /**
     * Registers its button to the {@link input.IInputManager input manager}.
     */
    void register();

    /**
     * Deregisters its button from the {@link input.IInputManager input manager}.
     */
    void deregister();

}
