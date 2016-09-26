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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
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
 * Created by Michael on 9/20/2016.
 */
public class BlockTest {

    IServiceLocator servicelocator;
    IGameObject gameobject = Mockito.mock(AGameObject.class);
    IJumpable jumpobject = Mockito.mock(IJumpable.class);
    ISprite sprite = mock(ISprite.class);
    IBlock block;
    IPlatform platform;


    @Before
    public void init() throws Exception {
        servicelocator = mock(IServiceLocator.class);
        ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
        when(spriteFactory.getPlatformSprite1()).thenReturn(sprite);
        when(sprite.getWidth()).thenReturn(30);
        when(sprite.getHeight()).thenReturn(50);
        when(servicelocator.getSpriteFactory()).thenReturn(spriteFactory);
        SpriteFactory.register(servicelocator);
       sprite = mock(ISprite.class);


        platform = mock(Platform.class);
        block = Whitebox.invokeConstructor(Block.class, servicelocator);

    }


    @Test
    public void testAddElement() throws Exception {

        block.addElement(platform);
        assertTrue(block.getElements().contains(platform));
    }

    @Test
    public void testGetTopJumpable() throws Exception {

        block.addElement(jumpobject);
        assertEquals(jumpobject, block.getTopJumpable());
    }

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

    @Test
    public void testCollisionCheck() throws Exception{
        IPlatform platform = Whitebox.invokeConstructor(Platform.class, servicelocator, 400, 400);
        IPlatform platform2 = Whitebox.invokeConstructor(Platform.class, servicelocator, 420, 430);
        //IBlock block2 = PowerMockito.spy(Whitebox.invokeConstructor(Block.class, servicelocator));
        block.addElement(platform);
        Whitebox.invokeMethod(block, "platformCollideCheck", platform2);
        assertFalse(block.getElements().contains(platform));

    }

}
