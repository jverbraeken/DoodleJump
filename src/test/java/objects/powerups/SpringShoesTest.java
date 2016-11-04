package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SpringShoesTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private ISprite sprite = mock(ISprite.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    private SpringShoes springShoes;

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
        when(loggerFactory.createLogger(SpringShoes.class)).thenReturn(logger);
        when(sprite.getWidth()).thenReturn(0);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(doodle.getHitBox()).thenReturn(new double[]{0, 0, 0, 0});
        when(doodle.getYPos()).thenReturn(-2d);
        when(doodle.getLegsHeight()).thenReturn(0d);
        when(doodle.getVerticalSpeed()).thenReturn(1d);

        springShoes = new SpringShoes(serviceLocator, new Point(0, 0), 1);
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
        springShoes.perform(PowerupOccasion.constant);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(0));
    }

    @Test
    public void testPerformOnce() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform(PowerupOccasion.collision);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(1));
    }

    @Test
    public void testPerformTwice() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform(PowerupOccasion.collision);
        springShoes.perform(PowerupOccasion.collision);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(2));
    }

    @Test
    public void testPerformThrice() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform(PowerupOccasion.collision);
        springShoes.perform(PowerupOccasion.collision);
        springShoes.perform(PowerupOccasion.collision);

        int uses = Whitebox.getInternalState(springShoes, "uses");
        assertThat(uses, is(3));
    }

    @Test
    public void testPerformThriceUnsetOwner() throws Exception {
        springShoes.collidesWith(doodle);
        springShoes.perform(PowerupOccasion.collision);
        springShoes.perform(PowerupOccasion.collision);
        springShoes.perform(PowerupOccasion.collision);

        Object owner = Whitebox.getInternalState(springShoes, "owner");
        assertThat(owner == null, is(true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPerformNoOwner() {
        springShoes.perform(PowerupOccasion.collision);
    }

    @Test
    public void testRenderNoOwner() {
        springShoes.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCollidesWithNull() {
        springShoes.collidesWith(null);
    }

    @Test
    public void testRenderWithOwner() {
        springShoes.collidesWith(doodle);
        springShoes.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, -2));
    }

}
