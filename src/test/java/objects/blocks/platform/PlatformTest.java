package objects.blocks.platform;

import constants.IConstants;
import math.ICalc;
import objects.doodles.IDoodle;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.ServiceLocator;
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
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        sprite = mock(ISprite.class);
        p = new Platform(serviceLocator, 1, 1, sprite);
    }

    @Test
    public void renderTest() throws NoSuchFieldException, IllegalAccessException {
        p.render();

        Mockito.verify(serviceLocator).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, 1, 1);
    }

    @Test
    public void updateEnumsTest() throws NoSuchFieldException, IllegalAccessException {
        p.updateEnums(1,1);

        Mockito.verify(serviceLocator, Mockito.times(2)).getConstants();
        Mockito.verify(constants).getGameWidth();
    }

    @Test
    public void getBoostTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Platform.class.getDeclaredField("BOOST");
        field.setAccessible(true);

        assertThat(p.getBoost(), is(field.get(p)));
    }

    @Test
    public void setOffSetTest() throws NoSuchFieldException, IllegalAccessException {
        p.setOffset(10);
        assertThat(p.getOffset(), is(10));
    }

    @Test
    public void getOffSetTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Platform.class.getDeclaredField("offSet");
        field.setAccessible(true);

        assertThat(p.getOffset(), is(field.get(p)));
    }

    @Test
    public void collidesWith(){
        p.collidesWith(doodle);

        Mockito.verify(doodle).collide(p);
        Mockito.verify(audioManager).playJump();
    }

}
