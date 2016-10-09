package objects.enemies;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.blocks.platform.Platform;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rendering.ICamera;
import rendering.IRenderer;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class EnemyTest {
    private IConstants constants;
    private IRenderer renderer;
    private IDoodle doodle;
    private IServiceLocator serviceLocator;
    private ISprite sprite;
    private Enemy enemy;

    /**
     * Initialize before every test.
     */
    @Before
    public void init() {
        constants = mock(IConstants.class);
        when(constants.getGameWidth()).thenReturn(1);

        renderer = mock(IRenderer.class);

        doodle = mock(IDoodle.class);

        serviceLocator = mock(IServiceLocator.class);
        ILogger logger = mock(ILogger.class);
        ILoggerFactory logFac = mock(ILoggerFactory.class);
        when(logFac.createLogger(Platform.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(logFac);

        when(serviceLocator.getConstants()).thenReturn(constants);
        when(constants.getGravityAcceleration()).thenReturn(1.5);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        sprite = mock(ISprite.class);
        enemy = new Enemy(serviceLocator, 1, 1, sprite);
    }

    /**
     * Test the getBoost method from Enemy.
     * @throws NoSuchFieldException if the field does not exist.
     * @throws IllegalAccessException if you can't acces that field.
     */
    @Test
    public void getBoostTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = Enemy.class.getDeclaredField("BOOST");
        field.setAccessible(true);

        assertThat(enemy.getBoost(), is(field.get(enemy)));
    }

    /**
     * Test rendering a normal enemy.
     */
    @Test
    public void renderNormalTest() {
        enemy.render();

        Mockito.verify(serviceLocator).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, -1, 1);
        assertThat(enemy.getOffSet(), is(-2));
    }

    /**
     * Test rendering a normal enemy after 2 times.
     */
    @Test
    public void renderTwoTest() {
        double startY = enemy.getYPos();
        enemy.render();
        enemy.render();

        Mockito.verify(serviceLocator, Mockito.times(2)).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, -3, 1);

        assertThat(enemy.getXPos(), is(-3d));
        assertThat(enemy.getYPos(), is(startY));
        assertThat(enemy.getOffSet(), is(-4));
    }

    /**
     * Test rendering a killed enemy.
     */
    @Test
    public void renderKilledTest() {
        enemy.setKilled(true);
        enemy.render();

        Mockito.verify(serviceLocator).getRenderer();
        Mockito.verify(renderer).drawSprite(sprite, 1, 1);
    }

    /**
     * Test rendering a killed enemy.
     */
    @Test
    public void renderKilledMultipleTest() {
        enemy.setKilled(true);
        for (int i = 0; i < 10; i ++){
            enemy.render();
        }

        Mockito.verify(serviceLocator, Mockito.times(10)).getRenderer();
        Mockito.verify(renderer, Mockito.times(10)).drawSprite(sprite, 1, 1);
    }

    @Test
    public void updateNotKilledTest() {
        enemy.setKilled(false);
        double startVSpeed = enemy.getVerticalSpeed();

        enemy.update(0);
        assertThat(enemy.getVerticalSpeed(), is(startVSpeed));
    }

    @Test
    public void updateKilledTest() {
        enemy.setKilled(true);
        double startVSpeed = enemy.getVerticalSpeed();
        double expectedVSpeed = startVSpeed + 1.5;

        enemy.update(0);
        assertThat(enemy.getVerticalSpeed(), is(expectedVSpeed));
        assertThat(enemy.getVerticalSpeed(), not(startVSpeed));

    }

    @Test
    public void collidesWithKillsEnemy(){
        when(doodle.getVerticalSpeed()).thenReturn(1d);
        when(doodle.isHitByEnemy()).thenReturn(false);
        enemy.collidesWith(doodle);

        assertThat(enemy.getKilled(), is(true));
        assertThat(enemy.getVerticalSpeed(), is(1d));

        Mockito.verify(doodle).collide(enemy);
    }

    @Test
    public void collidesWithKillsDoodle(){
        when(doodle.getVerticalSpeed()).thenReturn(-8d);
        enemy.collidesWith(doodle);

        Mockito.verify(doodle).setVerticalSpeed(-5d);
        Mockito.verify(doodle).setHitByEnemy(true);
    }

    /**
     * Check that gravity is applied to the Enemy.
     */
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
        assertThat(enemy.getKilled(), is(false));

        enemy.setKilled(true);
        assertThat(enemy.getKilled(), is(true));
    }

    @Test
    public void setKilledTest() {
        enemy.setKilled(true);
        assertThat(enemy.getKilled(), is(true));

        enemy.setKilled(false);
        assertThat(enemy.getKilled(), is(false));
        enemy.setKilled(false);
        assertThat(enemy.getKilled(), is(false));
    }

    @Test
    public void getOffSetTest() {
        assertThat(enemy.getOffSet(), is(0));

        enemy.render();
        assertThat(enemy.getOffSet(), is(-2));
    }

    @Test
    public void getVerticalSpeedTest() {
        assertThat(enemy.getVerticalSpeed(), is(0d));

        enemy.applyGravity();
        assertThat(enemy.getVerticalSpeed(), is(1.5));
    }
}
