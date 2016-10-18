package progression;

import system.IServiceLocator;

import java.util.concurrent.Callable;

public final class DefaultProgressionObserver implements
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
        ITrampolineJumpedObserver {
    private final int times;
    private final Callable<Void> action;
    private final IServiceLocator serviceLocator;
    private double counter;
    private Mission mission;
    private final ProgressionObservers type;

    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = () -> null;
        this.counter = 0d;
        this.type.addObserver(this);
    }

    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final double counter) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = () -> null;
        this.counter = counter;
        this.type.addObserver(this);
    }

    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final Callable<Void> action) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = action;
        this.counter = 0d;
        this.type.addObserver(this);
    }

    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final Callable<Void> action, final double counter) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = action;
        this.counter = counter;
        this.type.addObserver(this);
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
            serviceLocator.getProgressionManager().removeObserver(this.type, this);
        }
    }
}
