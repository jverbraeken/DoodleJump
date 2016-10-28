package progression;

import system.IServiceLocator;

import java.util.concurrent.Callable;

/* package */ abstract class DefaultProgressionObserver implements IProgressionObserver {

    private Mission mission;
    private final IServiceLocator serviceLocator;
    private final int times;
    private final Callable<Void> action;
    private final ProgressionObservers type;
    private double counter;

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param type           The progression type being observed
     * @param times          The amount of times the progression should be raised before the goal is achieved
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = () -> null;
        this.counter = 0d;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param type           The progression type being observed
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final double counter) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = () -> null;
        this.counter = counter;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param type           The progression type being observed
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final Callable<Void> action) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = action;
        this.counter = 0d;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param type           The progression type being observed
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final ProgressionObservers type, final int times, final Callable<Void> action, final double counter) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.action = action;
        this.counter = counter;
    }

    /**
     * Of course we rather use notify, but as that name is occupied already by java.lang it's not possible
     * to use that name unfortunately.
     */
    protected final void alert() {
        counter++;
        checkFinished();
    }

    /**
     * Of course we rather use notify, but as that name is occupied already by java.lang it's not possible
     * to use that name unfortunately.
     *
     * @param amount The amount of which the variable that caused the trigger to alert the observers has been changed.
     */
    protected final void alert(final double amount) {
        counter += amount;
        checkFinished();
    }

    public final double getProgression() {
        return counter;
    }

    public final void reset() {
        counter = 0;
    }

    public final void setMission(final Mission mission) {
        this.mission = mission;
    }

    protected final void finished() {
        try {
            action.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mission != null) {
            mission.alertFinished();
        }
        finishedExtension();
    }

    protected void finishedExtension() {

    }

    protected final IServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    private void checkFinished() {
        if (counter == times) {
            finished();
        }
    }

    /* package */ abstract IProgressionObserver getThis();
}
