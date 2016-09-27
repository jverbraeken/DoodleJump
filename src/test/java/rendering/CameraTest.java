package rendering;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CameraTest {
    static ICamera camera;

    @Before
    public void init() {
        camera = new Camera();
    }

    @Test
    public void testGetYPos() throws NoSuchFieldException, IllegalAccessException {
        Field field = camera.getClass().getDeclaredField("y");
        field.setAccessible(true);

        field.set(camera, 2d);
        assertThat(camera.getYPos(), is(2d));

        field.set(camera, 3d);
        assertThat(camera.getYPos(), is(3d));
    }

    @Test
    public void testSetYPos() throws NoSuchFieldException, IllegalAccessException {
        Field field = camera.getClass().getDeclaredField("y");
        field.setAccessible(true);

        camera.setYPos(2d);
        assertThat(field.get(camera), is(2d));

        camera.setYPos(3d);
        assertThat(field.get(camera), is(3d));
    }
}
