package objects.powerups;

import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import resources.sprites.ISprite;
import system.IServiceLocator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Created by Michael on 9/30/2016.
 */
public class SpringTest {

    private IAudioManager audioManager;
    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;
    private ISprite sprite, newSprite;
    private IRenderer renderer;
    private Spring spring;
    private IDoodle doodle;

    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        sprite = mock(ISprite.class);
        newSprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getSpringSprite()).thenReturn(sprite);
        when(spriteFactory.getSpringUsedSprite()).thenReturn(newSprite);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(sprite.getHeight()).thenReturn(20);
        when(newSprite.getHeight()).thenReturn(40);
    }

    @Test
    public void testPlaySound() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        Whitebox.invokeMethod(spring, "playSound");
        verify(audioManager).playFeder();
    }

    @Test
    public void testRenderer() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        spring.render();
        verify(renderer).drawSprite(sprite, (int) spring.getXPos(), (int) spring.getYPos());
    }

    @Test
    public void testCollidesWith() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        spring.collidesWith(doodle);
        verify(doodle).collide(spring);
    }

    @Test
    public void testAnimate() throws Exception {
        double deviation = 0.001;
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 50, 200);
        Whitebox.invokeMethod(spring, "animate");
        assertEquals(newSprite, spring.getSprite());
        assertEquals(180, spring.getYPos(), deviation);
    }

}
