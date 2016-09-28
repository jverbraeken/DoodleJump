package math;


import org.junit.Before;
import org.junit.Test;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

public class CalcTest {

    private IServiceLocator sl;
    private ICalc c;

    @Before
    public void init() throws Exception{
        IServiceLocator sl = mock(IServiceLocator.class);
        Calc.register(sl);
        c = Whitebox.invokeConstructor(Calc.class);
    }

    @Test
    public void getRandomIntBetweenTest() {
        for (int i = 0; i < 50; i++) {
            int integer = c.getRandomIntBetween(1, 50);
            assertThat(integer >= 1 || integer <= 50, is(true));
        }
    }

    @Test
    public void getRandomIntOneBetweenTest() {
        int integer = c.getRandomIntBetween(1, 2);
        assertThat(integer == 1 || integer == 2, is(true));
    }

    @Test(expected = AssertionError.class)
    public void getRandomIntBetweenTestWrong() {
        int integer = c.getRandomIntBetween(5,2);
    }

    @Test(expected = AssertionError.class)
    public void getRandomIntBetweenTestEqual() {
        int integer = c.getRandomIntBetween(2,2);
    }

    @Test
    public void randomDoubleTest() {
        double d = c.getRandomDouble(1);
        assertThat(d >= 0 || d <= 1, is(true));
    }

    @Test(expected = AssertionError.class)
    public void randomDoubleTestZero() {
        double d = c.getRandomDouble(0);
    }

    @Test(expected = AssertionError.class)
    public void randomDoubleTestNegative() {
        double d = c.getRandomDouble(-42);
    }
}
