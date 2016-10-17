package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CrashTest {

    private IServiceLocator sL;

    @Before
    public void Init() throws Exception {
        Whitebox.invokeConstructor(Game.class);
        sL = Whitebox.getInternalState(Game.class, "serviceLocator");
        sL.provide(new IAudioManager() {
            @Override
            public void preload() {
                
            }

            @Override
            public void playBijeli() {

            }

            @Override
            public void playBlizzard() {

            }

            @Override
            public void playBubbles1() {

            }

            @Override
            public void playBubbles2() {

            }

            @Override
            public void playChill() {

            }

            @Override
            public void playCollect() {

            }

            @Override
            public void playCrnarupa() {

            }

            @Override
            public void playEggMonsterHit() {

            }

            @Override
            public void playExplodingPlatform() {

            }

            @Override
            public void playExplodingPlatform2() {

            }

            @Override
            public void playFeder() {

            }

            @Override
            public void playJetpack() {

            }

            @Override
            public void playJump() {

            }

            @Override
            public void playJumpOnMonster() {

            }

            @Override
            public void playLomise() {

            }

            @Override
            public void playMatchSound() {

            }

            @Override
            public void playMonsterCrash() {

            }

            @Override
            public void playMonsterBlizu() {

            }

            @Override
            public void playMonsterPogodak() {

            }

            @Override
            public void playOogapucanje() {

            }

            @Override
            public void playOogapucanje2() {

            }

            @Override
            public void playPada() {

            }

            @Override
            public void playPropeller() {

            }

            @Override
            public void playPucanje() {

            }

            @Override
            public void playPucanje2() {

            }

            @Override
            public void playRain() {

            }

            @Override
            public void playRocket() {

            }

            @Override
            public void playSnowballMonsterHit() {

            }

            @Override
            public void playSnowballThrow() {

            }

            @Override
            public void playSnowballThrow2() {

            }

            @Override
            public void playSoccerMonsterCrash() {

            }

            @Override
            public void playSoccerMonsterHit() {

            }

            @Override
            public void playSpringShoes() {

            }

            @Override
            public void playStart() {

            }

            @Override
            public void playThunder() {

            }

            @Override
            public void playTrampoline() {

            }

            @Override
            public void playUFO() {

            }

            @Override
            public void playUFOPogodak() {

            }

            @Override
            public void playUnderwaterShoot() {

            }

            @Override
            public void playUnderwaterShoot2() {

            }

            @Override
            public void playUsaugateufo() {

            }

            @Override
            public void playWin() {

            }
        });
        String[] a = {"a"};
        Game.main(a);
    }

    @Test
    public void testCreateSinglePlayerWorld() {
        Game.setScene(
                sL.getSceneFactory().createSinglePlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateMultiplayerWorld() {
        Game.setScene(
                sL.getSceneFactory().createTwoPlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateScoreScreen() {
        Game.setScene(
                sL.getSceneFactory().createScoreScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateChoodeMode() {
        Game.setScene(
                sL.getSceneFactory().newChooseMode());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateKillScreen() {
        Game.setScene(
                sL.getSceneFactory().createKillScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreatePauseScreen() {
        Game.setScene(
                sL.getSceneFactory().createPauseScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateMainMenu() {
        Game.setScene(
                sL.getSceneFactory().createMainMenu());
        //No crashes
        assertThat(true, is(true));
    }

}
