package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArcadeCameraTest {

    private ICamera camera;

    @Before
    public void init() {
        camera = new ArcadeCamera();
    }

    @Test
    public void testGetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(ArcadeCamera.class, "y");

        ((AtomicDouble) y).set(2d);
        assertThat(camera.getYPos(), is(2d));

        ((AtomicDouble) y).set(3d);
        assertThat(camera.getYPos(), is(3d));
    }

    @Test
    public void testSetYPos() throws NoSuchFieldException, IllegalAccessException {
        Object y = Whitebox.getInternalState(ArcadeCamera.class, "y");

        camera.setYPos(2d);
        assertThat(((AtomicDouble) y).get(), is(2d));

        camera.setYPos(3d);
        assertThat(((AtomicDouble) y).get(), is(3d));
    }

    @Test
    public void testUpdate() {
        double currentSpeed = Whitebox.getInternalState(camera, "speed");
        camera.update(0d);
        double newSpeed = Whitebox.getInternalState(camera, "speed");
        assertFalse(newSpeed == currentSpeed);
    }

    @Test
    public void testSetAccelerationType() {
        camera.setAccelerationType(AccelerationType.fast);
        AccelerationType actual = Whitebox.getInternalState(camera, "accelerationType");
        assertThat(actual, is(AccelerationType.fast));
    }

}
