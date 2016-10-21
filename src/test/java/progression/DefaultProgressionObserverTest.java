package progression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(Mission.class)
public class DefaultProgressionObserverTest {
    /*DefaultProgressionObserver observer;

    @Before
    public void init() throws Exception {
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, mock(IServiceLocator.class), ProgressionObservers.spring, 0);
    }

    @Test
    public void testConstructor1() throws Exception {
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        ProgressionObservers type = ProgressionObservers.spring;
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, type, 42);
        assertThat(Whitebox.getInternalState(observer, "serviceLocator"), is(serviceLocator));
        assertThat(Whitebox.getInternalState(observer, "type"), is(type));
        assertThat(Whitebox.getInternalState(observer, "times"), is(42));
        assertThat(Whitebox.getInternalState(observer, "counter"), is(0d));
    }

    @Test
    public void testConstructor2() throws Exception {
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        ProgressionObservers type = ProgressionObservers.spring;
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, type, 42, 5d);
        assertThat(Whitebox.getInternalState(observer, "serviceLocator"), is(serviceLocator));
        assertThat(Whitebox.getInternalState(observer, "type"), is(type));
        assertThat(Whitebox.getInternalState(observer, "times"), is(42));
        assertThat(Whitebox.getInternalState(observer, "counter"), is(5d));
    }

    @Test
    public void testConstructor3() throws Exception {
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        ProgressionObservers type = ProgressionObservers.spring;
        final Callable action = mock(Callable.class);
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, type, 42, action);
        assertThat(Whitebox.getInternalState(observer, "serviceLocator"), is(serviceLocator));
        assertThat(Whitebox.getInternalState(observer, "type"), is(type));
        assertThat(Whitebox.getInternalState(observer, "times"), is(42));
        assertThat(Whitebox.getInternalState(observer, "action"), is(action));
        assertThat(Whitebox.getInternalState(observer, "counter"), is(0d));
    }

    @Test
    public void testConstructor4() throws Exception {
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        ProgressionObservers type = ProgressionObservers.spring;
        final Callable action = mock(Callable.class);
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, type, 42, action, 5d);
        assertThat(Whitebox.getInternalState(observer, "serviceLocator"), is(serviceLocator));
        assertThat(Whitebox.getInternalState(observer, "type"), is(type));
        assertThat(Whitebox.getInternalState(observer, "times"), is(42));
        assertThat(Whitebox.getInternalState(observer, "action"), is(action));
        assertThat(Whitebox.getInternalState(observer, "counter"), is(5d));
    }

    @Test
    public void testAlert() throws Exception {
        final Callable action = mock(Callable.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getProgressionManager()).thenReturn(mock(IProgressionManager.class));
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, ProgressionObservers.spring, 2, action, 0d);
        observer.alert();
        verifyPrivate(action, never()).invoke("call");
        observer.alert();
        verifyPrivate(action, times(1)).invoke("call");
    }

    @Test
    public void testAlertAmount() throws Exception {
        final Callable action = mock(Callable.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getProgressionManager()).thenReturn(mock(IProgressionManager.class));
        observer = Whitebox.invokeConstructor(DefaultProgressionObserver.class, serviceLocator, ProgressionObservers.spring, 5, action, 0d);
        observer.alert(2.5d);
        verifyPrivate(action, never()).invoke("call");
        observer.alert(2.5d);
        verifyPrivate(action, times(1)).invoke("call");
    }

    @Test
    public void testGetProgression() throws Exception {
        Whitebox.setInternalState(observer, "counter", 42);
        assertThat(observer.getProgression(), is(42d));
    }

    @Test
    public void testReset() {
        observer.reset();
        assertThat(observer.getProgression(), is(0d));
    }

    @Test
    public void testSetMission() {
        final Mission mission = mock(Mission.class);
        observer.setMission(mission);
        assertThat(Whitebox.getInternalState(observer, "mission"), is(equalTo(mission)));
    }

    @Test
    public void testSetMissionNull() {
        observer.setMission(null);
        assertThat(Whitebox.getInternalState(observer, "mission"), is(equalTo(null)));
    }*/
}
