package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class CameraTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ICamera camera;

    @Before
    public void init() {
        camera = new DoodleCamera(serviceLocator, doodle);
    }

    @Test
    public void testGetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(DoodleCamera.class, "y");

        ((AtomicDouble) y).set(2d);
        assertThat(camera.getYPos(), is(2d));

        ((AtomicDouble) y).set(3d);
        assertThat(camera.getYPos(), is(3d));
    }

    @Test
    public void testSetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(DoodleCamera.class, "y");

        camera.setYPos(2d);
        assertThat(((AtomicDouble) y).get(), is(2d));

        camera.setYPos(3d);
        assertThat(((AtomicDouble) y).get(), is(3d));
    }
}
