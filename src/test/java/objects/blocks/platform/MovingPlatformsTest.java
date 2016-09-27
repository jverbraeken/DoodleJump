package objects.blocks.platform;

import constants.IConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rendering.ICamera;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class MovingPlatformsTest {

    private IPlatform p;
    private IServiceLocator serviceLocator;
    private IRenderer renderer;
    private ISprite sprite;

    @Before
    public void init() {
        IConstants constants = mock(IConstants.class);
        when(constants.getGameWidth()).thenReturn(100);
        when(constants.getGameHeight()).thenReturn(100);
        ICamera camera = mock(ICamera.class);
        when(camera.getYPos()).thenReturn(2d);
        renderer = mock(IRenderer.class);
        when(renderer.getCamera()).thenReturn(camera);
        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        sprite = mock(ISprite.class);
        p = new Platform(serviceLocator, 1, 1, sprite);
    }

    @Test
    public void updateEnumsTestNothingHori() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, expected);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    @Test
    public void updateEnumsTestFlip1Hori() throws NoSuchFieldException, IllegalAccessException {
        int expected = -1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, 1);

        p.updateEnums(200,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    @Test
    public void updateEnumsTestFlipMin1Hori() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, -1);

        p.updateEnums(-200,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    @Test
    public void updateEnumsTestNothingVert() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, expected);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    @Test
    public void updateEnumsTestFlip1Vert() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, -1);

        p.setOffset(21);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    @Test
    public void updateEnumsTestFlipMin1Vert() throws NoSuchFieldException, IllegalAccessException {
        int expected = -1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, 1);

        p.setOffset(-21);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    @Test
    public void renderHoriTest() {
        double expected = p.getXPos() + 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, 1);

        p.render();

        assertThat(p.getXPos(), is(expected));
    }

    @Test
    public void renderHoriOppositeTest() {
        double expected = p.getXPos() - 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, -1);

        p.render();

        assertThat(p.getXPos(), is(expected));
    }

    @Test
    public void renderVertTest() {
        double expected = p.getYPos() - 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, 1);

        p.render();

        assertThat(p.getYPos(), is(expected));
    }

    @Test
    public void renderOppositVertTest() {
        double expected = p.getYPos() + 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, -1);

        p.render();

        assertThat(p.getYPos(), is(expected));
    }
}
