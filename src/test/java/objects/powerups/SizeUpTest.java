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
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SizeUpTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SizeUp sizeUp;
    private double sizeUpScalar = Whitebox.getInternalState(SizeUp.class, "SCALE_INCREASE");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(sprite.getWidth()).thenReturn(0);
        when(spriteFactory.getSizeUpSprite()).thenReturn(sprite);
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(null);

        sizeUp = new SizeUp(serviceLocator, 0, 0);
    }

    @After
    public void finish() {
        sizeUp = null;
    }

    @Test
    public void testCollidesWith() throws Exception {
        sizeUp.collidesWith(doodle);
        verify(doodle, times(1)).increaseSpriteScalar(sizeUpScalar);
    }

}
