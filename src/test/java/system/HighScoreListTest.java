package system;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILoggerFactory;
import org.junit.*;
import org.powermock.reflect.Whitebox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class HighScoreListTest {

    private IServiceLocator serviceLocator;
    private ILoggerFactory loggerFactory;
    private IFileSystem fileSystem;
    private IConstants constants;

    private HighScoreList highScores;
    private ArrayList<HighScore> expected;

    private final static HighScore SCORE_1 = new HighScore("Foo", 10);
    private final static HighScore SCORE_2 = new HighScore("bar", 5);
    private final static HighScore SCORE_3 = new HighScore("Hello", 2);
    private final static HighScore SCORE_4 = new HighScore("World", 1);

    private final static List<String> INIT_FILE_CONTENT_1 = new ArrayList<String>() {{ add("Foo 10 bar 5"); }};
    private final static List<String> INIT_FILE_CONTENT_2 = new ArrayList<String>() {{ add("Foo 10"); }};
    private final static List<String> INIT_FILE_CONTENT_3 = new ArrayList<String>() {{ add(""); }};


    @Before
    public void init() {
        this.serviceLocator = mock(IServiceLocator.class);
        this.loggerFactory = mock(ILoggerFactory.class);
        this.fileSystem = mock(IFileSystem.class);
        this.constants = mock(IConstants.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(this.loggerFactory);
        when(serviceLocator.getFileSystem()).thenReturn(this.fileSystem);
        when(serviceLocator.getConstants()).thenReturn(this.constants);
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
        highScores.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 1, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));
    }

    @Test
    public void testAdd2HighScores() {
        highScores.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        highScores.addHighScore(SCORE_2.getName(), SCORE_2.getScore());
        expected.add(SCORE_1);
        expected.add(SCORE_2);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 2, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));
    }

    @Test
    public void testInitHighScores1() throws FileNotFoundException {
        when(fileSystem.readResourceFile(constants.getHighScoresFilePath())).thenReturn(INIT_FILE_CONTENT_1);
        highScores.initHighScores();

        expected.add(SCORE_1);
        expected.add(SCORE_2);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 2, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));
    }

    @Test
    public void testInitHighScores2() throws FileNotFoundException {
        when(fileSystem.readResourceFile(constants.getHighScoresFilePath())).thenReturn(INIT_FILE_CONTENT_2);
        highScores.initHighScores();

        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 1, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));
    }

    @Test
    public void testInitHighScores3() throws FileNotFoundException {
        when(fileSystem.readResourceFile(constants.getHighScoresFilePath())).thenReturn(INIT_FILE_CONTENT_3);
        highScores.initHighScores();

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 0, is(true));
        assertThat(actual.size() == expected.size(), is(true));
    }

    @Test
    public void testUpdateHighScores_CorrectOrder() {
        highScores.addHighScore(SCORE_2.getName(), SCORE_2.getScore());
        highScores.addHighScore(SCORE_4.getName(), SCORE_4.getScore());
        highScores.addHighScore(SCORE_3.getName(), SCORE_3.getScore());
        highScores.addHighScore(SCORE_1.getName(), SCORE_1.getScore());

        // Actual order
        expected.add(SCORE_1);
        expected.add(SCORE_2);
        expected.add(SCORE_3);
        expected.add(SCORE_4);

        Object temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));

        assertThat(actual.get(2).getName().equals(expected.get(2).getName()), is(true));
        assertThat(actual.get(2).getScore() == expected.get(2).getScore(), is(true));

        assertThat(actual.get(3).getName().equals(expected.get(3).getName()), is(true));
        assertThat(actual.get(3).getScore() == expected.get(3).getScore(), is(true));
    }

    @Test
    public void testUpdateHighScores_MaxHighScores() {
        Object temp = Whitebox.getInternalState(HighScoreList.class, "MAX_ENTRIES");
        int maxEntries = (int) temp;

        for (int i = 0; i < maxEntries + 1; i++) {
            highScores.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        }

        temp = Whitebox.getInternalState(highScores, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == maxEntries, is(true));
    }
}
