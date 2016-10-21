package progression;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SaveFileHighScoreEntryTest {
    private SaveFileHighScoreEntry entry;

    @Before
    public void init() {
        entry = new SaveFileHighScoreEntry();
    }

    @Test
    public void testGetName() {
        Whitebox.setInternalState(entry, "name", "foo");
        assertThat(entry.getName(), is(equalTo("foo")));

        Whitebox.setInternalState(entry, "name", "bar");
        assertThat(entry.getName(), is(equalTo("bar")));
    }

    @Test
    public void testGetScore() {
        Whitebox.setInternalState(entry, "score", 5);
        assertThat(entry.getScore(), is(5));

        Whitebox.setInternalState(entry, "score", 42);
        assertThat(entry.getScore(), is(42));
    }

    @Test
    public void testSetName() {
        entry.setName("foo");
        assertThat(Whitebox.getInternalState(entry, "name"), is(equalTo("foo")));

        entry.setName("bar");
        assertThat(Whitebox.getInternalState(entry, "name"), is(equalTo("bar")));
    }

    @Test
    public void testSetScore() {
        entry.setScore(5);
        assertThat(Whitebox.getInternalState(entry, "score"), is(equalTo(5)));

        entry.setScore(42);
        assertThat(Whitebox.getInternalState(entry, "score"), is(equalTo(42)));
    }

    @Test
    public void testToString() {
        Whitebox.setInternalState(entry, "name", "foo");
        Whitebox.setInternalState(entry, "score", 5);

        String expected = "HighScoreEntry{name='foo', score=5}";

        assertThat(entry.toString(), is(equalTo(expected)));

        Whitebox.setInternalState(entry, "name", "bar");
        Whitebox.setInternalState(entry, "score", 42);

        expected = "HighScoreEntry{name='bar', score=42}";

        assertThat(entry.toString(), is(equalTo(expected)));
    }
}
