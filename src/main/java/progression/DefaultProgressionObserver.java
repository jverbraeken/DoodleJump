package progression;

import java.util.concurrent.Callable;

public class DefaultProgressionObserver implements
        IDisappearingPowerupObserver,
        IEquipmentPowerupObserver,
        IHeightObserver,
        IJetpackUsedObserver,
        IJumpablePowerupObserver,
        IPowerupObserver,
        IProgressionObserver,
        IPropellerUsedObserver,
        ISizeDownUsedObserver,
        ISizeUpUserObserver,
        ISpringShoesUsedObserver,
        ISpringUsedObserver,
        ITrampolineJumpedObserver
{
    private final int times;
    private final Callable<Void> action;
    private double counter = 0d;
    private Mission mission;

    /* package */ DefaultProgressionObserver(final int times, final Callable<Void> action) {
        this.times = times;
        this.action = action;
    }

    /* package */ DefaultProgressionObserver(final int times, final Callable<Void> action, int counter) {
        this(times, action);
        this.counter = counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alert() {
        counter++;
        if (counter >= times) {
            mission.alertFinished();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alert(final double amount) {
        counter++;
        if (counter >= times) {
            mission.alertFinished();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgression() {
        return counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        counter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMission(final Mission mission) {
        this.mission = mission;
    }
}
