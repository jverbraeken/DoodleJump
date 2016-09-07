package input;

import audio.IAudioManager;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import system.IServiceLocator;

public final class InputManager implements IInputManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new InputManager());
    }

    private InputManager() {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
