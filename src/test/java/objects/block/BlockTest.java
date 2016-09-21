package objects.block;


import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.Block;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.Platform;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;
import system.ServiceLocator;
import rendering.IRenderer;
import rendering.Renderer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;

import java.util.Set;

/**
 * Created by Michael on 9/20/2016.
 */
public class BlockTest {

    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IJumpable jumpobject = Mockito.mock(IJumpable.class);
    IBlock block;
    IPlatform platform;


    @Before
    public void init() throws Exception {
        servicelocator = Whitebox.invokeConstructor(ServiceLocator.class);
        platform = Whitebox.invokeConstructor(Platform.class, servicelocator, 80, 900);
        block = Whitebox.invokeConstructor(Block.class, servicelocator);

    }


    @Test
    public void testAddElement() throws Exception {

        block.addElement(gameobject);
        assertTrue(block.getElements().contains(gameobject));
    }

    @Test
    public void testAddElement2() throws Exception {

        block.addElement(jumpobject);
        assertEquals(jumpobject, block.getTopJumpable());
    }

    @Test
    public void testAddYPos() {

    }

}
