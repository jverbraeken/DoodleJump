package resources;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;
import resources.IRes.Sprites;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by Michael on 10/10/2016.
 */
public class ResTest {

    private IServiceLocator serviceLocator;
    private Res res;
    private ISprite sprite;
    private String filePath = "mockedSprite.jpg";
    private Map<Sprites, String> mockedSprites = new EnumMap<>(Sprites.class);
    private Map<Sprites, String> insertedSprites;
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
        mockedSprites.put(Sprites.menu, filePath);
        Whitebox.setInternalState(res, "sprites", mockedSprites);
        assertEquals(filePath, res.getSpritePath(Sprites.menu));
    }

    @Test
    public void testResetSkinButtons() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.menu));
        assertTrue(insertedSprites.containsKey(Sprites.play));
        assertTrue(insertedSprites.containsKey(Sprites.playAgain));
        assertTrue(insertedSprites.containsKey(Sprites.resume));
        assertTrue(insertedSprites.containsKey(Sprites.scoreButton));
        assertTrue(insertedSprites.containsKey(Sprites.chooseMode));
    }

    @Test
    public void testResetSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
        assertTrue(insertedSprites.containsKey(Sprites.pauseCover));
    }

    @Test
    public void testResetSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    @Test
    public void testResetSkinKillScreen() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.gameOver));
        assertTrue(insertedSprites.containsKey(Sprites.killScreenBottom));
    }

    @Test
    public void testResetSkinNumbers() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.pause));
        assertTrue(insertedSprites.containsKey(Sprites.zero));
        assertTrue(insertedSprites.containsKey(Sprites.one));
        assertTrue(insertedSprites.containsKey(Sprites.two));
        assertTrue(insertedSprites.containsKey(Sprites.three));
        assertTrue(insertedSprites.containsKey(Sprites.four));
        assertTrue(insertedSprites.containsKey(Sprites.five));
        assertTrue(insertedSprites.containsKey(Sprites.six));
        assertTrue(insertedSprites.containsKey(Sprites.seven));
        assertTrue(insertedSprites.containsKey(Sprites.eight));
        assertTrue(insertedSprites.containsKey(Sprites.nine));
    }

    @Test
    public void testResetSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
        assertTrue(insertedSprites.containsKey(Sprites.platformHorizontal));
        assertTrue(insertedSprites.containsKey(Sprites.platformVertical));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken1));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken2));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken3));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken4));
    }

    @Test
    public void testResetSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.propeller));
        assertTrue(insertedSprites.containsKey(Sprites.jetpack));
        assertTrue(insertedSprites.containsKey(Sprites.spring));
        assertTrue(insertedSprites.containsKey(Sprites.springUsed));
        assertTrue(insertedSprites.containsKey(Sprites.springShoes));
        assertTrue(insertedSprites.containsKey(Sprites.trampoline));
        assertTrue(insertedSprites.containsKey(Sprites.trampolineUsed));
        assertTrue(insertedSprites.containsKey(Sprites.shield));
        assertTrue(insertedSprites.containsKey(Sprites.sizeUp));
        assertTrue(insertedSprites.containsKey(Sprites.sizeDown));
    }

    @Test
    public void testResetScoreScreen() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenBottom));
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenLeft));
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenTop));
    }

    @Test
    public void testResetSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }

    @Test
    public void testResetSkinIcons() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.storyMode));
        assertTrue(insertedSprites.containsKey(Sprites.regularMode));
        assertTrue(insertedSprites.containsKey(Sprites.darknessMode));
        assertTrue(insertedSprites.containsKey(Sprites.invertMode));
        assertTrue(insertedSprites.containsKey(Sprites.spaceMode));
        assertTrue(insertedSprites.containsKey(Sprites.underwaterMode));
    }

    @Test
    public void testSetSpaceSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
    }

    @Test
    public void testSetSpaceSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    @Test
    public void testSetSpaceSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
    }

    @Test
    public void testSetSpaceSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.trampoline));
        assertTrue(insertedSprites.containsKey(Sprites.trampolineUsed));
    }

    @Test
    public void testSetSpaceSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }

    @Test
    public void testUnderwaterSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
    }

    @Test
    public void testUnderwaterSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    @Test
    public void testSetUnderwaterSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
    }

    @Test
    public void testSetUnderwaterSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.trampoline));
        assertTrue(insertedSprites.containsKey(Sprites.trampolineUsed));
        assertTrue(insertedSprites.containsKey(Sprites.spring));
        assertTrue(insertedSprites.containsKey(Sprites.springUsed));
    }

    @Test
    public void testSetUnderwaterSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "resetSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }
    
}
