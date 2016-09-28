package objects.blocks;


import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
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

    private IServiceLocator serviceLocator;
    private IGameObject gameObject = Mockito.mock(AGameObject.class);
    private IJumpable jumpObject = Mockito.mock(IJumpable.class);
    private IBlock block;
    private IPlatform platform = mock(Platform.class);
    private Set<IGameObject> set = new HashSet<>();


    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
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
        block = Whitebox.invokeConstructor(Block.class, serviceLocator, set, jumpObject);
        assertEquals(set, block.getElements());
    }

    /**
     * Test if the jump object is present in the set of IGameObjects after being added to that set.
     * It also tests if the topJumpable is indeed the jumpobject that has been added.
     */
    @Test
    public void testGetTopJumpable() throws Exception {

        block = Whitebox.invokeConstructor(Block.class, serviceLocator, set, jumpObject);
        assertEquals(jumpObject, block.getTopJumpable());

    }

    /**
     * Tests that for each element in the set of IGameObjects the render in their own class is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testRender() throws Exception {
        set.add(platform);
        block = Whitebox.invokeConstructor(Block.class, serviceLocator, set, jumpObject);
        block.render();

        verify(platform).render();

    }
}

