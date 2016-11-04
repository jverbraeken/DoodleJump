package objects.enemies;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.blocks.platform.Platform;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.IRes;
import resources.animations.IAnimation;
import resources.animations.IAnimationFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;
import java.awt.Point;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class EnemyTest {

    ILogger logger =  mock(ILogger.class);
    IConstants constants = mock(IConstants.class);
    IRenderer renderer = mock(IRenderer.class);
    IDoodle doodle = mock(IDoodle.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISprite sprite = mock(ISprite.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IAnimationFactory animationFactory = mock(IAnimationFactory.class);
    IAnimation animation = mock(IAnimation.class);

    Enemy enemy;

    @Before
    public void init() {
        when(constants.getGameWidth()).thenReturn(1);
        when(constants.getGravityAcceleration()).thenReturn(1.5);
        when(loggerFactory.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getAnimationFactory()).thenReturn(animationFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(animationFactory.getAnimation(Matchers.<IRes.Animations>any())).thenReturn(animation);
        when(animation.getFromIndex(anyInt())).thenReturn(mock(ISprite.class));

        enemy = new Enemy(serviceLocator, new Point(1, 1), Enemies.ordinaryMonster);
    }

    @Test
    public void getBoostTest() throws NoSuchFieldException, IllegalAccessException {
        assertThat(enemy.getBoost(), is(Enemies.ordinaryMonster.getBoost()));
    }

    @Test
    public void renderNormalTest() {
        enemy.render();

        verify(serviceLocator).getRenderer();
        verify(renderer).drawSprite(Matchers.<ISprite>any(), eq(new Point(1, 1)));
    }

    @Test
    public void updateTwoTest() {
        enemy.update(0d);
        enemy.update(0d);

        assertThat(enemy.getXPos(), is(-3d));
        assertThat(enemy.getYPos(), is(1.0));
        assertThat(enemy.getOffSet(), is(-4));
    }

    @Test
    public void testUpdateDeath() {
        Whitebox.setInternalState(enemy, "alive", false);
        enemy.update(0d);

        verify(constants, times(1)).getGravityAcceleration();
    }

    @Test
    public void testUpdateChangeMovingDirection() {
        Whitebox.setInternalState(enemy, "movingDirection", 1);

        int offset = (int) Enemies.ordinaryMonster.getMovingDistance() * 2;
        Whitebox.setInternalState(enemy, "offset", offset);

        enemy.update(0d);
        int actual = Whitebox.getInternalState(enemy, "movingDirection");
        assertThat(actual, is(0));
    }

    @Test
    public void testUpdateChangeMovingDirection2() {
        Whitebox.setInternalState(enemy, "movingDirection", 0);

        int offset = (int) Enemies.ordinaryMonster.getMovingDistance() * -2;
        Whitebox.setInternalState(enemy, "offset", offset);

        enemy.update(0d);
        int actual = Whitebox.getInternalState(enemy, "movingDirection");
        assertThat(actual, is(1));
    }

    @Test
    public void renderKilledMultipleTest() {
        enemy.setAlive(false);
        for (int i = 0; i < 10; i ++){
            enemy.render();
        }

        verify(serviceLocator, Mockito.times(10)).getRenderer();
        verify(renderer, Mockito.times(10)).drawSprite(Matchers.<ISprite>any(), eq(new Point(1, 1)));
    }

    @Test
    public void updateAliveTest() {
        enemy.setAlive(true);
        double startVSpeed = enemy.getVerticalSpeed();

        enemy.update(0);
        assertThat(enemy.getVerticalSpeed(), is(startVSpeed));

    }

    @Test
    public void collidesWithKillsEnemy(){
        when(doodle.getVerticalSpeed()).thenReturn(1d);
        when(doodle.isAlive()).thenReturn(true);
        enemy.collidesWith(doodle);

        assertThat(enemy.isAlive(), is(false));
        assertThat(enemy.getVerticalSpeed(), is(1d));

        verify(doodle).collide(enemy);
    }

    @Test
    public void collidesWithKillsDoodle(){
        when(doodle.getVerticalSpeed()).thenReturn(-8d);
        enemy.collidesWith(doodle);

        verify(doodle).setVerticalSpeed(-5d);
        verify(doodle).setAlive(false);
        assertThat(doodle.isAlive(), is(false));
    }

    @Test
    public void applyGravityTest() {
        double oldYpos = enemy.getYPos();
        double oldVerticalSpeed = enemy.getVerticalSpeed();
        enemy.applyGravity();

        assertThat(enemy.getYPos(), is(oldYpos + 1.5));
        assertThat(enemy.getVerticalSpeed(), is(oldVerticalSpeed + 1.5));
    }

    @Test
    public void getKilledTest() {
        assertThat(enemy.isAlive(), is(true));

        enemy.setAlive(false);
        assertThat(enemy.isAlive(), is(false));
    }

    @Test
    public void setKilledTest() {
        enemy.setAlive(false);
        assertThat(enemy.isAlive(), is(false));

        enemy.setAlive(true);
        assertThat(enemy.isAlive(), is(true));
        enemy.setAlive(true);
        assertThat(enemy.isAlive(), is(true));
    }

    @Test
    public void getOffSetTest() {
        assertThat(enemy.getOffSet(), is(0));

        enemy.update(0);
        assertThat(enemy.getOffSet(), is(-2));
    }

    @Test
    public void getVerticalSpeedTest() {
        assertThat(enemy.getVerticalSpeed(), is(0d));

        enemy.applyGravity();
        assertThat(enemy.getVerticalSpeed(), is(1.5));
    }

}
