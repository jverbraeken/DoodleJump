package objects.block;


import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.Platform;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test class for the Block class.
 */
public class BlockTest {

    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IJumpable jumpobject = Mockito.mock(IJumpable.class);
    IBlock block;
    IPlatform platform = mock(Platform.class);
    Set<IGameObject> set = new HashSet<>();



    @Before
    public void init() throws Exception {
        servicelocator = mock(IServiceLocator.class);
    }

    /**
     * Tests if the getElements method has the set that is given as parameter to the constructor.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testGetElements() throws Exception {
        IGameObject gObject = mock(IGameObject.class);
        set.add(gObject);
        block = Whitebox.invokeConstructor(Block.class, servicelocator, set, jumpobject);
        assertEquals(set, block.getElements());
    }

    /**
     *  Test if the jumpobject is present in the set of IGameObjects after being added to that set.
     *  It also tests if the topJumpable is indeed the jumpobject that has been added.
     */
    @Test
    public void testGetTopJumpable() throws Exception{

        block = Whitebox.invokeConstructor(Block.class, servicelocator, set, jumpobject);
        assertEquals(jumpobject, block.getTopJumpable());

    }

    /**
     * Tests that for each element in the set of IGameObjects the render in their own class is called.
     * @throws Exception
     *                  throws an exception when the private constructor can not be called or when an exception is thrown
     *                  in the constructor.
     */
    @Test
    public void testRender() throws Exception {
        set.add(platform);
        block = Whitebox.invokeConstructor(Block.class, servicelocator, set, jumpobject);
        block.render();

        verify(platform).render();

    }
}
