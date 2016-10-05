package resources.sprites;

import logging.ILoggerFactory;
import objects.doodles.DoodleBehavior.MovementBehavior;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import system.IServiceLocator;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpriteFactory.class)
public class SpriteFactoryTest {
    private ISpriteFactory spriteFactory;

    @Before
    public void init() throws Exception {
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        Whitebox.setInternalState(SpriteFactory.class, "sL", serviceLocator);

        spriteFactory = spy(Whitebox.invokeConstructor(SpriteFactory.class));
        doReturn(mock(ISprite.class)).when(spriteFactory, "loadISprite", anyObject());
    }

    @Test
    public void TestGetMenuButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.menu);
        ISprite result = spriteFactory.getMenuButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPauseButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.pause);
        ISprite result = spriteFactory.getPauseButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPlayButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.play);
        ISprite result = spriteFactory.getPlayButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestPlayAgainGetButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.playagain);
        ISprite result = spriteFactory.getPlayAgainButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetResumeButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.resume);
        ISprite result = spriteFactory.getResumeButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestChooseModeGetButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.chooseMode);
        ISprite result = spriteFactory.getChooseModeButtonSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetBackgroundButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.background);
        ISprite result = spriteFactory.getBackground();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPauseCoverButtonSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.pauseCover);
        ISprite result = spriteFactory.getPauseCoverSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetStartCoverSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.startCover);
        ISprite result = spriteFactory.getStartCoverSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDoodleSpriteLeft() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.doodleLeftAscend);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.doodleLeftDescend);
        ISprite[] result = spriteFactory.getDoodleSprite(MovementBehavior.Directions.Left);
        ISprite[] expected = new ISprite[2];
        expected[0] = mock;
        expected[1] = mock2;
        assertThat(Arrays.equals(expected, result), is(true));
    }

    @Test
    public void TestGetDoodleSpriteRight() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.doodleRightAscend);
        ISprite mock2 = mock(ISprite.class);
        doReturn(mock2).when(spriteFactory, "getSprite", IRes.Sprites.doodleRightDescend);
        ISprite[] result = spriteFactory.getDoodleSprite(MovementBehavior.Directions.Right);
        ISprite[] expected = new ISprite[2];
        expected[0] = mock;
        expected[1] = mock2;
        assertThat(Arrays.equals(expected, result), is(true));
    }

    @Test
    public void TestGetGameOverSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.gameOver);
        ISprite result = spriteFactory.getGameOverSprite();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetKillScreenBottomSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.killScreenBottom);
        ISprite result = spriteFactory.getKillScreenBottomSprite();
        assertEquals(mock, result);
    }

    // MONSTERS


    @Test
    public void TestGetPuddingMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPuddingMonsterSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster2);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPuddingMonsterSprite3() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster3);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPuddingMonsterSprite4() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster4);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetPuddingMonsterSprite5() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster5);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetTwinMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.twinMonster);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetThreeEyedMonsterSprite1() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.threeEyedMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetThreeEyedMonsterSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.threeEyedMonster2);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetThreeEyedMonsterSprite3() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.threeEyedMonster3);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetThreeEyedMonsterSprite4() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.threeEyedMonster4);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetThreeEyedMonsterSprite5() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.threeEyedMonster5);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetVampireMonsterSprite1() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.vampireMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetVampireMonsterSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.vampireMonster2);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetVampireMonsterSprite3() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.vampireMonster3);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetVampireMonsterSprite4() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.vampireMonster4);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetVampireMonsterSprite5() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.vampireMonster5);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetOrdinaryMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.ordinaryMonster);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetCactusMonsterSprite1() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.cactusMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetCactusMonsterSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.cactusMonster2);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetFiveFeetMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.fiveFeetMonster);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetLowFiveFeetMonsterSprite1() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.lowFiveFeetMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetLowFiveFeetMonsterSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.lowFiveFeetMonster2);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void getSmallMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.smallMonster);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    @Test
    public void TestGetMonsterSprite() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.puddingMonster1);
        ISprite result = spriteFactory.getPuddingMonsterSprite1();
        assertEquals(mock, result);
    }

    // NUMBERS


    @Test(expected = IllegalArgumentException.class)
    public void TestGetDigitSpriteNegative() throws Exception {
        spriteFactory.getDigitSprite(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestGetDigitSpriteMoreThan9() throws Exception {
        spriteFactory.getDigitSprite(10);
    }

    @Test
    public void TestGetDigitSprite0() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.zero);
        ISprite result = spriteFactory.getDigitSprite(0);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite1() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.one);
        ISprite result = spriteFactory.getDigitSprite(1);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite2() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.two);
        ISprite result = spriteFactory.getDigitSprite(2);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite3() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.three);
        ISprite result = spriteFactory.getDigitSprite(3);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite4() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.four);
        ISprite result = spriteFactory.getDigitSprite(4);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite5() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.five);
        ISprite result = spriteFactory.getDigitSprite(5);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite6() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.six);
        ISprite result = spriteFactory.getDigitSprite(6);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite7() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.seven);
        ISprite result = spriteFactory.getDigitSprite(7);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite8() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.eight);
        ISprite result = spriteFactory.getDigitSprite(8);
        assertEquals(mock, result);
    }

    @Test
    public void TestGetDigitSprite9() throws Exception {
        ISprite mock = mock(ISprite.class);
        doReturn(mock).when(spriteFactory, "getSprite", IRes.Sprites.nine);
        ISprite result = spriteFactory.getDigitSprite(9);
        assertEquals(mock, result);
    }
}