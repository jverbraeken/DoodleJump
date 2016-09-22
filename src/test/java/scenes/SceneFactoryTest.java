package scenes;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SceneFactoryTest {
    private static SceneFactory sceneFactory;

    @BeforeClass
    public static void init() throws Exception {/*
        // Blocks
        IBlock block1 = mock(IBlock.class);
        when(block1.getYPos()).thenReturn(2d);
        IBlock block2 = mock(IBlock.class);
        when(block2.getYPos()).thenReturn(1d);
        IBlock block3 = mock(IBlock.class);
        when(block3.getYPos()).thenReturn(0d);

        IBlockFactory blockFactory = mock(IBlockFactory.class);
        when(blockFactory.createStartBlock()).thenReturn(block1);

        // Background

        // Doodle

        IDoodle doodle = mock(IDoodle.class);

        IDoodleFactory doodleFactory = mock(IDoodleFactory.class);
        when(doodleFactory.createDoodle()).thenReturn(doodle);

        // Button

        IButton playButton = mock(IButton.class);

        IButtonFactory buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createPlayButton(anyInt(), anyInt())).thenReturn(playButton);

        // Audio

        IAudioManager audioManager = mock(IAudioManager.class);

        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getBlockFactory()).thenReturn(blockFactory);
        when(serviceLocator.getDoodleFactory()).thenReturn(doodleFactory);
        when(serviceLocator.getAudioManager()).thenReturn(audioManager);
        SceneFactory.register(serviceLocator);
        sceneFactory = Whitebox.invokeConstructor(SceneFactory.class);*/
    }

    @Test
    public void testNewMenu() {
        /*IScene menu = sceneFactory.newMenu();
        Assert.assertTrue(menu instanceof Menu);*/
    }

    @Test
    public void testNewWorld() {
       /* IScene world = sceneFactory.newWorld();
        Assert.assertTrue(world instanceof World);*/
    }
}
