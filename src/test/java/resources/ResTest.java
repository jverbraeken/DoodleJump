package resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes.Sprites;
import system.Game;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Test class for Res.class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Res.class)
public class ResTest {

    private IServiceLocator serviceLocator;
    private Res res;
    private String filePath = "mockedSprite.jpg";
    private Map<Sprites, String> mockedSprites = new EnumMap<>(Sprites.class);
    private Map<Sprites, String> insertedSprites;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Object aNull;

    /**
     * Initialising the variables for the test cases.
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Before
    public void setUp() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        res = Whitebox.invokeConstructor(Res.class);
    }

    /**
     * Tests if the register method returns an assertion error if the input is a null object.
     * @throws NullPointerException throws an exception when the input is null.
     */
    @Test(expected = AssertionError.class)
    public void testRegisterNullInput() {
        thrown.expect(IllegalArgumentException.class);
        Res.register(null);
    }

    /**
     * Tests if the method returns the correct path to the file.
     */
    @Test
    public void testGetSpritePath() {
        mockedSprites.put(Sprites.menu, filePath);
        Whitebox.setInternalState(res, "sprites", mockedSprites);
        assertEquals(filePath, res.getSpritePath(Sprites.menu));
    }

    /**
     * Tests if the method inserts the sprites for the buttons.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDefaultSkinButtons() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.menu));
        assertTrue(insertedSprites.containsKey(Sprites.play));
        assertTrue(insertedSprites.containsKey(Sprites.playAgain));
        assertTrue(insertedSprites.containsKey(Sprites.resume));
        assertTrue(insertedSprites.containsKey(Sprites.scoreButton));
        assertTrue(insertedSprites.containsKey(Sprites.chooseMode));
    }

    /**
     * Tests if the method inserts the sprites for the covers.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
        assertTrue(insertedSprites.containsKey(Sprites.pauseCover));
    }

    /**
     * Tests if the method inserts the sprites for the doodle.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    /**
     * Tests if the method inserts the sprites for the kill screen.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinKillScreen() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.gameOver));
        assertTrue(insertedSprites.containsKey(Sprites.killScreenBottom));
    }

    /**
     * Tests if the method inserts the sprites for the numbers.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinNumbers() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
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

    /**
     * Tests if the method inserts the sprites for the platforms.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
        assertTrue(insertedSprites.containsKey(Sprites.platformHorizontal));
        assertTrue(insertedSprites.containsKey(Sprites.platformVertical));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken1));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken2));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken3));
        assertTrue(insertedSprites.containsKey(Sprites.platformBroken4));
    }

    /**
     * Tests if the method inserts the sprites for the powerups.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
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

    /**
     * Tests if the method inserts the sprites for the score screen.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testResetScoreScreen() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenBottom));
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenLeft));
        assertTrue(insertedSprites.containsKey(Sprites.scoreScreenTop));
    }

    /**
     * Tests if the method inserts the sprites for the top bar.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }

    /**
     * Tests if the method inserts the sprites for the icons in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinIcons() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.storyMode));
        assertTrue(insertedSprites.containsKey(Sprites.regularMode));
        assertTrue(insertedSprites.containsKey(Sprites.darknessMode));
        assertTrue(insertedSprites.containsKey(Sprites.invertMode));
        assertTrue(insertedSprites.containsKey(Sprites.spaceMode));
        assertTrue(insertedSprites.containsKey(Sprites.underwaterMode));
    }

    /**
     * Tests if the method inserts the sprites for the covers in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
    }

    /**
     * Tests if the method inserts the sprites for the doodle in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    /**
     * Tests if the method inserts the sprites for the platforms in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
    }

    /**
     * Tests if the method inserts the sprites for the powerups in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.trampoline));
        assertTrue(insertedSprites.containsKey(Sprites.trampolineUsed));
    }

    /**
     * Tests if the method inserts the sprites for the top bar in the space game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }

    /**
     * Tests if the method inserts the sprites for the covers in the underwater game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testUnderwaterSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.background));
        assertTrue(insertedSprites.containsKey(Sprites.startCover));
    }

    /**
     * Tests if the method inserts the sprites for the doodle in the underwater game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testUnderwaterSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleLeftDescend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightAscend));
        assertTrue(insertedSprites.containsKey(Sprites.doodleRightDescend));
    }

    /**
     * Tests if the method inserts the sprites for the platforms in the underwater game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.platform1));
    }

    /**
     * Tests if the method inserts the sprites for the powerups in the underwater game mode.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.trampoline));
        assertTrue(insertedSprites.containsKey(Sprites.trampolineUsed));
        assertTrue(insertedSprites.containsKey(Sprites.spring));
        assertTrue(insertedSprites.containsKey(Sprites.springUsed));
    }

    /**
     * Tests if the method inserts the sprites for the top bar.
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertTrue(insertedSprites.containsKey(Sprites.scoreBar));
    }

    /**
     * Tests if the setDefaultSkin method is called when the input is the regular game mode
     * @throws Exception throws an exception when the constructor can not be called the verify method returns an error.
     */
    @Test
    public void testSetSkinRegular() throws Exception {
        Res mockedRes = spy(Whitebox.invokeConstructor(Res.class));
        doNothing().when(mockedRes, "setDefaultSkin");
        mockedRes.setSkin(Game.Modes.regular);
        verifyPrivate(mockedRes).invoke("setDefaultSkin");
    }

    /**
     * Tests if the setDefaultSkin method is called when the input is the regular game mode
     * @throws Exception throws an exception when the constructor can not be called the verify method returns an error.
     */
    @Test
    public void testSetSkinSpace() throws Exception {
        Res mockedRes = spy(Whitebox.invokeConstructor(Res.class));
        doNothing().when(mockedRes, "setSpaceSkin");
        mockedRes.setSkin(Game.Modes.space);
        verifyPrivate(mockedRes).invoke("setSpaceSkin");
    }

    /**
     * Tests if the setUnderwaterSkin method is called when the input is the underwater game mode
     * @throws Exception throws an exception when the constructor can not be called the verify method returns an error.
     */
    @Test
    public void testSetSkinUnderwater() throws Exception {
        Res mockedRes = spy(Whitebox.invokeConstructor(Res.class));
        doNothing().when(mockedRes, "setUnderwaterSkin");
        mockedRes.setSkin(Game.Modes.underwater);
        verifyPrivate(mockedRes).invoke("setUnderwaterSkin");
    }

    /**
     * Tests if the setSkin method returns a NullPointerException if the input is a null object.
     */
    @Test
    public void testSetSkinNoGameMode() {
        thrown.expect(NullPointerException.class);
        res.setSkin(null);
    }

    /**
     * Cleaning the sprites map in the res object.
     */
    @After
    public void cleanUp() {
        aNull = null;
        Whitebox.setInternalState(res, "sprites", aNull);
    }

}
