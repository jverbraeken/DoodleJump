package resources.sprites;

import logging.ILoggerFactory;
import objects.doodles.DoodleBehavior.MovementBehavior;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import system.IServiceLocator;

import java.util.Arrays;
import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

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

    @Test(expected = IllegalArgumentException.class)
    public void TestRegisterNull() {
        SpriteFactory.register(null);
    }

    @Test
    public void TestGetMenuButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.menu, () -> spriteFactory.getMenuButtonSprite());
    }

    @Test
    public void TestGetPauseButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.pause, () -> spriteFactory.getPauseButtonSprite());
    }

    @Test
    public void TestGetPlayButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.play, () -> spriteFactory.getPlayButtonSprite());
    }

    @Test
    public void TestPlayAgainGetButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.playAgain, () -> spriteFactory.getPlayAgainButtonSprite());
    }

    @Test
    public void TestGetResumeButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.resume, () -> spriteFactory.getResumeButtonSprite());
    }

    @Test
    public void TestChooseModeGetButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.chooseMode, () -> spriteFactory.getChooseModeButtonSprite());
    }

    @Test
    public void TestGetBackgroundButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.background, () -> spriteFactory.getBackground());
    }

    @Test
    public void TestGetPauseCoverButtonSprite() throws Exception {
        TestSprite(IRes.Sprites.pauseCover, () -> spriteFactory.getPauseCoverSprite());
    }

    @Test
    public void TestGetStartCoverSprite() throws Exception {
        TestSprite(IRes.Sprites.startCover, () -> spriteFactory.getStartCoverSprite());
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
        TestSprite(IRes.Sprites.gameOver, () -> spriteFactory.getGameOverSprite());
    }

    @Test
    public void TestGetKillScreenBottomSprite() throws Exception {
        TestSprite(IRes.Sprites.killScreenBottom, () -> spriteFactory.getKillScreenBottomSprite());
    }

    // MONSTERS


    @Test
    public void TestGetPuddingMonsterSprite() throws Exception {
        TestSprite(IRes.Sprites.puddingMonster1, () -> spriteFactory.getPuddingMonsterSprite1());
    }

    @Test
    public void TestGetPuddingMonsterSprite2() throws Exception {
        TestSprite(IRes.Sprites.puddingMonster2, () -> spriteFactory.getPuddingMonsterSprite2());
    }

    @Test
    public void TestGetPuddingMonsterSprite3() throws Exception {
        TestSprite(IRes.Sprites.puddingMonster3, () -> spriteFactory.getPuddingMonsterSprite3());
    }

    @Test
    public void TestGetPuddingMonsterSprite4() throws Exception {
        TestSprite(IRes.Sprites.puddingMonster4, () -> spriteFactory.getPuddingMonsterSprite4());
    }

    @Test
    public void TestGetPuddingMonsterSprite5() throws Exception {
        TestSprite(IRes.Sprites.puddingMonster5, () -> spriteFactory.getPuddingMonsterSprite5());
    }

    @Test
    public void TestGetTwinMonsterSprite() throws Exception {
        TestSprite(IRes.Sprites.twinMonster, () -> spriteFactory.getTwinMonsterSprite());
    }

    @Test
    public void TestGetThreeEyedMonsterSprite1() throws Exception {
        TestSprite(IRes.Sprites.threeEyedMonster1, () -> spriteFactory.getThreeEyedMonsterSprite1());
    }

    @Test
    public void TestGetThreeEyedMonsterSprite2() throws Exception {
        TestSprite(IRes.Sprites.threeEyedMonster2, () -> spriteFactory.getThreeEyedMonsterSprite2());
    }

    @Test
    public void TestGetThreeEyedMonsterSprite3() throws Exception {
        TestSprite(IRes.Sprites.threeEyedMonster3, () -> spriteFactory.getThreeEyedMonsterSprite3());
    }

    @Test
    public void TestGetThreeEyedMonsterSprite4() throws Exception {
        TestSprite(IRes.Sprites.threeEyedMonster4, () -> spriteFactory.getThreeEyedMonsterSprite4());
    }

    @Test
    public void TestGetThreeEyedMonsterSprite5() throws Exception {
        TestSprite(IRes.Sprites.threeEyedMonster5, () -> spriteFactory.getThreeEyedMonsterSprite5());
    }

    @Test
    public void TestGetVampireMonsterSprite1() throws Exception {
        TestSprite(IRes.Sprites.vampireMonster1, () -> spriteFactory.getVampireMonsterSprite1());
    }

    @Test
    public void TestGetVampireMonsterSprite2() throws Exception {
        TestSprite(IRes.Sprites.vampireMonster2, () -> spriteFactory.getVampireMonsterSprite2());
    }

    @Test
    public void TestGetVampireMonsterSprite3() throws Exception {
        TestSprite(IRes.Sprites.vampireMonster3, () -> spriteFactory.getVampireMonsterSprite3());
    }

    @Test
    public void TestGetVampireMonsterSprite4() throws Exception {
        TestSprite(IRes.Sprites.vampireMonster4, () -> spriteFactory.getVampireMonsterSprite4());
    }

    @Test
    public void TestGetVampireMonsterSprite5() throws Exception {
        TestSprite(IRes.Sprites.vampireMonster5, () -> spriteFactory.getVampireMonsterSprite5());
    }

    @Test
    public void TestGetOrdinaryMonsterSprite() throws Exception {
        TestSprite(IRes.Sprites.ordinaryMonster, () -> spriteFactory.getOrdinaryMonsterSprite());
    }

    @Test
    public void TestGetCactusMonsterSprite1() throws Exception {
        TestSprite(IRes.Sprites.cactusMonster1, () -> spriteFactory.getCactusMonsterSprite1());
    }

    @Test
    public void TestGetCactusMonsterSprite2() throws Exception {
        TestSprite(IRes.Sprites.cactusMonster2, () -> spriteFactory.getCactusMonsterSprite2());
    }

    @Test
    public void TestGetFiveFeetMonsterSprite() throws Exception {
        TestSprite(IRes.Sprites.fiveFeetMonster, () -> spriteFactory.getFiveFeetMonsterSprite());
    }

    @Test
    public void TestGetLowFiveFeetMonsterSprite1() throws Exception {
        TestSprite(IRes.Sprites.lowFiveFeetMonster1, () -> spriteFactory.getLowFiveFeetMonsterSprite1());
    }

    @Test
    public void TestGetLowFiveFeetMonsterSprite2() throws Exception {
        TestSprite(IRes.Sprites.lowFiveFeetMonster2, () -> spriteFactory.getLowFiveFeetMonsterSprite2());
    }

    @Test
    public void getSmallMonsterSprite() throws Exception {
        TestSprite(IRes.Sprites.smallMonster, () -> spriteFactory.getSmallMonsterSprite());
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
        TestSprite(IRes.Sprites.zero, () -> spriteFactory.getDigitSprite(0));
    }

    @Test
    public void TestGetDigitSprite1() throws Exception {
        TestSprite(IRes.Sprites.one, () -> spriteFactory.getDigitSprite(1));
    }

    @Test
    public void TestGetDigitSprite2() throws Exception {
        TestSprite(IRes.Sprites.two, () -> spriteFactory.getDigitSprite(2));
    }

    @Test
    public void TestGetDigitSprite3() throws Exception {
        TestSprite(IRes.Sprites.three, () -> spriteFactory.getDigitSprite(3));
    }

    @Test
    public void TestGetDigitSprite4() throws Exception {
        TestSprite(IRes.Sprites.four, () -> spriteFactory.getDigitSprite(4));
    }

    @Test
    public void TestGetDigitSprite5() throws Exception {
        TestSprite(IRes.Sprites.five, () -> spriteFactory.getDigitSprite(5));
    }

    @Test
    public void TestGetDigitSprite6() throws Exception {
        TestSprite(IRes.Sprites.six, () -> spriteFactory.getDigitSprite(6));
    }

    @Test
    public void TestGetDigitSprite7() throws Exception {
        TestSprite(IRes.Sprites.seven, () -> spriteFactory.getDigitSprite(7));
    }

    @Test
    public void TestGetDigitSprite8() throws Exception {
        TestSprite(IRes.Sprites.eight, () -> spriteFactory.getDigitSprite(8));
    }

    @Test
    public void TestGetDigitSprite9() throws Exception {
        TestSprite(IRes.Sprites.nine, () -> spriteFactory.getDigitSprite(9));
    }

    // PLATFORMS


    @Test
    public void TestGetPlatformSprite1() throws Exception {
        TestSprite(IRes.Sprites.platform1, () -> spriteFactory.getPlatformSprite1());
    }

    @Test
    public void TestGetPlatformSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformHorizontal, () -> spriteFactory.getPlatformSpriteHorizontal());
    }

    @Test
    public void TestGetPlatformSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformVertical, () -> spriteFactory.getPlatformSpriteVertical());
    }

    @Test
    public void TestGetPlatformSprite4() throws Exception {
        TestSprite(IRes.Sprites.platform4, () -> spriteFactory.getPlatformSprite4());
    }

    @Test
    public void TestGetPlatformSprite5() throws Exception {
        TestSprite(IRes.Sprites.platform5, () -> spriteFactory.getPlatformSprite5());
    }

    @Test
    public void TestGetPlatformSprite6() throws Exception {
        TestSprite(IRes.Sprites.platform6, () -> spriteFactory.getPlatformSprite6());
    }

    @Test
    public void TestGetPlatformSprite7() throws Exception {
        TestSprite(IRes.Sprites.platform7, () -> spriteFactory.getPlatformSprite7());
    }

    @Test
    public void TestGetPlatformSprite8() throws Exception {
        TestSprite(IRes.Sprites.platform8, () -> spriteFactory.getPlatformSprite8());
    }

    @Test
    public void TestGetPlatformSprite9() throws Exception {
        TestSprite(IRes.Sprites.platform9, () -> spriteFactory.getPlatformSprite9());
    }

    @Test
    public void TestGetPlatformBrokenSprite1() throws Exception {
        TestSprite(IRes.Sprites.platformBroken1, () -> spriteFactory.getPlatformBrokenSprite1());
    }

    @Test
    public void TestGetPlatformBrokenSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformBroken2, () -> spriteFactory.getPlatformBrokenSprite2());
    }

    @Test
    public void TestGetPlatformBrokenSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformBroken3, () -> spriteFactory.getPlatformBrokenSprite3());
    }

    @Test
    public void TestGetPlatformBrokenSprite4() throws Exception {
        TestSprite(IRes.Sprites.platformBroken4, () -> spriteFactory.getPlatformBrokenSprite4());
    }

    @Test
    public void TestGetPlatformExplosiveSprite1() throws Exception {
        TestSprite(IRes.Sprites.platformExplosive1, () -> spriteFactory.getPlatformExplosiveSprite1());
    }

    @Test
    public void TestGetPlatformExplosiveSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformExplosive2, () -> spriteFactory.getPlatformExplosiveSprite2());
    }

    @Test
    public void TestGetPlatformExplosiveSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformExplosive3, () -> spriteFactory.getPlatformExplosiveSprite3());
    }

    @Test
    public void TestGetPlatformMovableSprite1() throws Exception {
        TestSprite(IRes.Sprites.platformMovable1, () -> spriteFactory.getPlatformMovableSprite1());
    }

    @Test
    public void TestGetPlatformMovableSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformMovable2, () -> spriteFactory.getPlatformMovableSprite2());
    }

    @Test
    public void TestGetPlatformMovableSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformMovable3, () -> spriteFactory.getPlatformMovableSprite3());
    }

    @Test
    public void TestGetPlatformMovableSprite4() throws Exception {
        TestSprite(IRes.Sprites.platformMovable4, () -> spriteFactory.getPlatformMovableSprite4());
    }

    @Test
    public void TestGetPlatformShiningSprite1() throws Exception {
        TestSprite(IRes.Sprites.platformShining1, () -> spriteFactory.getPlatformShiningSprite1());
    }

    @Test
    public void TestGetPlatformShiningSprite2() throws Exception {
        TestSprite(IRes.Sprites.platformShining2, () -> spriteFactory.getPlatformShiningSprite2());
    }

    @Test
    public void TestGetPlatformShiningSprite3() throws Exception {
        TestSprite(IRes.Sprites.platformShining3, () -> spriteFactory.getPlatformShiningSprite3());
    }

    // POWERUPS


    @Test
    public void TestGetTrampolineSprite() throws Exception {
        TestSprite(IRes.Sprites.trampoline, () -> spriteFactory.getTrampolineSprite());
    }

    @Test
    public void TestGetTrampolineUsedSprite() throws Exception {
        TestSprite(IRes.Sprites.trampolineUsed, () -> spriteFactory.getTrampolineUsedSprite());
    }

    @Test
    public void TestGetSpringSprite() throws Exception {
        TestSprite(IRes.Sprites.spring, () -> spriteFactory.getSpringSprite());
    }

    @Test
    public void TestGetSpringUsedSprite() throws Exception {
        TestSprite(IRes.Sprites.springUsed, () -> spriteFactory.getSpringUsedSprite());
    }

    @Test
    public void TestGetRocketSprite() throws Exception {
        TestSprite(IRes.Sprites.jetpack, () -> spriteFactory.getJetpackSprite());
    }

    @Test
    public void TestGetPropellorSprite() throws Exception {
        TestSprite(IRes.Sprites.propeller, () -> spriteFactory.getPropellerSprite());
    }

    @Test
    public void TestGetShieldSprite() throws Exception {
        TestSprite(IRes.Sprites.shield, () -> spriteFactory.getShieldSprite());
    }

    // TEXT


    @Test
    public void TestGetWaitDontShootSprite() throws Exception {
        TestSprite(IRes.Sprites.waitDoNotShoot, () -> spriteFactory.getWaitDoNotShootSprite());
    }

    @Test
    public void TestGetAvoidSprite() throws Exception {
        TestSprite(IRes.Sprites.avoid, () -> spriteFactory.getAvoidSprite());
    }

    // TOP BAR


    @Test
    public void TestGetScorebarSprite() throws Exception {
        TestSprite(IRes.Sprites.scoreBar, () -> spriteFactory.getScoreBarSprite());
    }

    // UFO

    @Test
    public void TestGetUFOSprite() throws Exception {
        TestSprite(IRes.Sprites.ufo, () -> spriteFactory.getUFOSprite());
    }

    @Test
    public void TestGetUFOShiningSprite() throws Exception {
        TestSprite(IRes.Sprites.ufoShining, () -> spriteFactory.getUFOShiningSprite());
    }

    // CHOOSE MODE ICONS

    @Test
    public void TestGetRegularModeButton() throws Exception {
        TestSprite(IRes.Sprites.regularMode, () -> spriteFactory.getRegularModeButton());
    }

    @Test
    public void TestGetStoryModeButton() throws Exception {
        TestSprite(IRes.Sprites.storyMode, () -> spriteFactory.getStoryModeButton());
    }

    @Test
    public void TestGetDarknessModeButton() throws Exception {
        TestSprite(IRes.Sprites.darknessMode, () -> spriteFactory.getDarknessModeButton());
    }

    @Test
    public void TestGetInvertModeButton() throws Exception {
        TestSprite(IRes.Sprites.invertMode, () -> spriteFactory.getInvertModeButton());
    }

    @Test
    public void TestGetSpaceModeButton() throws Exception {
        TestSprite(IRes.Sprites.spaceMode, () -> spriteFactory.getSpaceModeButton());
    }

    @Test
    public void TestGetUnderwaterModeButton() throws Exception {
        TestSprite(IRes.Sprites.underwaterMode, () -> spriteFactory.getUnderwaterModeButton());
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
