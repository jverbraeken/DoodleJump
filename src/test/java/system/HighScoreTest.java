package system;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HighScoreTest {

    private HighScore score;

    // Test names
    private String scoreNameA = "Foo";
    private String scoreNameB = "Bar";

    // Test scores
    private int scoreScore = 0;

    @Test
    public void testSpritesName1() {
        score = new HighScore(scoreNameA, scoreScore);
        assertThat(score.getName().equals(scoreNameA), is(true));
    }

    @Test
    public void testSpritesName2() {
        score = new HighScore(scoreNameB, scoreScore);
        assertThat(score.getName().equals(scoreNameB), is(true));
    }

    @Test
    public void testSpritesName3() {
        score = new HighScore(scoreNameA, scoreScore);
        assertThat(score.getName().equals(scoreNameB), is(false));
    }

}
