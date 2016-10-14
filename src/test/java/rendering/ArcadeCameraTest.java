package rendering;

import com.google.common.util.concurrent.AtomicDouble;
import constants.IConstants;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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
        assertThat(newSpeed == currentSpeed, is(false));
    }

}
