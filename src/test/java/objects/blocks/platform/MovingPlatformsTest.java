package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
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

    /**
     * Initialize before every test.
     */
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
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);

        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        sprite = mock(ISprite.class);
        p = new Platform(serviceLocator, 1, 1, sprite);
    }

    /**
     * Test that the updateEnums method changes nothing for the horizontal direction.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestNothingHori() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, expected);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    /**
     * Test that the updateEnums method changes the horizontal direction
     * from right to left.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestFlip1Hori() throws NoSuchFieldException, IllegalAccessException {
        int expected = -1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, 1);

        p.updateEnums(200,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    /**
     * Test that the updateEnums method changes the horizontal direction
     * from left to right.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestFlipMin1Hori() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, -1);

        p.updateEnums(-200,0);

        assertThat(p.getProps().get(hori), is(expected));
    }

    /**
     * Test that the updateEnums method changes nothing for the vertical direction.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestNothingVert() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, expected);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    /**
     * Test that the updateEnums method changes the vertical direction
     * from down to up.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestFlip1Vert() throws NoSuchFieldException, IllegalAccessException {
        int expected = 1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, -1);

        p.setOffset(21);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    /**
     * Test that the updateEnums method changes the vertical direction
     * from up to down.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void updateEnumsTestFlipMin1Vert() throws NoSuchFieldException, IllegalAccessException {
        int expected = -1;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, 1);

        p.setOffset(-21);

        p.updateEnums(0,0);

        assertThat(p.getProps().get(vert), is(expected));
    }

    /**
     * Test rendering a horizontal platform going right.
     */
    @Test
    public void renderHoriTest() {
        double expected = p.getXPos() + 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, 1);

        p.render();
        p.update(0.05d);

        assertThat(p.getXPos(), is(expected));
    }

    /**
     * Test rendering a horizontal platform going left.
     */
    @Test
    public void renderHoriOppositeTest() {
        double expected = p.getXPos() - 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        p.getProps().put(hori, -1);

        p.render();
        p.update(0.05d);

        assertThat(p.getXPos(), is(expected));
    }

    /**
     * Test rendering a vertical moving platform going up.
     */
    @Test
    public void renderVertTest() {
        double expected = p.getYPos() - 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, 1);

        p.render();
        p.update(0.05d);

        assertThat(p.getYPos(), is(expected));
    }

    /**
     * Test rendering a vertical moving platform going down.
     */
    @Test
    public void renderOppositVertTest() {
        double expected = p.getYPos() + 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        p.getProps().put(vert, -1);

        p.render();
        p.update(0.05d);

        assertThat(p.getYPos(), is(expected));
    }
}
