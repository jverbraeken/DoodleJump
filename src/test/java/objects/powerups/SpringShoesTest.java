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

public class SpringShoesTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private ISprite sprite = mock(ISprite.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SpringShoes springShoes;
    private double springShoesBoost = Whitebox.getInternalState(SpringShoes.class, "BOOST");

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
        when(doodle.getSprite()).thenReturn(sprite);
        when(spriteFactory.getSpringShoesSprite()).thenReturn(sprite);
        when(sprite.getWidth()).thenReturn(0);
        when(loggerFactory.createLogger(SpringShoes.class)).thenReturn(null);

        springShoes = new SpringShoes(serviceLocator, 0, 0);
    }

    @After
    public void finish() {
        springShoes = null;
    }

    @Test
    public void testCollidesWithSetOwner() throws Exception {
        springShoes.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner.equals(doodle), is(true));
    }

    @Test
    public void testNoUses() throws Exception {
        springShoes.collidesWith(doodle);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses == 0, is(true));
    }

    @Test
    public void testGetBoost1() throws Exception {
        springShoes.collidesWith(doodle);

        double boost = springShoes.getBoost();
        assertThat(boost == springShoesBoost, is(true));
    }

    @Test
    public void testGetBoost1SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses == 1, is(true));
    }

    @Test
    public void testGetBoost2() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();

        double boost = springShoes.getBoost();
        assertThat(boost == springShoesBoost, is(true));
    }

    @Test
    public void testGetBoost2SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();
        springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses == 2, is(true));
    }

    @Test
    public void testGetBoost3() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();
        springShoes.getBoost();

        double boost = springShoes.getBoost();
        assertThat(boost == springShoesBoost, is(true));
    }

    @Test
    public void testGetBoost3SetUses() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();
        springShoes.getBoost();
        springShoes.getBoost();

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses == 3, is(true));
    }

    @Test
    public void testGetBoost3UnsetsOwner() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.getBoost();
        springShoes.getBoost();
        springShoes.getBoost();

        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner == null, is(true));
    }

    @Test
    public void testGetType() {
        PassiveType type = springShoes.getType();
        assertThat(type.equals(PassiveType.collision), is(true));
    }

    @Test
    public void testRenderNoOwner() {
        springShoes.render();
        verify(renderer, times(1)).drawSprite(sprite, 0, 0);
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

    @Test
    public void testRenderWithOwner() {
        springShoes.collidesWith(doodle);

        springShoes.render();
        verify(renderer, times(1)).drawSprite(sprite, 0, 0);
        verify(doodle, times(1)).getXPos();
        verify(doodle, times(1)).getYPos();
    }

}
