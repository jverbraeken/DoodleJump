package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.IJumpable;
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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SpringShoesTest {

    private IConstants constants;
    private IDoodle doodle;
    private ILoggerFactory loggerFactory;
    private IServiceLocator serviceLocator;
    private ISpriteFactory spriteFactory;

    private SpringShoes springShoes;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        constants = mock(IConstants.class);
        doodle = mock(IDoodle.class);
        loggerFactory = mock(ILoggerFactory.class);
        serviceLocator = mock(IServiceLocator.class);
        spriteFactory = mock(ISpriteFactory.class);

        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(spriteFactory.getSpringShoesSprite()).thenReturn(null);
        when(loggerFactory.createLogger(SpringShoes.class)).thenReturn(null);

        springShoes = new SpringShoes(serviceLocator, 0, 0);
    }

    @After
    public void finish() {
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        springShoes.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner.equals(doodle), is(true));
    }

}
