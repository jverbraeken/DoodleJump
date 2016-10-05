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

public class SpringShoesTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SpringShoes springShoes;
    private double springShoesBoost = Whitebox.getInternalState(SpringShoes.class, "BOOST");

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);

        when(constants.getGameWidth()).thenReturn(100);
        when(spriteFactory.getSpringShoesSprite()).thenReturn(null);
        when(loggerFactory.createLogger(SpringShoes.class)).thenReturn(null);

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
    public void testGetBoost1() throws Exception {
        springShoes.collidesWith(doodle);

        //double boost = springShoes.getBoost();
        //assertThat(boost, is(springShoesBoost));
    }

    @Test
    public void testGetBoost1SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(1));
    }

    @Test
    public void testGetBoost2() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();

        //double boost = springShoes.getBoost();
        //assertThat(boost, is(springShoesBoost));
    }

    @Test
    public void testGetBoost2SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();
        //springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(2));
    }

    @Test
    public void testGetBoost3() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();
        //springShoes.getBoost();

        //double boost = springShoes.getBoost();
        //assertThat(boost, is(springShoesBoost));
    }

    @Test
    public void testGetBoost3SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();
        //springShoes.getBoost();
        //springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(3));
    }

    @Test
    public void testGetBoost3UnsetsOwner() throws Exception {
        springShoes.collidesWith(doodle);
        //springShoes.getBoost();
        //springShoes.getBoost();
        //springShoes.getBoost();

        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner == null, is(true));
    }

}
