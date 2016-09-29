package system;

import org.junit.*;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameTest {

    private ArrayList<HighScore> expected;

    private HighScore score1 = new HighScore("Foo", 10);
    private HighScore score2 = new HighScore("bar", 5);

    @Before
    public void init() throws Exception {
        Whitebox.setInternalState(Game.class, "highScores", new ArrayList<HighScore>());
        expected = new ArrayList<>();
    }

    @After
    public void finish() {
        expected = null;
    }

    @Test
    public void testNoHighScore() {
        Object temp = Whitebox.getInternalState(Game.class, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == 0, is(true));
        assertThat(actual.size() == expected.size(), is(true));
    }

    @Test
    public void testAdd1HighScore() {
        Game.addHighScore(score1.getName(), score1.getScore());
        expected.add(score1);

        Object temp = Whitebox.getInternalState(Game.class, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == expected.size(), is(true));
        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));
    }

    @Test
    public void testAdd2HighScores() {
        Game.addHighScore(score1.getName(), score1.getScore());
        Game.addHighScore(score2.getName(), score2.getScore());
        expected.add(score1);
        expected.add(score2);

        Object temp = Whitebox.getInternalState(Game.class, "highScores");
        ArrayList<HighScore> actual = (ArrayList<HighScore>) temp;

        assertThat(actual.size() == expected.size(), is(true));

        assertThat(actual.get(0).getName().equals(expected.get(0).getName()), is(true));
        assertThat(actual.get(0).getScore() == expected.get(0).getScore(), is(true));

        assertThat(actual.get(1).getName().equals(expected.get(1).getName()), is(true));
        assertThat(actual.get(1).getScore() == expected.get(1).getScore(), is(true));
    }

}
