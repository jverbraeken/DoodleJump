package progression;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

public class SaveFileTest {
    private SaveFile saveFile;

    @Before
    public void init() {
        saveFile = new SaveFile();
    }

    @Test
    public void testGetHighScores() {
        final List<SaveFileHighScoreEntry> highScores1 = mock(List.class);
        final List<SaveFileHighScoreEntry> highScores2 = mock(List.class);

        Whitebox.setInternalState(saveFile, "highScores", highScores1);
        assertThat(saveFile.getHighScores(), is(equalTo(highScores1)));

        Whitebox.setInternalState(saveFile, "highScores", highScores2);
        assertThat(saveFile.getHighScores(), is(equalTo(highScores2)));

    }

    @Test
    public void testGetCoins() {
        final int coins1 = 5;
        final int coins2 = 42;

        Whitebox.setInternalState(saveFile, "coins", coins1);
        assertThat(saveFile.getCoins(), is(equalTo(coins1)));

        Whitebox.setInternalState(saveFile, "coins", coins2);
        assertThat(saveFile.getCoins(), is(equalTo(coins2)));

    }

    @Test
    public void testGetPowerupLevels() {
        final Map<String, Integer> powerupLevels1 = mock(Map.class);
        final Map<String, Integer> powerupLevels2 = mock(Map.class);

        Whitebox.setInternalState(saveFile, "powerupLevels", powerupLevels1);
        assertThat(saveFile.getPowerupLevels(), is(equalTo(powerupLevels1)));

        Whitebox.setInternalState(saveFile, "powerupLevels", powerupLevels2);
        assertThat(saveFile.getPowerupLevels(), is(equalTo(powerupLevels2)));

    }

    @Test
    public void testSetHighScores() {
        final List<SaveFileHighScoreEntry> highScores1 = mock(List.class);
        final List<SaveFileHighScoreEntry> highScores2 = mock(List.class);

        saveFile.setHighScores(highScores1);
        assertThat(Whitebox.getInternalState(saveFile, "highScores"), is(equalTo(highScores1)));

        saveFile.setHighScores(highScores2);
        assertThat(Whitebox.getInternalState(saveFile, "highScores"), is(equalTo(highScores2)));

    }

    @Test
    public void testSetCoins() {
        final int coins1 = 5;
        final int coins2 = 42;

        saveFile.setCoins(coins1);
        assertThat(Whitebox.getInternalState(saveFile, "coins"), is(equalTo(coins1)));

        saveFile.setCoins(coins2);
        assertThat(Whitebox.getInternalState(saveFile, "coins"), is(equalTo(coins2)));

    }

    @Test
    public void testSetPowerupLevels() {
        final Map<String, Integer> powerupLevels1 = mock(Map.class);
        final Map<String, Integer> powerupLevels2 = mock(Map.class);

        saveFile.setPowerupLevels(powerupLevels1);
        assertThat(Whitebox.getInternalState(saveFile, "powerupLevels"), is(equalTo(powerupLevels1)));

        saveFile.setPowerupLevels(powerupLevels2);
        assertThat(Whitebox.getInternalState(saveFile, "powerupLevels"), is(equalTo(powerupLevels2)));

    }

    // We don't test bad weather behaviours because this class is, as is explicitly noted, not meant for regular use,
    // but only for JSON (de)serializers (which are extremely predictable programs).
}
