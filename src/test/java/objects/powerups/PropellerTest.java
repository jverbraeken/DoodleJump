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
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class PropellerTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
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
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(null);

        propeller = new Propeller(serviceLocator, 0, 0);
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        propeller.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(propeller, "owner");
        assertThat(owner, is(doodle));
    }

    @Test
    public void testGetBoost() throws Exception {
        propeller.collidesWith(doodle);

        double boost = propeller.getBoost();
        assertThat(boost, is(propellerBoost));
    }

    @Test
    public void testGetType() {
        PassiveType type = propeller.getType();
        assertThat(type, is(PassiveType.constant));
    }

}
