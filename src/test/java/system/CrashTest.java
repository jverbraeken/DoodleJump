package system;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CrashTest {

    private IServiceLocator sL;

    @Before
    public void Init() throws Exception {
        Whitebox.invokeConstructor(Game.class, ServiceLocatorNoAudio.getServiceLocator());
        sL = Whitebox.getInternalState(Game.class, "serviceLocator");
    }

    /*@Test
    public void testCreateSinglePlayerWorld() {
        Game.setScene(
                ServiceLocatorNoAudio.getServiceLocator().getSceneFactory().createSinglePlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }

    @Test
    public void testCreateMultiplayerWorld() {
        Game.setScene(
                sL.getSceneFactory().createTwoPlayerWorld());
        //No crashes
        assertThat(true, is(true));
    }*/

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
                sL.getSceneFactory().createKillScreen(0));
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
