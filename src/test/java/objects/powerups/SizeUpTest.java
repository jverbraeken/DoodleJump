package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import java.awt.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SizeUpTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SizeUp sizeUp;
    private double sizeUpScalar = Whitebox.getInternalState(SizeUp.class, "SCALE_INCREASE");

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(loggerFactory.createLogger(SizeUp.class)).thenReturn(logger);
        when(sprite.getWidth()).thenReturn(0);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);

        sizeUp = new SizeUp(serviceLocator, new Point(0, 0), 1);
    }

    @Test
    public void testCollidesWith() throws Exception {
        sizeUp.collidesWith(doodle);
        verify(doodle, times(1)).increaseSpriteScalar(sizeUpScalar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCollidesWithNull() {
        sizeUp.collidesWith(null);
    }

    @Test
    public void testRender() {
        sizeUp.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

}
