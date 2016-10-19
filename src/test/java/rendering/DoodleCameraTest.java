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

public class DoodleCameraTest {

    private ICamera camera;
    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);

    @Before
    public void init() {
        camera = new DoodleCamera(serviceLocator, doodle);

        when(constants.getGameHeight()).thenReturn(1000); // Should be big to ensure testUpdate works.
        when(doodle.getYPos()).thenReturn(0d);
        when(serviceLocator.getConstants()).thenReturn(constants);
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

    @Test
    public void testUpdate() {
        camera.update(0d);
        verify(doodle, times(1)).getYPos();
    }

}
