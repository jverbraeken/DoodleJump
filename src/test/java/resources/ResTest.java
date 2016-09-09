package resources;

import filesystem.FileSystem;
import filesystem.IFileSystem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Multiple print statements are used. The reason we chose to use ordinary statements instead of a file logger is
 * that from the terminal an error can be traced immediately.
 */
@RunWith(PowerMockRunner.class)
public class ResTest {
    private static Res res;
    private static FileSystem fileSystem;

    @BeforeClass
    public static void initialize() throws Exception {
        res = Whitebox.invokeConstructor(Res.class);
        fileSystem = Whitebox.invokeConstructor(FileSystem.class);
    }

    @Test
    public void testSpritesValid() throws FileNotFoundException {
        System.out.println("\ntestSpritesValid\n");
        Map<IRes.sprites, String> map = Whitebox.getInternalState(res, "sprites");
        ClassLoader classLoader = IFileSystem.class.getClassLoader();
        for (Map.Entry<IRes.sprites, String> entry : map.entrySet()) {
            System.out.print(entry.getValue() + " - ");
            URL url = classLoader.getResource(entry.getValue());
            assertNotNull(url);
            System.out.println("done");
        }
    }

    @Test
    public void testAllSpritesImplemented() {
        System.out.println("\ntestAllSpritesImplemented\n");
        for (IRes.sprites sprite : IRes.sprites.values()) {
            System.out.print(String.valueOf(sprite) + " - ");
            assertNotNull(res.getSpritePath(sprite));
            System.out.println("done");
        }
    }
}
