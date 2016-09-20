package objects.block;


import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.IBlock;
import objects.blocks.Block;
import org.junit.BeforeClass;
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

/**
 * Created by Michael on 9/20/2016.
 */
public class BlockTest {

    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IBlock block;
    IRenderer renderer = Mockito.mock(Renderer.class);

    @Before
    public void init() throws Exception {
        servicelocator = Whitebox.invokeConstructor(ServiceLocator.class);
        block = Whitebox.invokeConstructor(Block.class);
        when(servicelocator.getRenderer()).thenReturn(renderer);
    }


    @Test
    public void testRender() throws Exception {
       block.render();
        verify(servicelocator).getRenderer();
    }
}
