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
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PropellerTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private Propeller propeller;
    private int render_y_offset = Whitebox.getInternalState(Propeller.class, "OWNED_Y_OFFSET");

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(doodle.getXPos()).thenReturn(0d);
        when(doodle.getYPos()).thenReturn(0d);
        when(loggerFactory.createLogger(Propeller.class)).thenReturn(logger);
        when(sprite.getHeight()).thenReturn(0);
        when(spriteFactory.getPropellerSprite()).thenReturn(sprite);

        propeller = new Propeller(serviceLocator, 0, 0);
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        propeller.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(propeller, "owner");
        assertThat(owner, is(doodle));
    }

    @Test
    public void testPerform() throws Exception {
        propeller.collidesWith(doodle);

        propeller.perform(PowerupOccasion.constant);
        verify(doodle, times(1)).setVerticalSpeed(anyDouble());
    }

    @Test
    public void testPerformInvalid() throws Exception {
        propeller.collidesWith(doodle);
        propeller.perform(PowerupOccasion.collision);
        verify(doodle, times(0)).setVerticalSpeed(anyDouble());
    }


    @Test
    public void testRenderNoOwner() {
        propeller.render();
        verify(renderer, times(1)).drawSprite(sprite, 0, 0);
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

    @Test
    public void testRenderWithOwner() {
        propeller.collidesWith(doodle);

        propeller.render();
        verify(renderer, times(1)).drawSprite(sprite, 0, render_y_offset);
        verify(doodle, times(1)).getXPos();
        verify(doodle, times(1)).getYPos();
    }

}
