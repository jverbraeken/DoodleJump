package system;

import org.junit.Test;

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
        assertThat(score.getName().equals(scoreNameB), is(true));
    }

    @Test
    public void testHighScoreNameNeg() {
        score = new HighScore(scoreNameA, scoreScoreDefault);
        assertThat(score.getName().equals(scoreNameB), is(false));
    }

    @Test
    public void testHighScoreScore1() {
        score = new HighScore(scoreNameDefault, scoreScoreA);
        assertThat(score.getScore() == scoreScoreA, is(true));
    }

    @Test
    public void testHighScoreScore2() {
        score = new HighScore(scoreNameDefault, scoreScoreB);
        assertThat(score.getScore() == scoreScoreBExpected, is(true));
    }

    @Test
    public void testHighScoreScore3() {
        score = new HighScore(scoreNameDefault, scoreScoreC);
        assertThat(score.getScore() == scoreScoreCExpected, is(true));
    }

    @Test
    public void testHighScoreScoreNeg() {
        score = new HighScore(scoreNameDefault, scoreScoreA);
        assertThat(score.getScore() == scoreScoreA + 2, is(false));
    }

}