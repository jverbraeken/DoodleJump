package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StaticCameraTest {

    private ICamera camera;

    @Before
    public void init() {
        camera = new StaticCamera();

        Object y = Whitebox.getInternalState(StaticCamera.class, "y");
        ((AtomicDouble) y).set(0d);
    }

    @Test
    public void testGetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(StaticCamera.class, "y");

        ((AtomicDouble) y).set(2d);
        assertThat(camera.getYPos(), is(2d));

        ((AtomicDouble) y).set(3d);
        assertThat(camera.getYPos(), is(3d));
    }

    @Test
    public void testSetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(StaticCamera.class, "y");

        camera.setYPos(2d);
        assertThat(((AtomicDouble) y).get(), is(0d));

        camera.setYPos(3d);
        assertThat(((AtomicDouble) y).get(), is(0d));
    }

}
