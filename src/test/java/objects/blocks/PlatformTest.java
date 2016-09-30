package objects.blocks;

import objects.blocks.platform.Platform;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the Platform class.
 * <p>
 * Created by Michael on 9/28/2016.
 */
public class PlatformTest {

    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;
    private ISprite mockedSprite;
    private IRenderer renderer;
    private IAudioManager audioManager;
    private Platform platform;
    private IDoodle doodle;

    /**
     * Initialising variables necessary for the test cases.
     */
    @Before
    public void init() {
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);
        renderer = mock(IRenderer.class);
        mockedSprite = mock(ISprite.class);
        audioManager = mock(IAudioManager.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getPlatformSprite1()).thenReturn(mockedSprite);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
    }

    /**
     * Tests if the getBoost method proerly returns the value for boost.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testGetBoost() throws Exception{
        double boost = -18;
        double fakeBoost = 40;
        double expectedDeviation = 0.001;
        platform = Whitebox.invokeConstructor(Platform.class, serviceLocator, 0, 0);
        assertEquals(boost, platform.getBoost(), expectedDeviation);
        assertNotEquals(fakeBoost, platform.getBoost());
    }

    /**
     * Tests if the render method properly calls the drawSprite method of the serverLocator's associated Renderer object.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRender() throws Exception {
        platform = Whitebox.invokeConstructor(Platform.class, serviceLocator, 0, 0);
        platform.render();
        verify(renderer).drawSprite(mockedSprite, (int) platform.getXPos(), (int) platform.getYPos());
    }

    /**
     * Tests when a platform collides with a doodle object, the collide method of the doodle object is called.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testCollideWith() throws Exception {
        platform = Whitebox.invokeConstructor(Platform.class, serviceLocator, 0, 0);
        doodle = mock(IDoodle.class);
        doodle.collide(platform);
        verify(doodle).collide(platform);
    }

    /**
     * Tests is the playJump method of AudioManager has been called if the playSound method of the platform object is called.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testPlaySound() throws Exception {
        platform = Whitebox.invokeConstructor(Platform.class, serviceLocator, 0, 0);
        Whitebox.invokeMethod(platform, "playSound");
        verify(audioManager).playJump();
    }
}

