package progression;

import constants.IConstants;
import groovy.transform.ToString;
import org.junit.Before;
import org.junit.Test;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.getInternalState;

public class MissionTest {
    private Mission mission;

    @Before
    public void init() {
        this.mission = new Mission(mock(IServiceLocator.class), MissionType.jumpOnSpring, "", mock(IProgressionObserver.class));
    }

    @Test
    public void testMission() {
        final IServiceLocator serviceLocator = mock(IServiceLocator.class);
        final IProgressionObserver observer1 = mock(IProgressionObserver.class);
        final IProgressionObserver observer2 = mock(IProgressionObserver.class);
        final IProgressionObserver observer3 = mock(IProgressionObserver.class);
        this.mission = new Mission(serviceLocator, MissionType.jumpOnSpring, "Foo", observer1);

        assertThat(getInternalState(this.mission, "serviceLocator"), is(serviceLocator));
        assertThat(getInternalState(this.mission, "type"), is(MissionType.jumpOnSpring));
        assertThat(getInternalState(this.mission, "observer"), is(observer1));
        assertThat(getInternalState(this.mission, "message"), is("Foo"));
    }

    @Test
    public void testGetType() {
        this.mission = new Mission(mock(IServiceLocator.class), MissionType.jumpOnSpring, "", mock(IProgressionObserver.class));
        assertThat(this.mission.getType(), is(MissionType.jumpOnSpring));
    }

    @Test
    public void testGetMaximumTimes() {
        IProgressionObserver observer = mock(IProgressionObserver.class);
        when(observer.getMaximumTimes()).thenReturn(42);
        this.mission = new Mission(mock(IServiceLocator.class), MissionType.jumpOnSpring, "", observer);
        assertThat(this.mission.getMaximumTimes(), is(42));
    }

    @Test
    public void testRender() throws Exception {
        final IServiceLocator serviceLocator = mock(IServiceLocator.class);
        final IRenderer renderer = mock(IRenderer.class);
        final ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
        final IConstants constants = mock(IConstants.class);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getConstants()).thenReturn(constants);
        this.mission = new Mission(serviceLocator, MissionType.jumpOnSpring, "", mock(IProgressionObserver.class));
        final int renderY = 13;

        this.mission.render(renderY);

        verifyPrivate(renderer, times(1)).invoke("drawSpriteHUD", any(ISprite.class), eq(0), eq(renderY));
    }

    @Test
    public void testAlertStartOver() throws Exception {
        final IProgressionObserver observer1 = mock(IProgressionObserver.class);
        this.mission = new Mission(mock(IServiceLocator.class), MissionType.jumpOnSpring, "", observer1);

        this.mission.alertStartOver();

        verifyPrivate(observer1, times(1)).invoke("reset");
    }

    @Test
    public void testAlertFinished() throws Exception {
        final IServiceLocator serviceLocator = mock(IServiceLocator.class);
        final IProgressionManager progressionManager = mock(IProgressionManager.class);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        this.mission = new Mission(serviceLocator, MissionType.jumpOnSpring, "", mock(IProgressionObserver.class));

        this.mission.alertFinished();

        verifyPrivate(progressionManager, times(1)).invoke("alertMissionFinished", this.mission);
    }
}
