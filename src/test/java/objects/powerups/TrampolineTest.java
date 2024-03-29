package objects.powerups;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import org.powermock.reflect.exceptions.ConstructorNotFoundException;
import rendering.IRenderer;
import resources.IRes;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import resources.audio.Sounds;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import java.awt.Point;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
    private ISprite[] sprites;
    private ISprite nullSprite;
    private ISprite nullUsedSprite;
    private ISprite[] nullSprites;
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
        sprites = new ISprite[]{sprite, usedSprite};
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        loggerFactory = mock(ILoggerFactory.class);
        logger = mock(ILogger.class);
        nullSprite = null;
        nullUsedSprite = null;
        nullSprites = new ISprite[]{nullSprite, nullUsedSprite};
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(Trampoline.class)).thenReturn(logger);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getPowerupSprite(any(Powerups.class), anyInt())).thenReturn(sprite);
        when(spriteFactory.getSprite(any(IRes.Sprites.class))).thenReturn(usedSprite);
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
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(0, 0), 1);
        Whitebox.invokeMethod(trampoline, "playSound");
        verify(audioManager).play(Sounds.TRAMPOLINE);
    }

    /**
     * Tests if the drawSprite method is called when the render method of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRenderer() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(0, 0), 1);
        trampoline.render();
        verify(renderer).drawSprite(sprite, new Point((int) trampoline.getXPos(), (int) trampoline.getYPos()));
    }

    /**
     * Tests if the collide method of the doodle is called when the method collidesWith of the trampoline is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith() throws Exception {
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(0, 0), 1);
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
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(0, 0), 1);
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
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(60, 653), 1);
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
        trampoline = Whitebox.invokeConstructor(Trampoline.class, serviceLocator, new Point(0, 0), 1);
        double boost = Whitebox.getInternalState(trampoline, "boost", AJumpablePowerup.class);
        assertEquals(boost, trampoline.getBoost(), 0.001);
    }

    /**
     * Tests the constructor with null objects as input.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testConstructorNullInput() throws Exception {
        thrown.expect(ConstructorNotFoundException.class);
        trampoline = Whitebox.invokeConstructor(Trampoline.class, null, new Point(0, 0), nullSprites, null);
    }

}
