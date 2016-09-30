package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import logging.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rendering.ICamera;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class BreakingPlatformsTest {

    private IPlatform p;
    private IServiceLocator serviceLocator;
    private IRenderer renderer;
    private ISprite sprite;
    private ISprite brokensprite;
    private ISprite brokensprite2;
    private ISprite brokensprite3;
    private ISpriteFactory sf;

    /**
     * Initialize before every test.
     */
    @Before
    public void init() {
        IConstants constants = mock(IConstants.class);
        when(constants.getGravityAcceleration()).thenReturn(1d);
        renderer = mock(IRenderer.class);
        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        sprite = mock(ISprite.class);
        brokensprite = mock(ISprite.class);
        brokensprite2 = mock(ISprite.class);
        brokensprite3 = mock(ISprite.class);

        sf = mock(ISpriteFactory.class);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getSpriteFactory()).thenReturn(sf);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);
        when(sf.getPlatformBrokenSprite2()).thenReturn(brokensprite);
        when(sf.getPlatformBrokenSprite3()).thenReturn(brokensprite2);
        when(sf.getPlatformBrokenSprite4()).thenReturn(brokensprite3);

        p = new Platform(serviceLocator, 1, 1, sprite);
        p.getProps().put(Platform.PlatformProperties.breaks, 1);
    }

    /**
     * Check if the broken sprite from animation 2 is returned.
     */
    @Test
    public void getBrokenSpriteTest2() {
        assertThat(p.getBrokenSprite(2), is(brokensprite));

        Mockito.verify(serviceLocator).getSpriteFactory();
        Mockito.verify(sf).getPlatformBrokenSprite2();
    }

    /**
     * Check if the broken sprite from animation 3 is returned.
     */
    @Test
    public void getBrokenSpriteTest3() {
        assertThat(p.getBrokenSprite(3), is(brokensprite2));

        Mockito.verify(sf).getPlatformBrokenSprite3();
    }

    /**
     * Check if the broken sprite from animation 4 is returned.
     */
    @Test
    public void getBrokenSpriteTest4() {
        assertThat(p.getBrokenSprite(4), is(brokensprite3));

        Mockito.verify(sf).getPlatformBrokenSprite4();
    }

    /**
     * Check if the broken sprite from animation 4 is returned.
     */
    @Test
    public void getBrokenSpriteTestMin1() {
        assertThat(p.getBrokenSprite(-1), is(brokensprite3));

        Mockito.verify(sf).getPlatformBrokenSprite4();
    }

    /**
     * Check if any other animation is asked for, the normal
     * sprite will be returned.
     */
    @Test
    public void getBrokenSpriteTestElse() {
        assertThat(p.getBrokenSprite(5), is(sprite));
    }

    /**
     * Check that gravity is applied to the platform, when 'broken'.
     */
    @Test
    public void applyGravityTest() {
        p.getProps().replace(Platform.PlatformProperties.breaks, -1);
        double oldYpos = p.getYPos();
        p.render();
        assertThat(p.getYPos(), is(oldYpos + 1));
        p.render();
        assertThat(p.getYPos(), is(oldYpos + 3));
    }
}