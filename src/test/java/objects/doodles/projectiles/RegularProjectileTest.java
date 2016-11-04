package objects.doodles.projectiles;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import objects.doodles.doodle_behavior.RegularBehavior;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.internal.util.reflection.Whitebox;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    int direction = 10;
    RegularProjectile projectile;

    @Before
    public void init() {
        when(loggerFactory.createLogger(RegularBehavior.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getSprite(Matchers.<IRes.Sprites>any())).thenReturn(sprite);

        projectile = new RegularProjectile(serviceLocator, point, direction);
    }

    @Test
    public void testCollidesWith() {
        projectile.collidesWith(doodle); // Shouldn't do anything, so shouldn't crash
        assertTrue(true); // No crash
    }

    @Test
    public void testRender() {
        projectile.render();
        verify(serviceLocator, times(1)).getRenderer();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
    }

    @Test
    public void testUpdateXPos() {
        double xBefore = (double) Whitebox.getInternalState(projectile, "xPos");
        projectile.update(0d);
        double xAfter = (double) Whitebox.getInternalState(projectile, "xPos");
        assertFalse(xBefore == xAfter);
    }

    @Test
    public void testUpdateYPos() {
        double yBefore = (double) Whitebox.getInternalState(projectile, "yPos");
        projectile.update(0d);
        double yAfter = (double) Whitebox.getInternalState(projectile, "yPos");
        assertFalse(yBefore == yAfter);
    }

}
