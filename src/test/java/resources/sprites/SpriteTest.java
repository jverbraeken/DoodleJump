package resources.sprites;

import org.junit.Before;
import org.junit.Test;

import org.powermock.reflect.Whitebox;

import java.awt.image.BufferedImage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

public class SpriteTest {

    private ISprite sprite;
    private BufferedImage bufferedImage;
    private String spriteName;
    private int width;
    private int height;

    @Before
    public void init() throws Exception {
        spriteName = "SpriteMock";
        width = 50;
        height = 70;
        bufferedImage = mock(BufferedImage.class);
        when(bufferedImage.getWidth()).thenReturn(width);
        when(bufferedImage.getHeight()).thenReturn(height);
        sprite = Whitebox.invokeConstructor(Sprite.class, "SpriteMock", bufferedImage);
    }

    @Test
    public void testGetName() {
        assertThat(sprite.getName(), is(spriteName));
    }

    @Test
    public void testGetImage() {
        assertThat(sprite.getImage(), is(bufferedImage));
    }

    @Test
    public void testGetWidth() {
        assertThat(sprite.getWidth(), is(width));
    }

    @Test
    public void testGetHeight() {
        assertThat(sprite.getHeight(), is(height));
    }

    @Test
    public void testGetRatio() {
        double expected = (double) height / (double) width;
        assertThat(sprite.getRatio(), is(expected));
    }
}
