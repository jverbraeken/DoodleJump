package progression;

import com.bluelinelabs.logansquare.LoganSquare;
import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.Powerups;
import org.junit.*;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ProgressionManagerTest {

    private static IServiceLocator serviceLocator;
    private static ILoggerFactory loggerFactory;
    private static IFileSystem fileSystem;
    private static IConstants constants;

    private ProgressionManager progressionManager;
    private List<HighScore> expected;

    private final static HighScore SCORE_1 = new HighScore("Foo", 78);
    private final static HighScore SCORE_2 = new HighScore("bar", 80);
    private final static HighScore SCORE_3 = new HighScore("Hello", 2);
    private final static HighScore SCORE_4 = new HighScore("World", 1);

    private final static String INIT_FILE_CONTENT_1 = "{\"coins\":0,\"highScores\":[{\"name\":\"Foo\",\"score\":78},{\"name\":\"bar\",\"score\":80}],\"powerupLevels\":{\"JETPACK\":0,\"SPRING\":1,\"SPRINGSHOES\":0,\"SIZEDOWN\":0,\"PROPELLER\":0,\"SIZEUP\":0,\"TRAMPOLINE\":0}}";
    private final static String INIT_FILE_CONTENT_2 = "{\"coins\":1,\"highScores\":[{\"name\":\"Foo\",\"score\":78}],\"powerupLevels\":{\"JETPACK\":0,\"SPRING\":1,\"SPRINGSHOES\":2,\"SIZEDOWN\":3,\"PROPELLER\":4,\"SIZEUP\":5,\"TRAMPOLINE\":0}}";
    private final static String INIT_FILE_CONTENT_3 = "{\"coins\":2,\"highScores\":[],\"powerupLevels\":{\"JETPACK\":0,\"SPRING\":1,\"SPRINGSHOES\":0,\"SIZEDOWN\":0,\"PROPELLER\":0,\"SIZEUP\":0,\"TRAMPOLINE\":0}}";

    
    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        loggerFactory = mock(ILoggerFactory.class);
        fileSystem = mock(IFileSystem.class);
        constants = mock(IConstants.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(loggerFactory.createLogger(ProgressionManager.class)).thenReturn(mock(ILogger.class));
        ProgressionManager.register(serviceLocator);
        progressionManager = Whitebox.invokeConstructor(ProgressionManager.class);
        expected = new ArrayList<>();
    }


    @Test
    public void testNoHighScore() {
        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        List<HighScore> actual = (List<HighScore>) temp;

        assertThat(actual.size() == 0, is(true));
    }

    @Test
    public void testAdd1HighScore() {
        progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());

        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 1, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));
    }

    @Test
    public void testAdd2HighScores() {
        progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        progressionManager.addHighScore(SCORE_2.getName(), SCORE_2.getScore());

        expected.add(SCORE_2);
        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 2, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName(), is(equalTo(expected.get(0).getName())));
        assertThat(actual.get(0).getScore(), is(equalTo(expected.get(0).getScore())));

        assertThat(actual.get(1).getName(), is(equalTo(expected.get(1).getName())));
        assertThat(actual.get(1).getScore(), is(equalTo(expected.get(1).getScore())));
    }

    @Test
    public void testAdd3HighScores() {
        progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        progressionManager.addHighScore(SCORE_2.getName(), SCORE_2.getScore());
        progressionManager.addHighScore(SCORE_3.getName(), SCORE_3.getScore());
        progressionManager.addHighScore(SCORE_4.getName(), SCORE_4.getScore());

        expected.add(SCORE_2);
        expected.add(SCORE_1);
        expected.add(SCORE_3);
        expected.add(SCORE_4);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 4, is(true));
        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName(), is(equalTo(expected.get(0).getName())));
        assertThat(actual.get(0).getScore(), is(expected.get(0).getScore()));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));

        assertThat(actual.get(2).getName().equals(expected.get(2).getName()), is(true));
        assertThat(actual.get(2).getScore() == expected.get(2).getScore(), is(true));

        assertThat(actual.get(3).getName().equals(expected.get(3).getName()), is(true));
        assertThat(actual.get(3).getScore() == expected.get(3).getScore(), is(true));
    }

    @Test
    public void testInit1() throws IOException {
        SaveFile saveFile = LoganSquare.parse(INIT_FILE_CONTENT_1, SaveFile.class);
        when(fileSystem.parseJson(serviceLocator.getConstants().getSaveFilePath(), SaveFile.class)).thenReturn(saveFile);
        progressionManager.init();

        expected.add(SCORE_2);
        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        List<HighScore> actual = (List<HighScore>) temp;

        assertThat(actual.size(), is(2));
        assertThat(actual.size(), is(expected.size()));

        assertThat(actual.get(0).getName(), is(equalTo(expected.get(0).getName())));
        assertThat(actual.get(0).getScore(), is(expected.get(0).getScore()));

        assertThat(actual.get(1).getName(), is(equalTo(expected.get(1).getName())));
        assertThat(actual.get(1).getScore(), is(expected.get(1).getScore()));

        int coins = Whitebox.getInternalState(progressionManager, "coins");

        assertThat(coins, is(0));

        final Map<Powerups, Integer> expectedPowerupLevels = new EnumMap<>(Powerups.class);
        expectedPowerupLevels.put(Powerups.JETPACK, 0);
        expectedPowerupLevels.put(Powerups.PROPELLER, 0);
        expectedPowerupLevels.put(Powerups.SIZEDOWN, 0);
        expectedPowerupLevels.put(Powerups.SIZEUP, 0);
        expectedPowerupLevels.put(Powerups.SPRING, 1);
        expectedPowerupLevels.put(Powerups.SPRINGSHOES, 0);
        expectedPowerupLevels.put(Powerups.TRAMPOLINE, 0);

        Map<Powerups, Integer> powerupLevels = Whitebox.getInternalState(progressionManager, "powerupLevels");

        assertThat(powerupLevels, is(equalTo(expectedPowerupLevels)));

    }

    @Test
    public void testInit2() throws IOException {
        SaveFile saveFile = LoganSquare.parse(INIT_FILE_CONTENT_2, SaveFile.class);
        when(fileSystem.parseJson(serviceLocator.getConstants().getSaveFilePath(), SaveFile.class)).thenReturn(saveFile);
        progressionManager.init();

        expected.add(SCORE_1);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        List<HighScore> actual = (List<HighScore>) temp;

        assertThat(actual.size(), is(1));
        assertThat(actual.size(), is(expected.size()));

        assertThat(actual.get(0).getName(), is(equalTo(expected.get(0).getName())));
        assertThat(actual.get(0).getScore(), is(expected.get(0).getScore()));

        int coins = Whitebox.getInternalState(progressionManager, "coins");

        assertThat(coins, is(1));

        final Map<Powerups, Integer> expectedPowerupLevels = new EnumMap<>(Powerups.class);
        expectedPowerupLevels.put(Powerups.JETPACK, 0);
        expectedPowerupLevels.put(Powerups.PROPELLER, 4);
        expectedPowerupLevels.put(Powerups.SIZEDOWN, 3);
        expectedPowerupLevels.put(Powerups.SIZEUP, 5);
        expectedPowerupLevels.put(Powerups.SPRING, 1);
        expectedPowerupLevels.put(Powerups.SPRINGSHOES, 2);
        expectedPowerupLevels.put(Powerups.TRAMPOLINE, 0);

        Map<Powerups, Integer> powerupLevels = Whitebox.getInternalState(progressionManager, "powerupLevels");

        assertThat(powerupLevels, is(equalTo(expectedPowerupLevels)));

    }

    @Test
    public void testInit3() throws IOException {
        SaveFile saveFile = LoganSquare.parse(INIT_FILE_CONTENT_3, SaveFile.class);
        when(fileSystem.parseJson(serviceLocator.getConstants().getSaveFilePath(), SaveFile.class)).thenReturn(saveFile);
        progressionManager.init();

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        List<HighScore> actual = (List<HighScore>) temp;

        assertThat(actual.size(), is(0));
        assertThat(actual.size(), is(expected.size()));

        int coins = Whitebox.getInternalState(progressionManager, "coins");

        assertThat(coins, is(2));

        final Map<Powerups, Integer> expectedPowerupLevels = new EnumMap<>(Powerups.class);
        expectedPowerupLevels.put(Powerups.JETPACK, 0);
        expectedPowerupLevels.put(Powerups.PROPELLER, 0);
        expectedPowerupLevels.put(Powerups.SIZEDOWN, 0);
        expectedPowerupLevels.put(Powerups.SIZEUP, 0);
        expectedPowerupLevels.put(Powerups.SPRING, 1);
        expectedPowerupLevels.put(Powerups.SPRINGSHOES, 0);
        expectedPowerupLevels.put(Powerups.TRAMPOLINE, 0);

        Map<Powerups, Integer> powerupLevels = Whitebox.getInternalState(progressionManager, "powerupLevels");

        assertThat(powerupLevels, is(equalTo(expectedPowerupLevels)));

    }

    @Test
    public void testInitFileNotFound() throws IOException {
        when(fileSystem.parseJson(serviceLocator.getConstants().getSaveFilePath(), SaveFile.class)).thenThrow(FileNotFoundException.class);
        progressionManager.init();

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
        List<HighScore> actual = (List<HighScore>) temp;

        assertThat(actual.size(), is(0));
        assertThat(actual.size(), is(expected.size()));

        int coins = Whitebox.getInternalState(progressionManager, "coins");

        assertThat(coins, is(0));

        final Map<Powerups, Integer> expectedPowerupLevels = new EnumMap<>(Powerups.class);
        expectedPowerupLevels.put(Powerups.JETPACK, 0);
        expectedPowerupLevels.put(Powerups.PROPELLER, 0);
        expectedPowerupLevels.put(Powerups.SIZEDOWN, 0);
        expectedPowerupLevels.put(Powerups.SIZEUP, 0);
        expectedPowerupLevels.put(Powerups.SPRING, 1);
        expectedPowerupLevels.put(Powerups.SPRINGSHOES, 0);
        expectedPowerupLevels.put(Powerups.TRAMPOLINE, 0);

        Map<Powerups, Integer> powerupLevels = Whitebox.getInternalState(progressionManager, "powerupLevels");

        assertThat(powerupLevels, is(equalTo(expectedPowerupLevels)));
    }

    @Test
    public void testUpdateHighScores_CorrectOrder() {
        progressionManager.addHighScore(SCORE_2.getName(), SCORE_2.getScore());
        progressionManager.addHighScore(SCORE_4.getName(), SCORE_4.getScore());
        progressionManager.addHighScore(SCORE_3.getName(), SCORE_3.getScore());
        progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());

        // Actual order
        expected.add(SCORE_2);
        expected.add(SCORE_1);
        expected.add(SCORE_3);
        expected.add(SCORE_4);

        Object temp = Whitebox.getInternalState(progressionManager, "highScores");
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
        Object temp = Whitebox.getInternalState(ProgressionManager.class, "MAX_HIGHSCORE_ENTRIES");
        int maxEntries = (int) temp;

        for (int i = 0; i < maxEntries + 1; i++) {
            progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        }

        temp = Whitebox.getInternalState(progressionManager, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;
        assertThat(actual.size(), is(maxEntries));
    }

    @Test
    public void testGetList_empty() {
        List<HighScore> actual = progressionManager.getHighscores();
        assertThat(actual.size(), is(0));
    }

    @Test
    public void testGetList_notEmpty() {
        progressionManager.addHighScore(SCORE_1.getName(), SCORE_1.getScore());
        expected.add(SCORE_1);

        List<HighScore> actual = progressionManager.getHighscores();
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getName(), is(expected.get(0).getName()));
        assertThat(actual.get(0).getScore(), is(expected.get(0).getScore()));
    }
}
