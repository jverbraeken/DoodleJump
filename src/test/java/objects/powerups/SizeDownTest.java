package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SizeDownTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SizeDown sizeDown;
    private double sizeDownScalar = Whitebox.getInternalState(SizeDown.class, "SCALE_INCREASE");

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(sprite.getWidth()).thenReturn(0);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(loggerFactory.createLogger(SizeDown.class)).thenReturn(logger);

        sizeDown = new SizeDown(serviceLocator, 0, 0);
    }

    @Test
    public void testCollidesWith() throws Exception {
        sizeDown.collidesWith(doodle);
        verify(doodle, times(1)).increaseSpriteScalar(sizeDownScalar);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCollidesWithNull() {
        sizeDown.collidesWith(null);
    }

    @Test
    public void testRender() {
        sizeDown.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

}
