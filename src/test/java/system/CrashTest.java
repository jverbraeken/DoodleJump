package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.audio.AudioManager;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CrashTest {

    private Game game;
    private IServiceLocator sL;

    @Before
    public void Init() throws Exception {
        game = Whitebox.invokeConstructor(Game.class, ServiceLocator2.getServiceLocator());
        sL = Whitebox.getInternalState(Game.class, "serviceLocator");
    }

    @Test
    public void testCreateSinglePlayerWorld() {
        game.setScene(
                ServiceLocator2.getServiceLocator().getSceneFactory().createSinglePlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateMultiplayerWorld() {
        game.setScene(
                sL.getSceneFactory().createTwoPlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateScoreScreen() {
        game.setScene(
                sL.getSceneFactory().createScoreScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateChoodeMode() {
        game.setScene(
                sL.getSceneFactory().newChooseMode());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateKillScreen() {
        game.setScene(
                sL.getSceneFactory().createKillScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreatePauseScreen() {
        game.setScene(
                sL.getSceneFactory().createPauseScreen());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateMainMenu() {
        game.setScene(
                sL.getSceneFactory().createMainMenu());
        //No crashes
        assertThat(true, is(true));
    }

}
