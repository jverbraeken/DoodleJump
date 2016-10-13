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
    private double counter;
    private Mission mission;

    /* package */ DefaultProgressionObserver(final int times) {
        this.times = times;
        this.action = () -> null;
        this.counter = 0d;
    }

    /* package */ DefaultProgressionObserver(final int times, final int counter) {
        this.times = times;
        this.action = () -> null;
        this.counter = counter;
    }

    /* package */ DefaultProgressionObserver(final int times, final Callable<Void> action) {
        this.times = times;
        this.action = action;
        this.counter = 0d;
    }

    /* package */ DefaultProgressionObserver(final int times, final Callable<Void> action, final int counter) {
        this.times = times;
        this.action = action;
        this.counter = counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alert() {
        counter++;
        checkFinished();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alert(final double amount) {
        counter += amount;
        checkFinished();
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

    private void checkFinished() {
        if (counter >= times) {
            try {
                action.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mission != null) {
                mission.alertFinished();
            }
        }
    }
}
