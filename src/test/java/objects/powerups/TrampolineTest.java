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
 * Test class to test the methods in Trampoline.java.
 *
 * Created by Michael on 9/30/2016.
 */
@SuppressWarnings("magicnumber")
public class TrampolineTest {
    private IAudioManager audioManager;
    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;
    private ISprite usedSprite, newSprite;
    private IRenderer renderer;
    private Trampoline trampoline;
    private IDoodle doodle;

    /**
     * Initialisation of variables for the test cases.
     */
    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        usedSprite = mock(ISprite.class);
        newSprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getSpringSprite()).thenReturn(usedSprite);
        when(spriteFactory.getSpringUsedSprite()).thenReturn(newSprite);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(usedSprite.getHeight()).thenReturn(20);
        when(newSprite.getHeight()).thenReturn(40);
    }

    /**
     * Tests if the playFeder method of the audio manager is called when playSound method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testPlaySound() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0);
        Whitebox.invokeMethod(trampoline, "playSound");
        verify(audioManager).playFeder();
    }

    /**
     * Tests if the drawSprite method is called when the render method of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRenderer() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0);
        trampoline.render();
        verify(renderer).drawSprite(usedSprite, (int) trampoline.getXPos(), (int) trampoline.getYPos());
    }

    /**
     * Tests if the collide method of the doodle is called when the method collidesWith of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0);
        trampoline.collidesWith(doodle);
        verify(doodle).collide(trampoline);
    }

    /**
     * Tests is the Y position of the spring is properly adjusted when the animate method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */

    @Test
    public void testAnimate() throws Exception {
        double deviation = 0.001;
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 30, 653);
        Whitebox.invokeMethod(trampoline, "animate");
        assertEquals(newSprite, trampoline.getSprite());
        assertEquals(633, trampoline.getYPos(), deviation);
    }

    /**
     * Tests if the getBoost method of the trampoline object properly returns the value of BOOST.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testGetBoost() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0);
        double boost = -35;
        double deviation = 0.001;
        assertEquals(boost, trampoline.getBoost(), deviation);
    }

}
