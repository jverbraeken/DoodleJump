package resources.sprites;

import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import scenes.PauseScreenModes;
import system.IServiceLocator;

import java.util.Arrays;
import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpriteFactory.class)
public class SpriteFactoryTest {
    private ISpriteFactory spriteFactory;

    @Before
    public void init() throws Exception {
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        Whitebox.setInternalState(SpriteFactory.class, "serviceLocator", serviceLocator);

        spriteFactory = spy(Whitebox.invokeConstructor(SpriteFactory.class));
        doReturn(mock(ISprite.class)).when(spriteFactory, "loadISprite", anyObject());
    }

    @Test
    public void TestGetPauseCoverButtonSprite() throws Exception {
        ISprite mockedSprite = mock(ISprite.class);
        doReturn(mockedSprite).when(spriteFactory, "getSprite", IRes.Sprites.pauseCover);
        ISprite result = spriteFactory.getPauseCoverSprite(PauseScreenModes.mission);
        assertEquals(mockedSprite, result);
    }

    @Test
    public void TestGetPauseCoverButtonSprite2() throws Exception {
        ISprite mockedSprite = mock(ISprite.class);
        doReturn(mockedSprite).when(spriteFactory, "getSprite", IRes.Sprites.shopCover);
        ISprite result = spriteFactory.getPauseCoverSprite(PauseScreenModes.shop);
        assertEquals(mockedSprite, result);
    }

    @Test
    public void TestGetGreenDoodleSprite() throws Exception {
        ISprite mock1 = mock(ISprite.class);
        doReturn(mock1).when(spriteFactory, "getSprite", IRes.Sprites.greenDoodleLeftDescend);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.greenDoodleLeftAscend);
        ISprite mock3 = mock(ISprite.class);
        doReturn(mock3).when(spriteFactory, "getSprite", IRes.Sprites.greenDoodleRightDescend);
        ISprite mock4 = mock(ISprite.class);
        doReturn(mock4).when(spriteFactory, "getSprite", IRes.Sprites.greenDoodleRightAscend);

