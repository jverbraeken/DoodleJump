package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ISprite.class, ISprite[].class})
public class JetpackTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);;

    private Jetpack jetpack;
    private ISprite[] spritePack = new ISprite[10];


    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(doodle.getXPos()).thenReturn(0d);
        when(doodle.getYPos()).thenReturn(0d);
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(logger);
        when(sprite.getHeight()).thenReturn(0);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(spriteFactory.getJetpackActiveSprites(anyInt())).thenReturn(spritePack);

        jetpack = new Jetpack(serviceLocator, new Point(0, 0), 1, new ISprite[] {}, 0, 0);
    }

    @Test
    public void testCollidesWith_SetOwner() {
        jetpack.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(jetpack, "owner");
        assertThat(owner, is(doodle));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCollidesWithNull() {
        jetpack.collidesWith(null);
    }

    @Test
    public void testRenderNoOwner() {
        jetpack.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

    @Test
    public void testRenderWithOwner() {
        jetpack.collidesWith(doodle);
        jetpack.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
    }

    @Test
    public void testPerform() {
        jetpack.collidesWith(doodle);
        jetpack.perform(PowerupOccasion.constant);
        verify(doodle, times(1)).setVerticalSpeed(anyDouble());
    }

    @Test
    public void testPerformInvalidOccasion() {
        jetpack.collidesWith(doodle);
        jetpack.perform(PowerupOccasion.collision);
        verify(doodle, never()).setVerticalSpeed(anyDouble());
    }

    @Test(expected=NullPointerException.class)
    public void testPerformNoOwner() {
        jetpack.perform(PowerupOccasion.constant);
    }
}
