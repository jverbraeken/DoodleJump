package progression;

import org.junit.Test;
import progression.HighScore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HighScoreTest {

    private HighScore score;

    // Test names
    private String scoreNameDefault = "";
    private String scoreNameA = "Foo";
    private String scoreNameB = "Bar";

    // Test scores
    private int scoreScoreDefault = 0;
    private int scoreScoreA = 5;
    private double scoreScoreB = 3.14d;
    private int scoreScoreBExpected = 3;
    private String scoreScoreC = "42";
    private int scoreScoreCExpected = 42;

    @Test
    public void testHighScoreName1() {
        score = new HighScore(scoreNameA, scoreScoreDefault);
        assertThat(score.getName().equals(scoreNameA), is(true));
    }

    @Test
    public void testHighScoreName2() {
        score = new HighScore(scoreNameB, scoreScoreDefault);
        assertThat(score.getName(), is(scoreNameB));
    }

    @Test
    public void testHighScoreScore1() {
        score = new HighScore(scoreNameDefault, scoreScoreA);
        assertThat(score.getScore(), is(scoreScoreA));
    }

    @Test
    public void testHighScoreScore2() {
        score = new HighScore(scoreNameDefault, scoreScoreB);
        assertThat(score.getScore(), is(scoreScoreBExpected));
    }

    @Test
    public void testHighScoreScore3() {
        score = new HighScore(scoreNameDefault, scoreScoreC);
        assertThat(score.getScore(), is(scoreScoreCExpected));
    }

}