        ISprite[] result = spriteFactory.getGreenDoodleSprites();
        ISprite[] expected = new ISprite[4];
        expected[0] = mock1;
        expected[1] = mock2;
        expected[2] = mock3;
        expected[3] = mock4;
        assertThat(expected, is(result));
    }

    @Test
    public void TestGetRedDoodleSprite() throws Exception {
        ISprite mock1 = mock(ISprite.class);
        doReturn(mock1).when(spriteFactory, "getSprite", IRes.Sprites.redDoodleLeftDescend);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.redDoodleLeftAscend);
        ISprite mock3 = mock(ISprite.class);
        doReturn(mock3).when(spriteFactory, "getSprite", IRes.Sprites.redDoodleRightDescend);
        ISprite mock4 = mock(ISprite.class);
        doReturn(mock4).when(spriteFactory, "getSprite", IRes.Sprites.redDoodleRightAscend);

        ISprite[] result = spriteFactory.getRedDoodleSprites();
        ISprite[] expected = new ISprite[4];
        expected[0] = mock1;
        expected[1] = mock2;
        expected[2] = mock3;
        expected[3] = mock4;
        assertThat(expected, is(result));
    }

    @Test
    public void TestGetBlueDoodleSprite() throws Exception {
        ISprite mock1 = mock(ISprite.class);
        doReturn(mock1).when(spriteFactory, "getSprite", IRes.Sprites.blueDoodleLeftDescend);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.blueDoodleLeftAscend);
        ISprite mock3 = mock(ISprite.class);
        doReturn(mock3).when(spriteFactory, "getSprite", IRes.Sprites.blueDoodleRightDescend);
        ISprite mock4 = mock(ISprite.class);
        doReturn(mock4).when(spriteFactory, "getSprite", IRes.Sprites.blueDoodleRightAscend);

        ISprite[] result = spriteFactory.getBlueDoodleSprites();
        ISprite[] expected = new ISprite[4];
        expected[0] = mock1;
        expected[1] = mock2;
        expected[2] = mock3;
        expected[3] = mock4;
        assertThat(expected, is(result));
    }

    // NUMBERS
    @Test
    public void TestGetDigitSprites() throws Exception {
        ISprite mock0 = mock(ISprite.class);
        doReturn(mock0).when(spriteFactory, "getSprite", IRes.Sprites.zero);
        ISprite mock1 = mock(ISprite.class);
        doReturn(mock1).when(spriteFactory, "getSprite", IRes.Sprites.one);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.two);
        ISprite mock3 = mock(ISprite.class);
        doReturn(mock3).when(spriteFactory, "getSprite", IRes.Sprites.three);
        ISprite mock4 = mock(ISprite.class);
        doReturn(mock4).when(spriteFactory, "getSprite", IRes.Sprites.four);
        ISprite mock5 = mock(ISprite.class);
        doReturn(mock5).when(spriteFactory, "getSprite", IRes.Sprites.five);
        ISprite mock6 = mock(ISprite.class);
        doReturn(mock6).when(spriteFactory, "getSprite", IRes.Sprites.six);
        ISprite mock7 = mock(ISprite.class);
        doReturn(mock7).when(spriteFactory, "getSprite", IRes.Sprites.seven);
        ISprite mock8 = mock(ISprite.class);
        doReturn(mock8).when(spriteFactory, "getSprite", IRes.Sprites.eight);
        ISprite mock9 = mock(ISprite.class);
        doReturn(mock9).when(spriteFactory, "getSprite", IRes.Sprites.nine);

        ISprite[] result = spriteFactory.getDigitSprites();
        ISprite[] expected = new ISprite[10];
        expected[0] = mock0;
        expected[1] = mock1;
        expected[2] = mock2;
        expected[3] = mock3;
        expected[4] = mock4;
        expected[5] = mock5;
        expected[6] = mock6;
        expected[7] = mock7;
        expected[8] = mock8;
        expected[9] = mock9;

        assertThat(Arrays.equals(expected, result), is(true));
    }

    @Test
    public void TestGetPlatformBrokenSprite1() throws Exception {
        TestSprite(IRes.Sprites.platformBroken1, () -> spriteFactory.getPlatformBrokenSprite(1));
    }

    @Test
    public void TestGetPlatformBrokenSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformBroken2, () -> spriteFactory.getPlatformBrokenSprite(2));
    }

    @Test
    public void TestGetPlatformBrokenSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformBroken3, () -> spriteFactory.getPlatformBrokenSprite(3));
    }

    @Test
    public void TestGetPlatformBrokenSprite4() throws Exception {
        TestSprite(IRes.Sprites.platformBroken4, () -> spriteFactory.getPlatformBrokenSprite(4));
    }
    
    // COINS


    @Test(expected = IllegalArgumentException.class)
    public void TestGetCoinLessThan1() throws Exception {
        spriteFactory.getCoinSprite(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestGetCoinSpriteMoreThan10() throws Exception {
        spriteFactory.getCoinSprite(11);
    }

    @Test
    public void TestGetCoinSprite1() throws Exception {
        TestSprite(IRes.Sprites.coin1, () -> spriteFactory.getCoinSprite(1));
    }

    @Test
    public void TestGetCoinSprite2() throws Exception {
        TestSprite(IRes.Sprites.coin2, () -> spriteFactory.getCoinSprite(2));
    }

    @Test
    public void TestGetCoinSprite3() throws Exception {
        TestSprite(IRes.Sprites.coin3, () -> spriteFactory.getCoinSprite(3));
    }

    @Test
    public void TestGetCoinSprite4() throws Exception {
        TestSprite(IRes.Sprites.coin4, () -> spriteFactory.getCoinSprite(4));
    }

    @Test
    public void TestGetCoinSprite5() throws Exception {
        TestSprite(IRes.Sprites.coin5, () -> spriteFactory.getCoinSprite(5));
    }

    @Test
    public void TestGetCoinSprite6() throws Exception {
        TestSprite(IRes.Sprites.coin6, () -> spriteFactory.getCoinSprite(6));
    }

    @Test
    public void TestGetCoinSprite7() throws Exception {
        TestSprite(IRes.Sprites.coin7, () -> spriteFactory.getCoinSprite(7));
    }

    @Test
    public void TestGetCoinSprite8() throws Exception {
        TestSprite(IRes.Sprites.coin8, () -> spriteFactory.getCoinSprite(8));
    }

    @Test
    public void TestGetCoinSprite9() throws Exception {
        TestSprite(IRes.Sprites.coin9, () -> spriteFactory.getCoinSprite(9));
    }

    @Test
    public void TestGetCoinSprite10() throws Exception {
        TestSprite(IRes.Sprites.coin10, () -> spriteFactory.getCoinSprite(10));
    }

    // getFileName
    @Test
    public void TestGetFileName() throws Exception {
        final String filepath = "resources/Sprites/sprite.png";
        final String result = Whitebox.invokeMethod(spriteFactory, "getFileName", filepath);
        assertThat(result, is("sprite.png"));
    }

    @Test
    public void TestGetFileNameBlanco() throws Exception {
        final String filepath = "";
        final String result = Whitebox.invokeMethod(spriteFactory, "getFileName", filepath);
        assertThat(result, is(""));
    }

    // No bad weather checks, because Travis doesn't compile the assertions and thus no AssertionErrors will be thrown
    // and it's a private method, so there's less risk other people will use the method for things it isn't supposed to do

    /**
     * A method that simplies the testing of {@link SpriteFactory}.
     *
     * @param sprite   The sprite that should be returned by the getter
     * @param function The getter that should return {@code sprite}
     * @throws Exception Thrown when the method {@link SpriteFactory#getSprite(IRes.Sprites)} could not be found
     */
    private void TestSprite(IRes.Sprites sprite, Callable<ISprite> function) throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", sprite);
        ISprite result = function.call();
        assertEquals(mock, result);
    }

}
