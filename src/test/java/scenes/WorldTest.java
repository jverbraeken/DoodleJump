package scenes;

import objects.backgrounds.IBackgroundFactory;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import rendering.IDrawable;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;

public class WorldTest {
    private static World world;
    private static IBlock block1, block2, block3;
    private static IDrawable background;
    private static IDoodle doodle;

    @BeforeClass
    public static void init() {
        // Blocks
        block1 = mock(IBlock.class);
        when(block1.getYPos()).thenReturn(2d);
        block2 = mock(IBlock.class);
        when(block2.getYPos()).thenReturn(1d);
        block3 = mock(IBlock.class);
        when(block3.getYPos()).thenReturn(0d);

        IBlockFactory blockFactory = mock(IBlockFactory.class);
        when(blockFactory.createStartBlock()).thenReturn(block1);
        when(blockFactory.createBlock(2d)).thenReturn(block1);
        when(blockFactory.createBlock(1d)).thenReturn(block2);

        // Background

        background = mock(IDrawable.class);

        IBackgroundFactory backgroundFactory = mock(IBackgroundFactory.class);
        when(backgroundFactory.createBackground()).thenReturn(background);

        // Doodle

        doodle = mock(IDoodle.class);

        IDoodleFactory doodleFactory = mock(IDoodleFactory.class);
        when(doodleFactory.createDoodle()).thenReturn(doodle);

        // Audio

        IAudioManager audioManager = mock(IAudioManager.class);

        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getBlockFactory()).thenReturn(blockFactory);
        when(serviceLocator.getBackgroundFactory()).thenReturn(backgroundFactory);
        when(serviceLocator.getDoodleFactory()).thenReturn(doodleFactory);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        world = new World(serviceLocator);
    }

    @Test
    public void testRender() {
        world.paint();
        verify(block1).render();
        // These mocks cannot be verified because Powermockito cannot override the equals method
        // and thus the hashset removes all "duplicates"
        //      Also, this error might show up because you use argument matchers with methods that cannot be mocked.
        //      Following methods *cannot* be stubbed/verified: final/private/equals()/hashCode().

        // Mockito.verify(block2).render();
        // Mockito.verify(block3).render();
        verify(background).render();
        verify(doodle).render();
    }

    @Test
    public void testUpdate() {
        world.update(5);
        verify(block1).update();
        verify(doodle).update();
    }

    @Test
    public void testStart() {
    }

    @Test
    public void testStop() {
    }
}
