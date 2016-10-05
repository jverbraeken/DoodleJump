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

public class SizeDownTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
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

        when(constants.getGameWidth()).thenReturn(100);
        when(sprite.getWidth()).thenReturn(0);
        when(spriteFactory.getSizeDownSprite()).thenReturn(sprite);
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(null);

        sizeDown = new SizeDown(serviceLocator, 0, 0);
    }

    @Test
    public void testCollidesWith() throws Exception {
        sizeDown.collidesWith(doodle);
        verify(doodle, times(1)).increaseSpriteScalar(sizeDownScalar);
    }

}
