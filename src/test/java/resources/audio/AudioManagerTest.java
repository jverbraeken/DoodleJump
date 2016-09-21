package resources.audio;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class AudioManagerTest {
    private static AudioManager audioManager;

    @BeforeClass
    public static void initialize() throws Exception {
        audioManager = Whitebox.invokeConstructor(AudioManager.class);
    }

    @Test
    public void test() {
        // This class does not contain any methods that are reasonable to test
    }
}
