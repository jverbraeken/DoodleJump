package filesystem;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.FileNotFoundException;

@RunWith(PowerMockRunner.class)
public class FileSystemTest {
    private static FileSystem fileSystem;

    @BeforeClass
    public static void initialize() throws Exception {
        fileSystem = Whitebox.invokeConstructor(FileSystem.class);
    }

    @Test
    public void testGetFileValid() throws FileNotFoundException {
        fileSystem.getFile("foo.bar");
    }

    @Test(expected=FileNotFoundException.class)
    public void testGetFileInvalid() throws FileNotFoundException {
        fileSystem.getFile("qwertyuiopasdfghjklzxcvbnm");
    }
}
