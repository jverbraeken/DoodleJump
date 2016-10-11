package constants;

import filesystem.FileSystem;
import filesystem.IFileSystem;
import math.Calc;
import math.ICalc;
import objects.blocks.platform.Platform;
import org.codehaus.plexus.component.configurator.converters.basic.DoubleConverter;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.ServiceLocator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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
        when(fileSystem.parseJsonMap("constants.json", String.class)).thenReturn(jsonObject);

        Constants.register(serviceLocator);
        constants = Whitebox.invokeConstructor(Constants.class);
    }

    @Test
    public void getGameWidthTest() throws NoSuchFieldException, IllegalAccessException{
        Field field = Constants.class.getDeclaredField("WIDTH");
        field.setAccessible(true);
        assertThat(constants.getGameWidth(), is(field.get(constants)));
        assertThat(constants.getGameWidth(), instanceOf(Integer.class));
    }

    @Test
    public void getGameHeightTest() throws NoSuchFieldException, IllegalAccessException{
        Field field = Constants.class.getDeclaredField("HEIGHT");
        field.setAccessible(true);
        assertThat(constants.getGameHeight(), is(field.get(constants)));
        assertThat(constants.getGameHeight(), instanceOf(Integer.class));
    }

    @Test
    public void getGravityAccelerationTest() throws NoSuchFieldException, IllegalAccessException{
        Field field = Constants.class.getDeclaredField("GRAVITY_ACCELERATION");
        field.setAccessible(true);
        assertThat(constants.getGravityAcceleration(), is(field.get(constants)));
        assertThat(constants.getGravityAcceleration(), instanceOf(Double.class));
    }
}
