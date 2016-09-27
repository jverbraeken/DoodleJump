package objects.block;


import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.Block;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.Platform;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import system.Game;
import system.IServiceLocator;
import system.ServiceLocator;
import rendering.IRenderer;
import rendering.Renderer;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

import resources.sprites.ISprite;
import resources.sprites.Sprite;

import org.mockito.Mockito;

import java.util.Set;

/**
 * Test class for the Block class.
 */
public class BlockTest {

    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IJumpable jumpobject = Mockito.mock(IJumpable.class);
    ISprite sprite = mock(ISprite.class);
    ISprite sprite2 = mock(ISprite.class);
    IDoodle doodle = mock(IDoodle.class);
    IBlock block;
    IPlatform platform = mock(Platform.class);



    @Before
    public void init() throws Exception {
        servicelocator = mock(IServiceLocator.class);
        ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
        IRenderer renderer = mock(IRenderer.class);
        when(spriteFactory.getPlatformSprite1()).thenReturn(sprite);
        when(sprite.getWidth()).thenReturn(30);
        when(sprite.getHeight()).thenReturn(50);
        when(servicelocator.getSpriteFactory()).thenReturn(spriteFactory);
        SpriteFactory.register(servicelocator);
       sprite = mock(ISprite.class);



        block = Whitebox.invokeConstructor(Block.class, servicelocator);
        IDoodle doodle = mock(IDoodle.class);
    }

    /**
     * Tests if an element is present in the set of IGameObjects after being added.
     *
     */
    @Test
    public void testAddElement() {
        IGameObject gObject = mock(IGameObject.class);
        block.addElement(gObject);
        assertTrue(block.getElements().contains(gObject));
    }

    /**
     *  Test if the jumpobject is present in the set of IGameObjects after being added to that set.
     *  It also tests if the topJumpable is indeed the jumpobject that has been added.
     */
    @Test
    public void testGetTopJumpable() {

        block.addElement(jumpobject);
        assertEquals(jumpobject, block.getTopJumpable());
        assertTrue(block.getElements().contains(jumpobject));
    }

    /**
     * Tests if a given double has been added to the current Y position of a GameObject.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testAddYPos() throws Exception {
      IPlatform platform = Whitebox.invokeConstructor(Platform.class, servicelocator, 0, 0);

       double i = 500.00;
        platform.setYPos(i);
        System.out.println(platform.getYPos());
        block.addElement(platform);
        block.addYPos(i);
        System.out.println(platform.getYPos());
        assertEquals(1000.0, platform.getYPos(), 0.001);

    }

    /**
     * Tests if 2 platforms truly collide with each other. If so, the platform that collides with the given platform
     * has been removed from the set of IGameObjects.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testCollisionCheck() throws Exception {
        IPlatform platform1 = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 400);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 420, 430);
        //IBlock block2 = PowerMockito.spy(Whitebox.invokeConstructor(Block.class, servicelocator));
        block.addElement(platform1);
        //assertTrue(block.getElements().contains(platform1));
        Whitebox.invokeMethod(block, "platformCollideCheck", platform2);
        assertFalse(block.getElements().contains(platform1));

    }

    /**
     * Tests that when 2 platforms do not collide with each other, the platform that is checked against the given platform
     * will not be removed from the set of IGameObjects.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testCollisionCheck2() throws Exception {
        IPlatform platform1 = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 400);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 500, 430);
        //IBlock block2 = PowerMockito.spy(Whitebox.invokeConstructor(Block.class, servicelocator));
        block.addElement(platform1);
        //assertTrue(block.getElements().contains(platform1));
        Whitebox.invokeMethod(block, "platformCollideCheck", platform2);
        assertTrue(block.getElements().contains(platform1));
        System.out.println(Game.HEIGHT);
    }


    /**
     * Tests when a GameObject is below the height of the game, the objects are removed from the set of IGameObjects.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testCleanUpPlatforms() throws Exception {
        IPlatform platform1 = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 1100);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 500, 1500);
        //IBlock block2 = PowerMockito.spy(Whitebox.invokeConstructor(Block.class, servicelocator));
        block.addElement(platform1);
        block.addElement(platform2);
        assertTrue(block.getElements().contains(platform1));
        assertTrue(block.getElements().contains(platform2));
        Whitebox.invokeMethod(block, "cleanUpPlatforms");
        assertFalse(block.getElements().contains(platform1));
        assertFalse(block.getElements().contains(platform2));

    }

    /**
     * Tests when a GameObject is not below the screen, that object is still present in the set of IGameObjects.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testCleanUpPlatforms2() throws Exception {
        IPlatform platform1 = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 800);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 500, 1500);
        //IBlock block2 = PowerMockito.spy(Whitebox.invokeConstructor(Block.class, servicelocator));
        block.addElement(platform1);
        block.addElement(platform2);
        assertTrue(block.getElements().contains(platform1));
        assertTrue(block.getElements().contains(platform2));
        Whitebox.invokeMethod(block, "cleanUpPlatforms");
        assertTrue(block.getElements().contains(platform1));
        assertFalse(block.getElements().contains(platform2));
    }

    /**
     * Tests when the update method is called, all objects that are below the screen are removed from the set of
     * IGameObject.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testUpdate() throws Exception{
        double delta = 1.00;
        IPlatform platform1 = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 800);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 500, 1500);
        block.addElement(platform1);
        block.addElement(platform2);
        assertTrue(block.getElements().contains(platform1));
        assertTrue(block.getElements().contains(platform2));
        block.update(delta);
        assertTrue(block.getElements().contains(platform1));
        assertFalse(block.getElements().contains(platform2));
    }

    /**
     * Tests that for each element in the set of IGameObjects the render in their own class is called.
     */
    @Test
    public void testRender() {

        block.addElement(doodle);
        block.addElement(platform);
        block.render();
        verify(doodle).render();
        verify(platform).render();

    }
}
