package rendering;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class RendererTest {

    Font font = mock(Font.class);
    Font font50 = mock(Font.class);
    FontMetrics fontMetrics = mock(FontMetrics.class);
    Graphics2D graphics = mock(Graphics2D.class);
    ICamera camera = mock(ICamera.class);
    IConstants constants = mock(IConstants.class);
    IFileSystem fileSystem = mock(IFileSystem.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    Image image = mock(Image.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISprite sprite = mock(ISprite.class);

    Renderer renderer;

    int gameHeight = 10, gameWidth = 10;
    int stringWidth = 10;

    @Before
    public void init() throws Exception {
        when(constants.getGameHeight()).thenReturn(gameHeight);
        when(constants.getGameWidth()).thenReturn(gameWidth);
        when(fileSystem.getFont(anyString())).thenReturn(font);
        when(font.deriveFont(anyInt(), anyFloat())).thenReturn(font50);
        when(fontMetrics.stringWidth("foo")).thenReturn(stringWidth);
        when(fontMetrics.stringWidth("bar")).thenReturn(stringWidth);
        when(graphics.getFontMetrics()).thenReturn(fontMetrics);
        when(loggerFactory.createLogger(Renderer.class)).thenReturn(logger);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(sprite.getImage()).thenReturn(image);

        Whitebox.setInternalState(Renderer.class, "serviceLocator", serviceLocator);
        renderer = Whitebox.invokeConstructor(Renderer.class);
        Whitebox.setInternalState(renderer, "graphics", graphics);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetGraphicsBufferNull() {
        renderer.setGraphicsBuffer(null);
    }

    @Test
    public void testSetGraphicsBuffer() {
        renderer.setGraphicsBuffer(graphics);
        verify(graphics, times(2)).setRenderingHint(anyObject(), anyObject());
    }

    @Test
    public void testSetCamera() {
        renderer.setCamera(camera);
        ICamera actual = Whitebox.getInternalState(renderer, "camera");
        assertThat(actual, is(camera));
    }

    @Test
    public void testGetCamera() {
        Whitebox.setInternalState(renderer, "camera", camera);
        ICamera actual = renderer.getCamera();
        assertThat(actual, is(camera));
    }

    @Test
    public void testClear() {
        renderer.clear();
        verify(graphics, times(1)).clearRect(0, 0, gameWidth, gameHeight);
    }

    @Test
    public void testDrawRectangle() {
        renderer.drawRectangle(1, 1, 10, 10);
        verify(graphics, times(1)).drawRect(1, 1, 10, 10);
    }

    @Test
    public void testDrawSprite() {
        renderer.drawSprite(sprite, 1, 1);
        verify(graphics, times(1)).drawImage(image, 1, 1, null);
    }

    @Test
    public void testDrawSpriteRotate() {
        renderer.drawSprite(sprite, 1, 1, 90);
        verify(graphics, times(1)).rotate(90);
        verify(graphics, times(1)).drawImage(image, 0, 0, null);
        verify(graphics, times(1)).rotate(-90);
    }

    @Test
    public void testDrawSpriteWidthHeight() {
        renderer.drawSprite(sprite, 1, 1, 10, 10);
        verify(graphics, times(1)).drawImage(image, 1, 1, 10, 10, null);
    }

    @Test
    public void testDrawSpriteWidthHeightRotate() {
        renderer.drawSprite(sprite, 1, 1, 10, 10, 90);
        verify(graphics, times(1)).rotate(90);
        verify(graphics, times(1)).drawImage(image, 0, 0, 10, 10, null);
        verify(graphics, times(1)).rotate(-90);
    }

    @Test
    public void testDrawRectangleHUD() {
        renderer.drawRectangleHUD(1, 1, 10, 10);
        verify(graphics, times(1)).drawRect(1, 1, 10, 10);
    }

    @Test
    public void testDrawSpriteHUD() {
        renderer.drawSpriteHUD(sprite, 10, 10);
        verify(graphics, times(1)).drawImage(image, 10, 10, null);
    }

    @Test
    public void testDrawSpriteHUDWidthHeight() {
        renderer.drawSpriteHUD(sprite, 10, 10, 1, 1);
        verify(graphics, times(1)).drawImage(image, 10, 10, 1, 1, null);
    }

    @Test
    public void testDrawText() {
        renderer.drawText(1, 1, "foo");
        verify(graphics, times(1)).drawString("foo", 1, 1);
    }

    @Test
    public void testDrawTextTextAlignmentLeft() {
        renderer.drawText(1, 1, "bar", TextAlignment.left);
        verify(graphics, times(1)).drawString("bar", 1, 1);
    }

    @Test
    public void testDrawTextTextAlignmentCenter() {
        renderer.drawText(1, 1, "foo", TextAlignment.center);
        verify(graphics, times(1)).drawString("foo", 1 - (stringWidth / 2), 1);
    }

    @Test
    public void testDrawTextTextAlignmentRight() {
        renderer.drawText(1, 1, "bar", TextAlignment.right);
        verify(graphics, times(1)).drawString("bar", 1 - stringWidth, 1);
    }

    @Test
    public void testDrawTextGraphic() {
        renderer.drawText(1, 1, "foo", rendering.Color.black);
        verify(graphics, times(1)).setColor(rendering.Color.black.getColor());
        verify(graphics, times(1)).drawString("foo", 1, 1);
    }

    @Test
    public void testDrawTextAll() {
        renderer.drawText(1, 1, "bar", TextAlignment.left, rendering.Color.black);
        verify(graphics, times(1)).setColor(rendering.Color.black.getColor());
        verify(graphics, times(1)).drawString("bar", 1, 1);
    }

    @Test
    public void testDrawTextHUD() {
        renderer.drawTextHUD(1, 1, "foo");
        verify(graphics, times(1)).drawString("foo", 1, 1);
    }

    @Test
    public void testDrawTextTextAlignmentLeftHUD() {
        renderer.drawTextHUD(1, 1, "bar", TextAlignment.left);
        verify(graphics, times(1)).drawString("bar", 1, 1);
    }

    @Test
    public void testDrawTextTextAlignmentCenterHUD() {
        renderer.drawTextHUD(1, 1, "foo", TextAlignment.center);
        verify(graphics, times(1)).drawString("foo", 1 - (stringWidth / 2), 1);
    }

    @Test
    public void testDrawTextTextAlignmentRightHUD() {
        renderer.drawTextHUD(1, 1, "bar", TextAlignment.right);
        verify(graphics, times(1)).drawString("bar", 1 - stringWidth, 1);
    }

    @Test
    public void testDrawTextGraphicHUD() {
        renderer.drawTextHUD(1, 1, "foo", rendering.Color.black);
        verify(graphics, times(1)).setColor(rendering.Color.black.getColor());
        verify(graphics, times(1)).drawString("foo", 1, 1);
    }

    @Test
    public void testDrawTextAllHUD() {
        renderer.drawTextHUD(1, 1, "bar", TextAlignment.left, rendering.Color.black);
        verify(graphics, times(1)).setColor(rendering.Color.black.getColor());
        verify(graphics, times(1)).drawString("bar", 1, 1);
    }

    @Test
    public void testFillRectangle() {
        renderer.fillRectangle(1, 1, 10, 10, rendering.Color.black);
        verify(graphics, times(1)).fillRect(1, 1, 10, 10);
        verify(graphics, times(1)).setColor(rendering.Color.black.getColor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteNull() {
        renderer.drawSprite(null, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteNullAngle() {
        renderer.drawSprite(null, 1, 1, 45);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteNullWidthHeight() {
        renderer.drawSprite(null, 1, 1, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteNullWidthHeightAngle() {
        renderer.drawSprite(null, 1, 1, 10, 10, 45);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteHUDNull() {
        renderer.drawSpriteHUD(null, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrawSpriteHUDNullWidthNull() {
        renderer.drawSpriteHUD(null, 1, 1, 10, 10);
    }

}
