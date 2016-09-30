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
    private ISprite sprite;
    private IRenderer renderer;
    private Spring spring;
    private IDoodle doodle;

    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        sprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getSpringSprite()).thenReturn(sprite);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
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

}
