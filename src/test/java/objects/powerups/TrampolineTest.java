package objects.powerups;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
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
public class TrampolineTest {

    private IAudioManager audioManager;
    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;
    private ISprite sprite, usedSprite;
    private IRenderer renderer;
    private Trampoline trampoline;
    private IDoodle doodle;
    private ILoggerFactory loggerFactory;
    private ILogger logger;
    private int boost = -35;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Initialisation of variables for the test cases.
     */
    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        usedSprite = mock(ISprite.class);
        sprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        loggerFactory = mock(ILoggerFactory.class);
        logger = mock(ILogger.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(Trampoline.class)).thenReturn(logger);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        //when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        //when(spriteFactory.getTrampolineUsedSprite(anyInt())).thenReturn(usedSprite);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(sprite.getHeight()).thenReturn(20);
        when(usedSprite.getHeight()).thenReturn(40);
        when(doodle.getHitBox()).thenReturn(new double[]{0, 0, 0, 0});
        when(doodle.getYPos()).thenReturn(-2d);
        when(doodle.getLegsHeight()).thenReturn(0d);
        when(doodle.getVerticalSpeed()).thenReturn(1d);
    }

    /**
     * Tests if the playFeder method of the audio manager is called when playSound method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testPlaySound() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0, sprite, usedSprite, boost);
        Whitebox.invokeMethod(trampoline, "playSound");
        verify(audioManager).playTrampoline();
    }

    /**
     * Tests if the drawSprite method is called when the render method of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRenderer() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0, sprite, usedSprite, boost);
        trampoline.render();
        verify(renderer).drawSprite(sprite, (int) trampoline.getXPos(), (int) trampoline.getYPos());
    }

    /**
     * Tests if the collide method of the doodle is called when the method collidesWith of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0, sprite, usedSprite, boost);
        trampoline.collidesWith(doodle);
        verify(doodle).collide(trampoline);
    }

    /**
     * Tests if the collide method returns a NullPointerException is the parameter is null.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith2() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0, sprite, usedSprite, boost);
        thrown.expect(IllegalArgumentException.class);
        trampoline.collidesWith(null);
    }

    /**
     * Tests is the Y position of the spring is properly adjusted when the animate method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testAnimate() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 30, 653, sprite, usedSprite, boost);
        Whitebox.invokeMethod(trampoline, "animate");
        assertEquals(usedSprite, trampoline.getSprite());
        assertEquals(633, trampoline.getYPos(), 0.001);
    }

    /**
     * Tests if the getBoost method of the trampoline object properly returns the value of BOOST.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testGetBoost() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, 0, 0, sprite, usedSprite, boost);
        double boost = Whitebox.getInternalState(trampoline, "boost", AJumpablePowerup.class);
        assertEquals(boost, trampoline.getBoost(), 0.001);
    }

}
