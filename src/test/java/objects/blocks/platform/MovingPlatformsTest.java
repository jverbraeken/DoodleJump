package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import math.Calc;
import math.ICalc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import rendering.ICamera;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import resources.sprites.Sprite;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpriteFactory.class, Calc.class, Sprite.class})
public class MovingPlatformsTest {

    private IPlatform platform;
    private IPlatform horizontal;
    private IPlatform vertical;
    private ISpriteFactory spriteFactory;
    private IServiceLocator serviceLocator;
    private ICalc calc;
    private IRenderer renderer;
    private ISprite sprite;

    /**
     * Initialize before every test.
     */
    @Before
    public void init() {
        IConstants constants = mock(IConstants.class);
        when(constants.getGameWidth()).thenReturn(0);
        when(constants.getGameHeight()).thenReturn(100);

        ICamera camera = mock(ICamera.class);
        when(camera.getYPos()).thenReturn(2d);

        renderer = mock(IRenderer.class);
        when(renderer.getCamera()).thenReturn(camera);

        sprite = mock(ISprite.class);
        when(sprite.getWidth()).thenReturn(0);

        spriteFactory = mock(SpriteFactory.class);
        when(spriteFactory.getSprite(Matchers.<IRes.Sprites>any())).thenReturn(sprite);

        calc = mock(Calc.class);
        when(calc.getRandomDouble(1)).thenReturn(0d);

        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getCalc()).thenReturn(calc);

        platform = new Platform(serviceLocator, new Point(1, 1), sprite);
        horizontal = new PlatformHorizontal(serviceLocator, platform);
        vertical = new PlatformVertical(serviceLocator, platform);
    }

    /**
     * Test that the updateEnums method changes the horizontal direction
     * from right to left.
     */
    @Test
    public void updateEnumsTestFlip1Horizontal() {
        horizontal.setXPos(11);
        horizontal.update(0d);

        int speed = Whitebox.getInternalState(horizontal, "speed");
        assertThat(speed < 0, is(true));
    }

    /**
     * Test that the updateEnums method changes the horizontal direction
     * from left to right.
     */
    @Test
    public void updateEnumsTestFlipMin1Horizontal() {
        horizontal.setXPos(-1);
        horizontal.update(0d);

        int speed = Whitebox.getInternalState(horizontal, "speed");
        assertThat(speed > 0, is(true));
    }

    /**
     * Test that the updateEnums method changes the vertical direction
     * from down to up.
     */
    @Test
    public void updateEnumsTestFlip1Vertical() {
        vertical.setOffset(1000);
        Whitebox.setInternalState(vertical, "speed", 2);
        vertical.update(0d);

        double speed = Whitebox.getInternalState(vertical, "speed");
        assertThat(speed < 0, is(true));
    }

    /**
     * Test that the updateEnums method changes the vertical direction
     * from up to down.
     */
    @Test
    public void updateEnumsTestFlipMin1Vertical() {
        vertical.setOffset(-1000);
        Whitebox.setInternalState(vertical, "speed", -2);
        vertical.update(0d);

        double speed = Whitebox.getInternalState(vertical, "speed");
        assertThat(speed < 0d, is(false));
    }

    /**
     * Test rendering a horizontal vertical going right.
     */
    @Test
    public void renderHorizontalTest() {
        // 2 times because inheritance (?)
        horizontal.render();
        verify(serviceLocator, times(2)).getRenderer();
        verify(renderer, times(2)).drawSprite(sprite, new Point((int) vertical.getXPos(), (int) vertical.getYPos()));
    }

    /**
     * Test rendering a vertical moving vertical going up.
     */
    @Test
    public void renderVerticalTest() {
        // 2 times because inheritance (?)
        vertical.render();
        verify(serviceLocator, times(2)).getRenderer();
        verify(renderer, times(2)).drawSprite(sprite, new Point((int) vertical.getXPos(), (int) vertical.getYPos()));
    }

}
