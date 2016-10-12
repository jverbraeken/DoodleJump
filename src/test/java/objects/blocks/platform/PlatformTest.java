package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import math.ICalc;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import rendering.ICamera;
import rendering.IRenderer;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PlatformTest {

    private IPlatform p;
    private IServiceLocator serviceLocator;
    private IRenderer renderer;
    private ISprite sprite;
    private IConstants constants;
    private IAudioManager audioManager;
    private IDoodle doodle;

    /**
     * Initialize before every test.
     */
    @Before
    public void init() {
        constants = mock(IConstants.class);
        when(constants.getGameWidth()).thenReturn(1);
        ICamera camera = mock(ICamera.class);
        when(camera.getYPos()).thenReturn(2d);

        renderer = mock(IRenderer.class);
        when(renderer.getCamera()).thenReturn(camera);

        audioManager = mock(IAudioManager.class);
        doodle = mock(IDoodle.class);

        serviceLocator = mock(IServiceLocator.class);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);

        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        sprite = mock(ISprite.class);
        p = new Platform(serviceLocator, 1, 1, sprite);
    }

    /**
     * Test rendering a normal platform
     */
    @Test
    public void renderTest() {
        p.render();

        Mockito.verify(serviceLocator).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, 1, 1);
    }

    /**
     * Test the updateEnums method.
     */
    @Test
    public void updateEnumsTest() {
        p.updateEnums(1,1);

        Mockito.verify(serviceLocator, Mockito.times(1)).getConstants();
    }

    /**
     * Test the getBoost method.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void getBoostTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Platform.class.getDeclaredField("BOOST");
        field.setAccessible(true);

        assertThat(p.getBoost(), is(field.get(p)));
    }

    /**
     * Test the setOffSet method, if the variable is set right.
     */
    @Test
    public void setOffSetTest() {
        p.setOffset(10);
        assertThat(p.getOffset(), is(10));
    }

    /**
     * Test the getOffSet method.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void getOffSetTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Platform.class.getDeclaredField("offSet");
        field.setAccessible(true);

        assertThat(p.getOffset(), is(field.get(p)));
    }

    /**
     * Test the collidesWith method.
     */
    @Test
    public void collidesWith(){
        p.collidesWith(doodle);

        Mockito.verify(doodle).collide(p);
        Mockito.verify(audioManager).playJump();
    }

    
    @Test
    public void collidesWithBreakPlatform(){
        p.getProps().put(Platform.PlatformProperties.breaks, 1);
        p.collidesWith(doodle);

        Mockito.verify(doodle, Mockito.times(0)).collide(p);
        Mockito.verify(audioManager).playLomise();
    }

}
