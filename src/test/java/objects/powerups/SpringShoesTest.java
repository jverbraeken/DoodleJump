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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SpringShoesTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SpringShoes springShoes;

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(spriteFactory.getSpringShoesSprite()).thenReturn(null);
        when(loggerFactory.createLogger(SpringShoes.class)).thenReturn(logger);

        springShoes = new SpringShoes(serviceLocator, 0, 0);
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        springShoes.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner, is(doodle));
    }

    @Test
    public void testNoUses() throws Exception {
        springShoes.collidesWith(doodle);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(0));
    }

    @Test
    public void testPerformInvalid() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform("invalid");

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(0));
    }

    @Test
    public void testPerformOnce() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform("collision");

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(1));
    }

    @Test
    public void testPerformTwice() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform("collision");
        springShoes.perform("collision");

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(2));
    }

    @Test
    public void testPerformThrice() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform("collision");
        springShoes.perform("collision");
        springShoes.perform("collision");

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(3));
    }

    @Test
    public void testPerformThriceUnsetOwner() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform("collision");
        springShoes.perform("collision");
        springShoes.perform("collision");

        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner == null, is(true));
    }

}
