package filesystem;

import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.io.FileNotFoundException;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FileSystemTest {
    private static FileSystem fileSystem;

    @BeforeClass
    public static void initialize() throws Exception {
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any(Class.class))).thenReturn(mock(ILogger.class));

        IServiceLocator sL = mock(IServiceLocator.class);
        when(sL.getLoggerFactory()).thenReturn(loggerFactory);

        FileSystem.register(sL);

        fileSystem = Whitebox.invokeConstructor(FileSystem.class);
    }

    @Test
    public void testGetFileValid() throws FileNotFoundException {
        fileSystem.getResourceFile("foo.bar");
    }

    @Test(expected=FileNotFoundException.class)
    public void testGetFileInvalid() throws FileNotFoundException {
        fileSystem.getResourceFile("qwertyuiopasdfghjklzxcvbnm");
    }
}
