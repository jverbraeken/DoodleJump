package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CrashTest {

    private IServiceLocator sL;

    private JFrame frame;

    @Before
    public void Init() throws Exception {
        String[] a = {"a"};
        Game.main(a);
        frame = Whitebox.getInternalState(Game.class, "frame");
        sL = Whitebox.getInternalState(Game.class, "serviceLocator");
    }

    @After
    public void Close() throws Exception {
        frame.dispose();
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
