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
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PlatformTest {

    private IPlatform p;
    private IPlatform q;
    private ISpriteFactory sf;
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

        sprite = mock(ISprite.class);
        sf = mock(ISpriteFactory.class);
        when(sf.getPlatformBrokenSprite(1)).thenReturn(sprite);
        when(sf.getPlatformSprite1()).thenReturn(sprite);

        serviceLocator = mock(IServiceLocator.class);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);
        when(serviceLocator.getSpriteFactory()).thenReturn(sf);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(doodle.getVerticalSpeed()).thenReturn(1d);
        when(doodle.getHitBox()).thenReturn(new double[4]);

        p = new Platform(serviceLocator, new Point(1, 1), sprite);
        q = new PlatformBroken(serviceLocator, p);
    }

    /**
     * Test rendering a normal platform
     */
    @Test
    public void renderTest() {
        p.render();

        Mockito.verify(serviceLocator).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, new Point(1, 1));
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
        q.collidesWith(doodle);

        Mockito.verify(doodle, Mockito.times(0)).collide(q);
        Mockito.verify(audioManager).playLomise();
    }

    @Test(expected=IllegalArgumentException.class)
    public void collideNoDoodleTest() {
        p.collidesWith(null);
    }

}
