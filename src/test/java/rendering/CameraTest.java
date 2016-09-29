package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CameraTest {
    private ICamera camera;

    @Before
    public void init() {
        camera = new Camera();
    }

    @Test
    public void testGetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(Camera.class, "y");

        ((AtomicDouble) y).set(2d);
        assertThat(camera.getYPos(), is(2d));

        ((AtomicDouble) y).set(3d);
        assertThat(camera.getYPos(), is(3d));
    }

    @Test
    public void testSetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(Camera.class, "y");

        camera.setYPos(2d);
        assertThat(((AtomicDouble) y).get(), is(2d));

        camera.setYPos(3d);
        assertThat(((AtomicDouble) y).get(), is(3d));
    }
}
