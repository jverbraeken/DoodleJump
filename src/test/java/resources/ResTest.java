package resources;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by Michael on 10/10/2016.
 */
public class ResTest {

    private IServiceLocator serviceLocator;
    private Res res;
    private ISprite sprite;
    private String filePath = "mockedSprite.jpg";
    private Map<IRes.Sprites, String> mockedSprites = new EnumMap<>(IRes.Sprites.class);
   // private enum MockedSprites {
     //   mock
    //}

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        sprite = mock(ISprite.class);
        res = Whitebox.invokeConstructor(Res.class);
    }

    @Test
    public void testRegisterNullInput() {
        thrown.expect(AssertionError.class);
        res.register(null);
    }

    @Test
    public void testGetSpritePath() {
        mockedSprites.put(IRes.Sprites.menu, filePath);
        Whitebox.setInternalState(res, "sprites", mockedSprites);
        assertEquals(filePath, res.getSpritePath(IRes.Sprites.menu));
    }
}
