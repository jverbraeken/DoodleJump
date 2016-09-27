package objects.blocks.platform;

import constants.IConstants;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.ServiceLocator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.ICamera;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PlatformTest {

    private IPlatform p;
    @Before
    public void init() {
        IConstants constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(1);
        ICamera camera = mock(ICamera.class);
        when(camera.getYPos()).thenReturn(2d);
        IRenderer renderer = mock(IRenderer.class);
        when(renderer.getCamera()).thenReturn(camera);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        ISprite sprite = mock(ISprite.class);
        p = new Platform(serviceLocator, 1, 1, sprite);
    }

    @Test
    public void getBoost() throws NoSuchFieldException, IllegalAccessException {
        Field field = Platform.class.getDeclaredField("BOOST");
        field.setAccessible(true);

        assertThat(p.getBoost(), is(field.get(p)));
    }
}
