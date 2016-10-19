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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

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
        when(spriteFactory.getJetpackActiveSprites()).thenReturn(spritePack);

        jetpack = new Jetpack(serviceLocator, 0, 0);
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
        verify(renderer, times(1)).drawSprite(sprite, 0, 0);
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

    @Test
    public void testRenderWithOwner() {
        jetpack.collidesWith(doodle);
        jetpack.render();
        verify(renderer, times(1)).drawSprite(sprite, 0, 0);
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
        verify(doodle, times(0)).setVerticalSpeed(anyDouble());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPerformNoOwner() {
        jetpack.perform(PowerupOccasion.constant);
    }

}
