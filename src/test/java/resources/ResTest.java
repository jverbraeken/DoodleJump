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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
    private static final String SPRITE_PATH = "sprites/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Object aNull;

    /**
     * Initialising the variables for the test cases.
     *
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
     *
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
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDefaultSkinButtons() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.menu), is(SPRITE_PATH + "menu@2x.png"));
        assertThat(insertedSprites.get(Sprites.play), is(SPRITE_PATH + "play@2x.png"));
        assertThat(insertedSprites.get(Sprites.multiplayer), is(SPRITE_PATH + "multiplayer@2x.png"));
        assertThat(insertedSprites.get(Sprites.playAgain), is(SPRITE_PATH + "playagain@2x.png"));
        assertThat(insertedSprites.get(Sprites.resume), is(SPRITE_PATH + "resume@2x.png"));
        assertThat(insertedSprites.get(Sprites.scoreButton), is(SPRITE_PATH + "scores-on@2x.png"));
        assertThat(insertedSprites.get(Sprites.chooseMode), is(SPRITE_PATH + "mode-button2@2x.png"));
        assertThat(insertedSprites.get(Sprites.shop), is(SPRITE_PATH + "shop@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the covers.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.background), is(SPRITE_PATH + "bck@2x.png"));
        assertThat(insertedSprites.get(Sprites.pauseCover), is(SPRITE_PATH + "pause-cover-3@2x.png"));
        assertThat(insertedSprites.get(Sprites.startCover), is(SPRITE_PATH + "Default@2x.png"));
        assertThat(insertedSprites.get(Sprites.shopCover), is(SPRITE_PATH + "pause-cover-4@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the doodle.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftAscend), is(SPRITE_PATH + "lik-left@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftDescend), is(SPRITE_PATH + "lik-left-odskok@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightAscend), is(SPRITE_PATH + "lik-right@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightDescend), is(SPRITE_PATH + "lik-right-odskok@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the kill screen.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinKillScreen() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.gameOver), is(SPRITE_PATH + "gameover@2x.png"));
        assertThat(insertedSprites.get(Sprites.killScreenBottom), is(SPRITE_PATH + "kill-bottom@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the numbers.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinNumbers() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.pause), is(SPRITE_PATH + "pause.png"));
        assertThat(insertedSprites.get(Sprites.zero), is(SPRITE_PATH + "0.png"));
        assertThat(insertedSprites.get(Sprites.one), is(SPRITE_PATH + "1.png"));
        assertThat(insertedSprites.get(Sprites.two), is(SPRITE_PATH + "2.png"));
        assertThat(insertedSprites.get(Sprites.three), is(SPRITE_PATH + "3.png"));
        assertThat(insertedSprites.get(Sprites.four), is(SPRITE_PATH + "4.png"));
        assertThat(insertedSprites.get(Sprites.five), is(SPRITE_PATH + "5.png"));
        assertThat(insertedSprites.get(Sprites.six), is(SPRITE_PATH + "6.png"));
        assertThat(insertedSprites.get(Sprites.seven), is(SPRITE_PATH + "7.png"));
        assertThat(insertedSprites.get(Sprites.eight), is(SPRITE_PATH + "8.png"));
    }

    /**
     * Tests if the method inserts the sprites for the platforms.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testsetDefaultSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.platform1), is(SPRITE_PATH + "platform-green@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformHorizontal), is(SPRITE_PATH + "platform-blue@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformVertical), is(SPRITE_PATH + "platform-gray@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken1), is(SPRITE_PATH + "platform-broken1@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken2), is(SPRITE_PATH + "platform-broken2@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken3), is(SPRITE_PATH + "platform-broken3@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken4), is(SPRITE_PATH + "platform-broken4@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the powerups.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDefaultSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.propeller), is(SPRITE_PATH + "powerup-propeller@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack), is(SPRITE_PATH + "powerup-jetpack@2x.png"));
        assertThat(insertedSprites.get(Sprites.spring), is(SPRITE_PATH + "powerup-spring@2x.png"));
        assertThat(insertedSprites.get(Sprites.springUsed), is(SPRITE_PATH + "powerup-spring-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.doubleSpring), is(SPRITE_PATH + "powerup-double-spring@2x.png"));
        assertThat(insertedSprites.get(Sprites.doubleSpringUsed), is(SPRITE_PATH + "powerup-double-spring-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.titaniumSpring), is(SPRITE_PATH + "powerup-titanium-spring@2x.png"));
        assertThat(insertedSprites.get(Sprites.titaniumSpringUsed), is(SPRITE_PATH + "powerup-titanium-spring-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.springShoes), is(SPRITE_PATH + "powerup-springshoes-3@2x.png"));
        assertThat(insertedSprites.get(Sprites.trampoline), is(SPRITE_PATH + "powerup-trampoline@2x.png"));
        assertThat(insertedSprites.get(Sprites.trampolineUsed), is(SPRITE_PATH + "powerup-trampoline-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.circusCannon), is(SPRITE_PATH + "powerup-circusCannon@2x.png"));
        assertThat(insertedSprites.get(Sprites.circusCannonUsed), is(SPRITE_PATH + "powerup-circusCannon-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.rocketLauncher), is(SPRITE_PATH + "rocketlauncher-unused.png"));
        assertThat(insertedSprites.get(Sprites.rocketLauncherUsed), is(SPRITE_PATH + "rocketlauncher-used.png"));
        assertThat(insertedSprites.get(Sprites.shield), is(SPRITE_PATH + "powerup-shield@2x.png"));
        assertThat(insertedSprites.get(Sprites.sizeUp), is(SPRITE_PATH +"powerup-size-up@2x.png"));
        assertThat(insertedSprites.get(Sprites.sizeDown), is(SPRITE_PATH + "powerup-size-down@2x.png"));
    }

    @Test
    public void testSetDefaultSkinPropellerUsed() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.propeller0), is(SPRITE_PATH + "propeller-0@2x.png"));
        assertThat(insertedSprites.get(Sprites.propeller1), is(SPRITE_PATH + "propeller-1@2x.png"));
        assertThat(insertedSprites.get(Sprites.propeller2), is(SPRITE_PATH + "propeller-2@2x.png"));
    }

    @Test
    public void testSetDefaultSkinJetpackUsed() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.jetpack0), is(SPRITE_PATH + "jetpack-0@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack1), is(SPRITE_PATH + "jetpack-1@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack2), is(SPRITE_PATH + "jetpack-2@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack3), is(SPRITE_PATH + "jetpack-3@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack4), is(SPRITE_PATH + "jetpack-4@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack5), is(SPRITE_PATH + "jetpack-5@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack6), is(SPRITE_PATH + "jetpack-6@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack7), is(SPRITE_PATH + "jetpack-7@2x.png"));
        assertThat(insertedSprites.get(Sprites.jetpack8), is(SPRITE_PATH + "jetpack-8@2x.png"));
    }

    @Test
    public void testSetDefaultSkinSpaceRocketUsed() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.spaceRocket0), is(SPRITE_PATH + "rocket-0@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket1), is(SPRITE_PATH + "rocket-1@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket2), is(SPRITE_PATH + "rocket-2@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket3), is(SPRITE_PATH + "rocket-3@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket4), is(SPRITE_PATH + "rocket-4@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket5), is(SPRITE_PATH + "rocket-5@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket6), is(SPRITE_PATH + "rocket-6@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket7), is(SPRITE_PATH + "rocket-7@2x.png"));
        assertThat(insertedSprites.get(Sprites.spaceRocket8), is(SPRITE_PATH + "rocket-8@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the score screen.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testResetScoreScreen() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.scoreScreenBottom), is(SPRITE_PATH + "high-scores-bottom@2x.png"));
        assertThat(insertedSprites.get(Sprites.scoreScreenLeft), is(SPRITE_PATH + "high-scores-left@2x.png"));
        assertThat(insertedSprites.get(Sprites.scoreScreenTop), is(SPRITE_PATH + "high-scores-top@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the top bar.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDefaultSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.scoreBar), is(SPRITE_PATH + "scorebar.png"));
    }

    /**
     * Tests if the method inserts the sprites for the icons in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDefaultSkinIcons() throws Exception {
        Whitebox.invokeMethod(res, "setDefaultSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.horizontalOnlyMode), is(SPRITE_PATH + "only-horizontal-mode@4x.png"));
        assertThat(insertedSprites.get(Sprites.regularMode), is(SPRITE_PATH + "regular-mode@4x.png"));
        assertThat(insertedSprites.get(Sprites.darknessMode), is(SPRITE_PATH + "darkness-mode@4x.png"));
        assertThat(insertedSprites.get(Sprites.verticalOnlyMode), is(SPRITE_PATH + "only-vertical-mode@4x.png"));
        assertThat(insertedSprites.get(Sprites.spaceMode), is(SPRITE_PATH + "space-mode@4x.png"));
        assertThat(insertedSprites.get(Sprites.underwaterMode), is(SPRITE_PATH + "underwater-mode@4x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the covers in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.background), is(SPRITE_PATH + "space-bck@2x.png"));
        assertThat(insertedSprites.get(Sprites.startCover), is(SPRITE_PATH + "space-Default@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the doodle in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftAscend), is(SPRITE_PATH + "space-left@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftDescend), is(SPRITE_PATH + "space-left-odskok@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightAscend), is(SPRITE_PATH + "space-right@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightDescend), is(SPRITE_PATH + "space-right-odskok@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the platforms in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.platform1), is(SPRITE_PATH + "space-platform@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the powerups in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.trampoline), is(SPRITE_PATH + "space-powerup-trampoline@2x.png"));
        assertThat(insertedSprites.get(Sprites.trampolineUsed), is(SPRITE_PATH + "space-powerup-trampoline-used@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the top bar in the space game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetSpaceSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setSpaceSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.scoreBar), is(SPRITE_PATH + "space-scoreBar@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the covers in the underwater game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testUnderwaterSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.background), is(SPRITE_PATH + "underwater-bck2@2x.png"));
        assertThat(insertedSprites.get(Sprites.startCover), is(SPRITE_PATH + "underwater-Default@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the doodle in the underwater game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testUnderwaterSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftAscend), is(SPRITE_PATH + "underwater-left@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftDescend), is(SPRITE_PATH + "underwater-left-odskok@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightAscend), is(SPRITE_PATH + "underwater-right@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightDescend), is(SPRITE_PATH + "underwater-right-odskok@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the platforms in the underwater game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.platform1), is(SPRITE_PATH + "underwater-platform@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the powerups in the underwater game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.trampoline), is(SPRITE_PATH + "underwater-powerup-trampoline@2x.png"));
        assertThat(insertedSprites.get(Sprites.trampolineUsed), is(SPRITE_PATH + "underwater-powerup-trampoline-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.spring), is(SPRITE_PATH + "underwater-powerup-spring@2x.png"));
        assertThat(insertedSprites.get(Sprites.springUsed), is(SPRITE_PATH + "underwater-powerup-spring-used@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the top bar.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetUnderwaterSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setUnderwaterSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.scoreBar), is(SPRITE_PATH + "underwater-scorebar@2x.png"));
    }

    /**
     * Tests if the setDefaultSkin method is called when the input is the regular game mode
     *
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
     *
     * @throws Exception throws an exception when the constructor can not be called the verify method returns an error.
     */
    @Test
    public void testSetSkinRegularDefault() throws Exception {
        Res mockedRes = spy(Whitebox.invokeConstructor(Res.class));
        doNothing().when(mockedRes, "setDefaultSkin");
        mockedRes.setSkin(Game.Modes.defaultmode);
        verifyPrivate(mockedRes).invoke("setDefaultSkin");
    }

    /**
     * Tests if the setDefaultSkin method is called when the input is the regular game mode
     *
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
     *
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
     * Tests if the setDarknessMode method is called when the input is the darkness game mode
     *
     * @throws Exception throws an exception when the constructor can not be called the verify method returns an error.
     */
    @Test
    public void testSetSkinDarkness() throws Exception {
        Res mockedRes = spy(Whitebox.invokeConstructor(Res.class));
        doNothing().when(mockedRes, "setDarknessSkin");
        mockedRes.setSkin(Game.Modes.darkness);
        verifyPrivate(mockedRes).invoke("setDarknessSkin");
    }

    /**
     * Tests if the method inserts the sprites for the top bar in the darkness game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDarknessSkinTopBar() throws Exception {
        Whitebox.invokeMethod(res, "setDarknessSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.scoreBar), is(SPRITE_PATH + "space-scorebar@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the covers in the darkness game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testDarknessSkinCovers() throws Exception {
        Whitebox.invokeMethod(res, "setDarknessSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.background), is(SPRITE_PATH + "darkness-bck@2x.png"));
        assertThat(insertedSprites.get(Sprites.startCover), is(SPRITE_PATH + "darkness-Default@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the doodle in the darkness game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testDarknessSkinDoodle() throws Exception {
        Whitebox.invokeMethod(res, "setDarknessSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftAscend), is(SPRITE_PATH + "ghost-left@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleLeftDescend), is(SPRITE_PATH + "ghost-left-odskok@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightAscend), is(SPRITE_PATH + "ghost-right@2x.png"));
        assertThat(insertedSprites.get(Sprites.greenDoodleRightDescend), is(SPRITE_PATH + "ghost-right-odskok@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the platforms in the darkness game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDarknessSkinPlatforms() throws Exception {
        Whitebox.invokeMethod(res, "setDarknessSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.platform1), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformHorizontal), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformVertical), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken1), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken2), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken3), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platformBroken4), is(SPRITE_PATH + "invisible-platform@2x.png"));
        assertThat(insertedSprites.get(Sprites.platform4), is(SPRITE_PATH + "space-platform@2x.png"));
    }

    /**
     * Tests if the method inserts the sprites for the powerups in the darkness game mode.
     *
     * @throws Exception throws an exception when a private method can not be called.
     */
    @Test
    public void testSetDarknessSkinPowerUps() throws Exception {
        Whitebox.invokeMethod(res, "setDarknessSkin");
        insertedSprites = Whitebox.getInternalState(res, "sprites");
        assertThat(insertedSprites.get(Sprites.trampoline), is(SPRITE_PATH + "powerup-trampoline@2x.png"));
        assertThat(insertedSprites.get(Sprites.trampolineUsed), is(SPRITE_PATH + "powerup-trampoline-used@2x.png"));
        assertThat(insertedSprites.get(Sprites.spring), is(SPRITE_PATH + "powerup-spring@2x.png"));
        assertThat(insertedSprites.get(Sprites.springUsed), is(SPRITE_PATH + "powerup-spring-used@2x.png"));
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
