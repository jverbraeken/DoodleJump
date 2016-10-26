package progression;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.Powerups;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SaveFile.class, SaveFileHighScoreEntry.class, Mission.class})
public class ProgressionManagerTest {

    private final static HighScore SCORE_1 = new HighScore("Foo", 78);
    private final static HighScore SCORE_2 = new HighScore("bar", 80);
    private final static HighScore SCORE_3 = new HighScore("Hello", 2);
    private final static HighScore SCORE_4 = new HighScore("World", 1);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IFileSystem fileSystem = mock(IFileSystem.class);
    private IConstants constants = mock(IConstants.class);
    private ILogger logger = mock(ILogger.class);
    private IMissionFactory missionFactory = mock(IMissionFactory.class);
    private ProgressionManager progressionManager;
    private List<HighScore> expected;
    private List<Mission> missions = new ArrayList<>();
    private Mission mission = mock(Mission.class);

    @Before
    public void init() throws Exception {
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getMissionFactory()).thenReturn(missionFactory);
        when(loggerFactory.createLogger(ProgressionManager.class)).thenReturn(logger);

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
        expectedPowerupLevels.put(Powerups.jetpack, 0);
        expectedPowerupLevels.put(Powerups.propeller, 0);
        expectedPowerupLevels.put(Powerups.shield, 0);
        expectedPowerupLevels.put(Powerups.sizeDown, 0);
        expectedPowerupLevels.put(Powerups.sizeUp, 0);
        expectedPowerupLevels.put(Powerups.spring, 1);
        expectedPowerupLevels.put(Powerups.springShoes, 0);
        expectedPowerupLevels.put(Powerups.trampoline, 0);

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

    @Test
    public void testGetCoins() {
        final int coins = 42;
        Whitebox.setInternalState(progressionManager, "coins", coins);

        assertThat(progressionManager.getCoins(), is(coins));
    }

    @Test
    public void testGetMissions() {
        final List missions = mock(List.class);
        Whitebox.setInternalState(progressionManager, "missions", missions);

        assertThat(progressionManager.getMissions(), is(missions));
    }

    @Test
    public void testAlertMissionFinishedLogs() {
        missions.add(mission);
        Whitebox.setInternalState(progressionManager, "missions", missions);
        progressionManager.alertMissionFinished(mission);
        verify(logger, times(1)).info(anyString());
    }

    @Test(expected = InternalError.class)
    public void testAlertMissionFinishedMissionNotFound() {
        progressionManager.alertMissionFinished(mission);
    }

//
//    @Test
//    public void testAlertObserversQueue() {
//        final ISpringUsedObserver observer = mock(ISpringUsedObserver.class);
//        final Mission mission = mock(Mission.class);
//        progressionManager.addObserver(ProgressionObservers.spring, observer);
//        Whitebox.setInternalState(progressionManager, "finishedMissionsQueue", new LinkedList<Mission>() {{
//            add(mission);
//        }});
//        progressionManager.alertObservers(ProgressionObservers.spring);
//
//        Mockito.verify(observer).alert();
//    }
//
//    @Test
//    public void testAlertObserversWithAmount() {
//        final int amount = 1234;
//        final ISpringUsedObserver observer = mock(ISpringUsedObserver.class);
//        progressionManager.addObserver(ProgressionObservers.spring, observer);
//        progressionManager.alertObservers(ProgressionObservers.spring, amount);
//
//        Mockito.verify(observer).alert(amount);
//    }
//
//    @Test
//    public void testAlertObserversWithAmountAndQueue() {
//        final int amount = 1234;
//        final ISpringUsedObserver observer = mock(ISpringUsedObserver.class);
//        final Mission mission = mock(Mission.class);
//        progressionManager.addObserver(ProgressionObservers.spring, observer);
//        Whitebox.setInternalState(progressionManager, "finishedMissionsQueue", new LinkedList<Mission>() {{
//            add(mission);
//        }});
//        progressionManager.alertObservers(ProgressionObservers.spring, amount);
//
//        Mockito.verify(observer).alert(amount);
//    }
//
//    @Test(expected = InternalError.class)
//    public void testAlertObserversMissionFinishedUnknownMission() {
//        final Mission mission = mock(Mission.class);
//        progressionManager.alertMissionFinished(mission);
//    }
//
//    @Test
//    public void testAlertObserversMissionFinished() {
//        final Mission mission = mock(Mission.class);
//        final Mission mission2 = mock(Mission.class);
//        final Mission mission3 = mock(Mission.class);
//        final Queue finishedMissionsQueue = mock(Queue.class);
//        Whitebox.setInternalState(progressionManager, "missions", new ArrayList<Mission>() {{
//            add(mission);
//            add(mission2);
//            add(mission3);
//        }});
//        progressionManager.alertMissionFinished(mission);
//        assertThat(((Queue<Mission>) Whitebox.getInternalState(progressionManager, "finishedMissionsQueue")).contains(mission), is(true));
//    }
//
//    @Test
//    public void testProgressionFromJson() throws Exception {
//        final SaveFile json = mock(SaveFile.class);
//        final SaveFileHighScoreEntry saveFileHighScoreEntry1 = mock(SaveFileHighScoreEntry.class);
//        final SaveFileHighScoreEntry saveFileHighScoreEntry2 = mock(SaveFileHighScoreEntry.class);
//        final SaveFileHighScoreEntry saveFileHighScoreEntry3 = mock(SaveFileHighScoreEntry.class);
//        when(json.getCoins()).thenReturn(42);
//        when(json.getHighScores()).thenReturn(new ArrayList<SaveFileHighScoreEntry>() {{
//            add(saveFileHighScoreEntry1);
//            add(saveFileHighScoreEntry2);
//            add(saveFileHighScoreEntry3);
//        }});
//        final Map<String, Integer> powerupsMap = new HashMap<String, Integer>() {{
//            put("jetpack", 1);
//            put("propeller", 2);
//            put("sizeDown", 3);
//            put("sizeUp", 4);
//            put("spring", 5);
//            put("springShoes", 6);
//            put("trampoline", 7);
//        }};
//        when(json.getPowerupLevels()).thenReturn(powerupsMap);
//
//        Whitebox.invokeMethod(progressionManager, "progressionFromJson", json);
//
//        final List<HighScore> expectedHighScores = new ArrayList<HighScore>() {{
//            add(new HighScore(saveFileHighScoreEntry1.getName(), saveFileHighScoreEntry1.getScore()));
//            add(new HighScore(saveFileHighScoreEntry2.getName(), saveFileHighScoreEntry2.getScore()));
//            add(new HighScore(saveFileHighScoreEntry3.getName(), saveFileHighScoreEntry3.getScore()));
//        }};
//        assertThat(Whitebox.getInternalState(progressionManager, "highScores"), is(equalTo(expectedHighScores)));
//
//        final int expectedCoins = 42;
//        assertThat(Whitebox.getInternalState(progressionManager, "coins"), is(expectedCoins));
//
//    }
}
