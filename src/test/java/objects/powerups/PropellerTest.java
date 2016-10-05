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
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private Propeller propeller;
    private double propellerBoost = Whitebox.getInternalState(Propeller.class, "BOOST");

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(spriteFactory.getPropellerSprite()).thenReturn(null);
        when(loggerFactory.createLogger(Propeller.class)).thenReturn(logger);

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
        propeller.perform("constant");
        verify(doodle, times(1)).setVerticalSpeed(propellerBoost);
    }

    @Test
    public void testPerformInvalid() throws Exception {
        propeller.collidesWith(doodle);
        propeller.perform("invalid");
        verify(doodle, times(0)).setVerticalSpeed(propellerBoost);
    }

}
