package objects.powerups;

import constants.IConstants;
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
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PropellerTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private Propeller propeller;
    private int render_y_offset = Whitebox.getInternalState(Propeller.class, "OWNED_Y_OFFSET");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(doodle.getXPos()).thenReturn(0d);
        when(doodle.getYPos()).thenReturn(0d);
        when(spriteFactory.getPropellerSprite()).thenReturn(sprite);
        when(sprite.getHeight()).thenReturn(0);
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(null);

        propeller = new Propeller(serviceLocator, 0, 0);
    }

    @After
    public void finish() {
        propeller = null;
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        propeller.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(propeller, "owner");
        assertThat(owner.equals(doodle), is(true));
    }

    @Test
    public void testGetBoost() throws Exception {
        propeller.collidesWith(doodle);

        propeller.update(0d);
        double boost = propeller.getBoost();
        assertThat(boost < 0, is(true));
    }

    @Test
    public void testGetType() {
        PassiveType x = propeller.getType();
        assertThat(x.equals(PassiveType.constant), is(true));
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
