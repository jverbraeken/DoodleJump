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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

}
