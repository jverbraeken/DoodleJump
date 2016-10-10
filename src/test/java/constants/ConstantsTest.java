package constants;

import filesystem.IFileSystem;
import math.Calc;
import math.ICalc;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ConstantsTest {

    private IServiceLocator sL;
    private IConstants constants;

    @Before
    public void init() throws Exception {
        IServiceLocator sl = mock(IServiceLocator.class);
        IFileSystem fileSystem = mock(IFileSystem.class);
        Map<String, String> jsonObject = new HashMap<String, String>();
        jsonObject.put("logFile", "async.log");
        when(sl.getFileSystem()).thenReturn(fileSystem);
        when(fileSystem.parseJsonMap("constants.json", String.class)).thenReturn(jsonObject);
        Constants.register(sl);
        constants = Whitebox.invokeConstructor(Constants.class);
    }

    @Test
    public void registerTest() {
        IServiceLocator sl = mock(IServiceLocator.class);
        Constants.register(sl);

        if (sl.getConstants() != null) {
            System.out.print("jo");
        }
    }
}
