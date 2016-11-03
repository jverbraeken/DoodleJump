package filesystem;

import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class FileSystemTest {

    private static FileSystem fileSystem;
    private static final String NOT_EXISTING_NAME = "qwewrteryuifdhgxfg";

    @BeforeClass
    public static void initialize() throws Exception {
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any(Class.class))).thenReturn(mock(ILogger.class));

        IServiceLocator sL = mock(IServiceLocator.class);
        when(sL.getLoggerFactory()).thenReturn(loggerFactory);

        FileSystem.register(sL);

        fileSystem = Whitebox.invokeConstructor(FileSystem.class);

        (new File(NOT_EXISTING_NAME)).delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadResourceFileInvalidFile() throws FileNotFoundException {
        fileSystem.readResourceFile(NOT_EXISTING_NAME);
    }

    @Test
    public void testReadProjectFileInvalidFile() throws FileNotFoundException {
        fileSystem.readProjectFile(NOT_EXISTING_NAME);
        assertThat((new File(NOT_EXISTING_NAME)).exists(), is(true));
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadBinaryFileInvalidFile() throws FileNotFoundException {
        fileSystem.readBinaryFile(NOT_EXISTING_NAME);
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadImageInvalidFile() throws FileNotFoundException {
        fileSystem.readImage(NOT_EXISTING_NAME);
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadSoundInvalidFile() throws FileNotFoundException {
        fileSystem.readSound(NOT_EXISTING_NAME);
    }

    @Test(expected = FileNotFoundException.class)
    public void testWriteResourceFileInvalidFile() throws FileNotFoundException {
        fileSystem.writeResourceFile(NOT_EXISTING_NAME, "foo");
    }

    @Test
    public void testDeleteFile() throws IOException {
        final File file = ((new File(NOT_EXISTING_NAME)));
        file.createNewFile();
        fileSystem.deleteFile(NOT_EXISTING_NAME);
        assertThat(file.exists(), is(false));
    }

    @Test
    public void testClearFile() throws IOException {
        fileSystem.writeProjectFile(NOT_EXISTING_NAME, "foo");
        fileSystem.clearFile(NOT_EXISTING_NAME);
        assertThat(new BufferedReader(new FileReader(NOT_EXISTING_NAME)).readLine(), is(nullValue()));
    }

    @Test
    public void testLog() throws IOException {
        final Writer logWriter = mock(Writer.class);
        Whitebox.setInternalState(fileSystem, "logWriter", logWriter);
        fileSystem.log("foo");
        verify(logWriter).write("foo\n");
    }

    @Test
    public void testGetFont() {
        final Font result = fileSystem.getFont(NOT_EXISTING_NAME);
        assertThat(result, is(instanceOf(Font.class)));
    }

    @Test
    public void testWriteProjectFileInvalidFile() throws IOException {
        fileSystem.writeProjectFile(NOT_EXISTING_NAME, "foo");
        assertThat((new File(NOT_EXISTING_NAME)).exists(), is(true));
    }
}
