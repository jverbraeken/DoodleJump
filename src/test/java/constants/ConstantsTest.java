package constants;

import filesystem.IFileSystem;
import math.Calc;
import math.ICalc;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ConstantsTest {

    private IServiceLocator serviceLocator;
    private IConstants constants;

    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        IFileSystem fileSystem = mock(IFileSystem.class);
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("logFile", "async.log");
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(fileSystem.parseJsonMap("constants.json", String.class)).thenReturn(jsonObject);

        constants = Whitebox.invokeConstructor(Constants.class);
    }

    @Test
    public void registerTest() {
        //Constants.register(sl);

        if (constants != null) {
            System.out.print("jo");
        }
    }
}
