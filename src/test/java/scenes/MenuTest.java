package scenes;

import objects.backgrounds.IBackgroundFactory;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import rendering.IDrawable;
import resources.audio.IAudioManager;
import system.IServiceLocator;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class MenuTest {
    private static Menu menu;
    private static IDrawable background;
    private static IButton playButton;

    @BeforeClass
    public static void init() {
        // Background

        background = mock(IDrawable.class);

        IBackgroundFactory backgroundFactory = mock(IBackgroundFactory.class);
        when(backgroundFactory.createBackground()).thenReturn(background);

        // Button

        playButton = mock(IButton.class);

        IButtonFactory buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createPlayButton(anyInt(), anyInt())).thenReturn(playButton);

        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getBackgroundFactory()).thenReturn(backgroundFactory);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        menu = new Menu(serviceLocator);
    }

    @Test
    public void testRender() {
        menu.paint();
        verify(background).render();
        verify(playButton).render();
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testStart() {
    }

    @Test
    public void testStop() {
    }
}
