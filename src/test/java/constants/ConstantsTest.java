package constants;

import filesystem.FileSystem;
import filesystem.IFileSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileSystem.class)
public class ConstantsTest {

    private IServiceLocator serviceLocator;
    private IConstants constants;

    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        IFileSystem fileSystem = mock(FileSystem.class);
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("logFile", "async.log");
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(fileSystem.parseJson("constants.json", Map.class)).thenReturn(jsonObject);

        Constants.register(serviceLocator);
        constants = Whitebox.invokeConstructor(Constants.class);
    }

    @Test
    public void getGameWidthTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("WIDTH");
        field.setAccessible(true);
        assertThat(constants.getGameWidth(), is(field.get(constants)));
        assertThat(constants.getGameWidth(), instanceOf(Integer.class));
    }

    @Test
    public void getGameHeightTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("HEIGHT");
        field.setAccessible(true);
        assertThat(constants.getGameHeight(), is(field.get(constants)));
        assertThat(constants.getGameHeight(), instanceOf(Integer.class));
    }

    @Test
    public void getGravityAccelerationTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("GRAVITY_ACCELERATION");
        field.setAccessible(true);
        assertThat(constants.getGravityAcceleration(), is(field.get(constants)));
        assertThat(constants.getGravityAcceleration(), instanceOf(Double.class));
    }

    @Test
    public void getScoreMultiplierTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("SCORE_MULTIPLIER");
        field.setAccessible(true);
        assertThat(constants.getScoreMultiplier(), is(field.get(constants)));
        assertThat(constants.getScoreMultiplier(), instanceOf(Double.class));
    }

    @Test
    public void getLogPendingTasksTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("LOG_PENDING_TASKS");
        field.setAccessible(true);
        assertThat(constants.getLogPendingTasks(), is(field.get(constants)));
        assertThat(constants.getLogPendingTasks(), instanceOf(Boolean.class));
    }

    @Test
    public void getLogFileTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("logFile");
        field.setAccessible(true);
        AtomicReference<String> logFile = (AtomicReference<String>) field.get(constants);
        assertThat(constants.getLogFile(), is(logFile.toString()));
        assertThat(constants.getLogFile(), instanceOf(String.class));
    }

    @Test
    public void getSaveFilePathTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Constants.class.getDeclaredField("SAVEFILE_DATA");
        field.setAccessible(true);
        assertThat(constants.getSaveFilePath(), is(field.get(constants)));
        assertThat(constants.getSaveFilePath(), instanceOf(String.class));
    }

    @Test
    public void interpretJsonNormalTest() throws Exception {
        Map<String, String> jsonObject = new HashMap<>();
        String logFileName = "foobar";
        jsonObject.put("logFile", logFileName);

        Whitebox.invokeMethod(constants, jsonObject);

        assertThat(constants.getLogFile(), is(logFileName));
    }

    @Test
    public void interpretJsonDefaultCaseTest() throws Exception {
        String logFileName = ((AtomicReference<String>) Whitebox.getInternalState(Constants.class, "logFile")).get();
        assertThat(constants.getLogFile(), is(logFileName));

        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("a", "b");

        Whitebox.invokeMethod(constants, jsonObject);

        assertThat(constants.getLogFile(), is(logFileName));
        assertThat(constants.getLogFile(), not("b"));
    }
}
