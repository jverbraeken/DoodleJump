package objects.powerups;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.blocks.platform.Platform;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.IRes;
import resources.animations.IAnimation;
import resources.animations.IAnimationFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(World.class)
public class PropellerTest {

    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IAnimationFactory animationFactory = mock(IAnimationFactory.class);
    private World world = mock(World.class);

    private Propeller propeller;
    private IAnimation spritePack = mock(IAnimation.class);

    @Before
    public void init() throws Exception {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);

        when(constants.getGameWidth()).thenReturn(100);
        when(doodle.getXPos()).thenReturn(0d);
        when(doodle.getYPos()).thenReturn(0d);
        when(doodle.getWorld()).thenReturn(world);
        when(loggerFactory.createLogger(Propeller.class)).thenReturn(logger);
        when(sprite.getHeight()).thenReturn(10);
        when(sprite.getWidth()).thenReturn(10);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(serviceLocator.getAnimationFactory()).thenReturn(animationFactory);
        when(animationFactory.getAnimation(Matchers.<IRes.Animations>any())).thenReturn(spritePack);
        Whitebox.setInternalState(world, "newUpdatables", new ArrayList<>());

        propeller = new Propeller(serviceLocator, new Point(0, 0), 1);
        when(spritePack.getLength()).thenReturn(3);
        when(spritePack.getFromIndex(anyInt())).thenReturn(sprite);
    }

    @Test
    public void testCollidesWithSetOwner() {
        propeller.collidesWith(doodle);
        Object owner = Whitebox.getInternalState(propeller, "owner");
        assertThat(owner, is(doodle));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCollidesWithNull() {
        propeller.collidesWith(null);
    }

    @Test
    public void testPerform() {
        propeller.collidesWith(doodle);

        propeller.perform(PowerupOccasion.constant);
        verify(doodle, times(1)).setVerticalSpeed(anyDouble());
    }

    @Test
    public void testPerformInvalidOccasion() {
        propeller.collidesWith(doodle);
        propeller.perform(PowerupOccasion.collision);
        verify(doodle, times(0)).setVerticalSpeed(anyDouble());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerformNoOwner() {
        propeller.perform(PowerupOccasion.constant);
    }

    @Test
    public void testRenderNoOwner() {
        propeller.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
        verify(doodle, times(0)).getXPos();
        verify(doodle, times(0)).getYPos();
    }

    @Test
    public void testRenderWithOwner() {
        propeller.collidesWith(doodle);
        propeller.render();
        verify(renderer, times(1)).drawSprite(sprite, new Point(0, 0));
    }

    @Test
    public void testUpdateWithOwner() {
        propeller.collidesWith(doodle);
        propeller.update(0d);
        int timer = Whitebox.getInternalState(propeller, "timer");
        assertThat(timer > 0, is(true));
    }

    @Test
    public void testUpdateWithOwner_Animation() {
        propeller.collidesWith(doodle);
        int currentIndex = Whitebox.getInternalState(propeller, "spriteIndex");
        for (int i = 0; i < Powerups.propeller.getAnimationRefreshRate(1); i++) {
            propeller.update(0d);
        }
        int newIndex = Whitebox.getInternalState(propeller, "spriteIndex");
        assertThat(newIndex, is(currentIndex + 1));
    }

    @Test
    public void testUpdateEnding() {
        propeller.collidesWith(doodle);
        Whitebox.setInternalState(propeller, "timer", Powerups.propeller.getMaxTimeInAir(1));
        propeller.update(0d);
        verify(doodle, times(1)).removePowerup(propeller);
    }

    @Test
    public void testUpdateFalling() {
        propeller.collidesWith(doodle);
        propeller.update(0d);

        propeller.update(0d);
    }

}
