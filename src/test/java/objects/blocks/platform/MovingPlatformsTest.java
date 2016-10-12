package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rendering.ICamera;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpriteFactory.class)
public class MovingPlatformsTest {

    private IPlatform platform;
    private IPlatform horizontal;
    private IPlatform vertical;
    private ISpriteFactory spriteFactory;
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

        sprite = mock(ISprite.class);

        spriteFactory = mock(SpriteFactory.class);
        when(spriteFactory.getPlatformSpriteHori()).thenReturn(sprite);

        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        platform = new Platform(serviceLocator, 1, 1, sprite);
        horizontal = new PlatformHorizontal(serviceLocator, this.platform);
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
        horizontal.getProps().put(hori, expected);

        horizontal.updateEnums(0,0);

        assertThat(horizontal.getProps().get(hori), is(expected));
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
        horizontal.getProps().put(hori, 1);

        horizontal.updateEnums(200,0);

        assertThat(horizontal.getProps().get(hori), is(expected));
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
        horizontal.getProps().put(hori, -1);

        horizontal.updateEnums(-200,0);

        assertThat(horizontal.getProps().get(hori), is(expected));
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
        horizontal.getProps().put(vert, expected);

        horizontal.updateEnums(0,0);

        assertThat(horizontal.getProps().get(vert), is(expected));
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
        platform.getProps().put(vert, -1);

        platform.setOffset(21);

        platform.updateEnums(0,0);

        assertThat(platform.getProps().get(vert), is(expected));
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
        platform.getProps().put(vert, 1);

        platform.setOffset(-21);

        platform.updateEnums(0,0);

        assertThat(platform.getProps().get(vert), is(expected));
    }

    /**
     * Test rendering a horizontal platform going right.
     */
    @Test
    public void renderHoriTest() {
        double expected = horizontal.getXPos() + 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        horizontal.getProps().put(hori, 1);

        horizontal.render();
        horizontal.update(0.05d);

        assertThat(horizontal.getXPos(), is(expected));
    }

    /**
     * Test rendering a horizontal platform going left.
     */
    @Test
    public void renderHoriOppositeTest() {
        double expected = horizontal.getXPos() - 2;
        Platform.PlatformProperties hori = Platform.PlatformProperties.movingHorizontally;
        horizontal.getProps().put(hori, -1);

        horizontal.render();
        horizontal.update(0.05d);

        assertThat(horizontal.getXPos(), is(expected));
    }

    /**
     * Test rendering a vertical moving platform going up.
     */
    @Test
    public void renderVertTest() {
        double expected = platform.getYPos() - 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        platform.getProps().put(vert, 1);

        platform.render();
        platform.update(0.05d);

        assertThat(platform.getYPos(), is(expected));
    }

    /**
     * Test rendering a vertical moving platform going down.
     */
    @Test
    public void renderOppositVertTest() {
        double expected = platform.getYPos() + 2;
        Platform.PlatformProperties vert = Platform.PlatformProperties.movingVertically;
        platform.getProps().put(vert, -1);

        platform.render();
        platform.update(0.05d);

        assertThat(platform.getYPos(), is(expected));
    }
}
