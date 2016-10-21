package objects.powerups;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
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
 * Test class for the Spring class.
 *
 * Created by Michael on 9/30/2016.
 */
public class SpringTest {

    private IAudioManager audioManager;
    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;
    private ISprite sprite, usedSprite;
    private IRenderer renderer;
    private Spring spring;
    private IDoodle doodle;
    private ILoggerFactory loggerFactory;
    private IProgressionManager progressionManager;
    private ILogger logger;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Initialisation of variables for the test cases.
     */
    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        sprite = mock(ISprite.class);
        usedSprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        renderer = mock(IRenderer.class);
        doodle = mock(IDoodle.class);
        loggerFactory = mock(ILoggerFactory.class);
        progressionManager = mock(IProgressionManager.class);
        logger = mock(ILogger.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(Spring.class)).thenReturn(logger);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getSpringSprite()).thenReturn(sprite);
        when(spriteFactory.getSpringUsedSprite()).thenReturn(usedSprite);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
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
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        Whitebox.invokeMethod(spring, "playSound");
        verify(audioManager).playFeder();
    }

    /**
     * Tests if the drawSprite method is called when the render method of the spring is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRenderer() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        spring.render();
        verify(renderer).drawSprite(sprite, (int) spring.getXPos(), (int) spring.getYPos());
    }

    /**
     * Tests if the collide method of the doodle is called when the method collidesWith of the spring is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        spring.collidesWith(doodle);
        verify(doodle).collide(spring);
    }

    /**
     * Tests if the method returns a NullPointerException when the parameter is null.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollidesWith2() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        thrown.expect(IllegalArgumentException.class);
        spring.collidesWith(null);
    }

    /**
     * Tests is the Y position of the spring is properly adjusted when the animate method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testAnimate() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 50, 200);
        Whitebox.invokeMethod(spring, "animate");
        assertEquals(usedSprite, spring.getSprite());
        assertEquals(180, spring.getYPos(), 0.001);
    }

    /**
     * Tests if the getBoost method of the spring object properly returns the value of BOOST.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testGetBoost() throws Exception {
        spring = Whitebox.invokeConstructor(Spring.class, serviceLocator, 0, 0);
        double boost = Whitebox.getInternalState(spring, "boost", AJumpablePowerup.class);
        assertEquals(boost, spring.getBoost(), 0.001);
    }

}
