package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import rendering.Color;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.Point;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test suite for the Popup class.
 */
public class PopupTest {

    private IButton button = mock(IButton.class);
    private IButtonFactory buttonFactory = mock(IButtonFactory.class);
    private IConstants constants = mock(IConstants.class);
    private IRenderable popup;
    private IRenderer renderer = mock(IRenderer.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ISprite sprite = mock(ISprite.class), background = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private String message = "Hello";

    @Before
    public void init() {
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(spriteFactory.getSprite(IRes.Sprites.popupBackground)).thenReturn(background);
        when(spriteFactory.getSprite(IRes.Sprites.popupOkButton)).thenReturn(sprite);
        when(buttonFactory.createOkPopupButton(anyDouble(), anyDouble(), any(Popup.class))).thenReturn(button);

        popup = new Popup(serviceLocator, message);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(popup, "serviceLocator"));
        assertEquals(message, Whitebox.getInternalState(popup, "message"));
        assertEquals(button, Whitebox.getInternalState(popup, "okButton"));

        verify(button, times(1)).register();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullInput() {
        IServiceLocator nullServiceLocator = null;
        new Popup(nullServiceLocator, message);
    }

    @Test
    public void testRender() {
        popup.render();
        verify(renderer, times(1)).drawSprite(background, new Point(0, 0));
        verify(renderer, times(1)).drawText(any(Point.class), anyString(), any(Color.class));
    }

}
