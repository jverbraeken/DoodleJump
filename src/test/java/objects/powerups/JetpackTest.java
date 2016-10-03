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

public class JetpackTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private Jetpack jetpack;
    private double jetpackBoost = Whitebox.getInternalState(Jetpack.class, "BOOST");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(spriteFactory.getJetpackSprite()).thenReturn(null);
        when(loggerFactory.createLogger(Jetpack.class)).thenReturn(null);

        jetpack = new Jetpack(serviceLocator, 0, 0);
    }

    @After
    public void finish() {
        jetpack = null;
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        jetpack.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(jetpack, "owner");
        assertThat(owner.equals(doodle), is(true));
    }

    @Test
    public void testGetBoost() throws Exception {
        jetpack.collidesWith(doodle);

        double boost = jetpack.getBoost();
        assertThat(boost == jetpackBoost, is(true));
    }

    @Test
    public void testGetType() {
        PassiveType x = jetpack.getType();
        assertThat(x.equals(PassiveType.constant), is(true));
    }

}
