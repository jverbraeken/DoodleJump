package objects.block.platform;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.PlatformFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import resources.sprites.SpriteFactory;
import system.IServiceLocator;

import static org.junit.Assert.assertTrue;

/**
 * Created by 'cornel on 22-9-2016.
 */
public class PlatformTest {
    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IJumpable jumpobject = Mockito.mock(IJumpable.class);
    IBlock block;
    IPlatform platform;


    @Before
    public void init() throws Exception {
        servicelocator = Whitebox.invokeConstructor(IServiceLocator.class);
        PlatformFactory.register(servicelocator);
        SpriteFactory.register(servicelocator);
        SpriteFactory s = (SpriteFactory) servicelocator.getSpriteFactory();
        
        PlatformFactory p = (PlatformFactory) servicelocator.getPlatformFactory();
        //assertTrue(servicelocator
         //       == p.getServicelocator());
        platform = p.createPlatform(50, 50);

    }

    @Test
    public void testAddElement() throws Exception {
        assertTrue(1 == 1);
    }
}
