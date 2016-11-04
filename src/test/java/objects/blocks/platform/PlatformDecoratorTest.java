package objects.blocks.platform;

import objects.IGameObject;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PlatformDecoratorTest {

    private IPlatform platform;
    private IServiceLocator serviceLocator;
    private PlatformDecorator platformDecorator;
    private IGameObject gameObject;
    private IDoodle doodle;
    private ISprite sprite;
    private double random;
    private double randomX;
    private double randomY;
    private double randomX2;
    private double randomY2;

    @Before
    public void setUp() throws Exception {
        platform = mock(IPlatform.class);
        serviceLocator = mock(IServiceLocator.class);
        gameObject = mock(IGameObject.class);
        doodle = mock(IDoodle.class);
        sprite = mock(ISprite.class);

        random = Math.random();
        randomX = Math.random();
        randomY = Math.random();
        randomX2 = Math.random();
        randomY2 = Math.random();

        when(platform.getBoost()).thenReturn(random);
        when(platform.getXPos()).thenReturn(randomX);
        when(platform.getYPos()).thenReturn(randomY);

        platformDecorator = new PlatformDecorator(serviceLocator, platform) {};
    }

    @Test
    public void testUpdate() {
        platformDecorator.update(random);
        verify(platform, times(1)).update(random);
    }

    @Test
    public void testGetBoost() {
        assertEquals(random, platformDecorator.getBoost(), 0.01);
    }

    @Test
    public void testAddXPos() {
        platformDecorator.addXPos(random);
        verify(platform, times(1)).addXPos(random);
    }

    @Test
    public void testAddYPos() {
        platformDecorator.addYPos(random);
        verify(platform, times(1)).addYPos(random);
    }

    @Test
    public void testCheckCollisions() {
        platformDecorator.checkCollision(gameObject);
        verify(platform, times(1)).checkCollision(gameObject);
    }

    @Test
    public void testCollidesWith() {
        platformDecorator.collidesWith(doodle);
        verify(platform, times(1)).collidesWith(doodle);
    }

    @Test
    public void testGetPoint() {
        Point point = new Point((int) randomX, (int) randomY);
        assertEquals(point, platformDecorator.getPoint());
    }

    @Test
    public void testSetHitBox() {
        platformDecorator.setHitBox((int) randomX, (int) randomY, (int) randomX2, (int) randomY2);
        verify(platform, times(1)).setHitBox((int) randomX, (int) randomY, (int) randomX2, (int) randomY2);
    }

    @Test
    public void testSetSprite() {
        platformDecorator.setSprite(sprite);
        verify(platform, times(1)).setSprite(sprite);
    }

    @Test
    public void testVerifyEnums() {
        platformDecorator.updateEnums(randomX, randomY);
        verify(platform, times(1)).updateEnums(randomX, randomY);
    }

    @Test
    public void testGetDirections() {
        platformDecorator.getDirections();
        verify(platform, times(1)).getDirections();
    }


}
