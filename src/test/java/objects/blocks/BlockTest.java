package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.Platform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the Block class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Platform.class)
public class BlockTest {

    private IServiceLocator serviceLocator;
    private IGameObject gameObject;
    private IGameObject gameObject2;
    private IJumpable jumpObject;
    private IBlock block;
    private IPlatform platform;
    private Set<IGameObject> set;
    private double random;

    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        gameObject = Mockito.mock(AGameObject.class);
        gameObject2 = Mockito.mock(AGameObject.class);
        jumpObject = Mockito.mock(IJumpable.class);
        platform = mock(Platform.class);
        set = new HashSet<>();
        random = Math.random();
    }

    /**
     * Tests if the getElements method has the set that is given as parameter to the constructor.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testGetElements() throws Exception {
        set.add(gameObject);
        block = Whitebox.invokeConstructor(Block.class, set, jumpObject);
        assertEquals(set, block.getElements());
    }

    /**
     * Test if the jump object is present in the set of IGameObjects after being added to that set.
     * It also tests if the topJumpable is indeed the jumpobject that has been added.
     */
    @Test
    public void testGetTopJumpable() throws Exception {
        block = Whitebox.invokeConstructor(Block.class, set, jumpObject);
        assertEquals(jumpObject, block.getTopJumpable());
    }

    /**
     * Tests that for each element in the set of IGameObjects the render in their own class is called.
     */
    @Test
    public void testRender() {
        set.add(gameObject);
        set.add(gameObject2);
        block = new Block(set, jumpObject);
        block.render();
        verify(gameObject, times(1)).render();
        verify(gameObject2, times(1)).render();
    }

    @Test
    public void testUpdate() {
        set.add(gameObject);
        set.add(gameObject2);
        block = new Block(set, jumpObject);
        block.update(random);
        verify(gameObject, times(1)).update(random);
        verify(gameObject2, times(1)).update(random);
    }

    @Test
    public void testRemove() {
        set.add(gameObject);
        set.add(gameObject2);
        block = new Block(set, jumpObject);
        block.removeElement(gameObject2);
        assertTrue(set.contains(gameObject));
        assertFalse(set.contains(gameObject2));
    }
}

