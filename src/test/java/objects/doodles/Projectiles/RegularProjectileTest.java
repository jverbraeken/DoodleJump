package objects.doodles.Projectiles;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

public class RegularProjectileTest {

    IDoodle doodle = mock(IDoodle.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IRenderer renderer = mock(IRenderer.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISprite  sprite = mock(ISprite.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    Point point = mock(Point.class);

    int xDir = 10, yDir = 10;
    RegularProjectile regularProjectile;

    @Before
    public void init() {
        when(loggerFactory.createLogger(RegularBehavior.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getRegularProjectileSprite()).thenReturn(sprite);

        regularProjectile = new RegularProjectile(serviceLocator, point, xDir, yDir);
    }

    @Test
    public void testCollidesWith() {
        regularProjectile.collidesWith(doodle);
        assertTrue(true); // No crash
    }

    @Test
    public void testRender() {
        regularProjectile.render();
        verify(serviceLocator, times(1)).getRenderer();
        verify(renderer, times(1)).drawSprite(sprite, anyObject());
    }

}
