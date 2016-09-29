package system;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILoggerFactory;
import org.junit.*;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class HighScoreListTest {

    private HighScoreList highScores;
    private ArrayList<HighScore> expected;

    private HighScore score1 = new HighScore("Foo", 10);
    private HighScore score2 = new HighScore("bar", 5);

    @Before
    public void init() throws Exception {
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        IFileSystem fileSystem = mock(IFileSystem.class);
        IConstants constants = mock(IConstants.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(loggerFactory.createLogger(HighScoreList.class)).thenReturn(null);

        highScores = new HighScoreList(serviceLocator);
        expected = new ArrayList<>();
    }

    @After
    public void finish() {
        highScores = null;
        expected = null;
    }

    @Test
    public void testNoHighScore() {
        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 0, is(true));
        assertThat(actual.size() == expected.size(), is(true));
    }

    @Test
    public void testAdd1HighScore() {
        highScores.addHighScore(score1.getName(), score1.getScore());
        expected.add(score1);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == expected.size(), is(true));
        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));
    }

    @Test
    public void testAdd2HighScores() {
        highScores.addHighScore(score1.getName(), score1.getScore());
        highScores.addHighScore(score2.getName(), score2.getScore());
        expected.add(score1);
        expected.add(score2);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));
    }

}
