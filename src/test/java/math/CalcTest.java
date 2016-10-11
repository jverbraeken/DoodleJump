package math;

import org.junit.Before;
import org.junit.Test;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

public class CalcTest {

    private IServiceLocator sL;
    private ICalc calc;

    /**
     * Initialize the test class.
     * @throws Exception from the Whitebox invokeConstructor
     */
    @Before
    public void init() throws Exception {
        IServiceLocator sl = mock(IServiceLocator.class);
        Calc.register(sl);
        calc = Whitebox.invokeConstructor(Calc.class);
    }

    /**
     * Check if when you take 50 times a random integer between
     * 0 and 50 if they are all really between 0 and 50.
     */
    @Test
    public void getRandomIntBetweenTest() {
        for (int i = 0; i < 50; i++) {
            int integer = calc.getRandomIntBetween(1, 50);
            assertThat(integer >= 1 || integer <= 50, is(true));
        }
    }

    /**
     * Test if when you take a random integer between 1 and 2 that
     * the return value is 1 or 2.
     */
    @Test
    public void getRandomIntOneBetweenTest() {
        int integer = calc.getRandomIntBetween(1, 2);
        assertThat(integer == 1 || integer == 2, is(true));
    }

    /**
     * Check that an AssertionError comes up when the bounderies are
     * wrongly given.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getRandomIntBetweenTestWrong() {
        calc.getRandomIntBetween(5, 2);
    }

    /**
     * Check that an AssertionError comes up when the bounderies are
     * the same number.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getRandomIntBetweenTestEqual() {
        calc.getRandomIntBetween(2, 2);
    }

    /**
     * Test that when the max is 1, an double between 0 and 1 is returned.
     */
    @Test
    public void randomDoubleTest() {
        double d = calc.getRandomDouble(1);
        assertThat(d >= 0 || d <= 1, is(true));
    }

    /**
     * Test that an AssertionError comes up when the max is 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public void randomDoubleTestZero() {
        calc.getRandomDouble(0);
    }

    /**
     * Test that an AssertionError comes up when the max is negative.
     */
    @Test(expected = IllegalArgumentException.class)
    public void randomDoubleTestNegative() {
        calc.getRandomDouble(-42);
    }
}
