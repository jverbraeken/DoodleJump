package input;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static junit.framework.TestCase.assertTrue;

public class KeyCodeTest {

    @Test
    public void test() throws Exception {
        Whitebox.invokeConstructor(KeyCode.class);
        assertTrue(true); // No crash
    }
}
