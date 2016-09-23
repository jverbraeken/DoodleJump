package resources.audio;

import filesystem.FileSystem;
import filesystem.IFileSystem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import resources.Res;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
public class AudioManagerTest {
    private static AudioManager audioManager;

    @BeforeClass
    public static void initialize() throws Exception {
        //audioManager = Whitebox.invokeConstructor(AudioManager.class);
    }

    @Test
    public void test() {
        // This class does not contain any methods that are reasonable to test
    }
}