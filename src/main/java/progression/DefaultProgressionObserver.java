package progression;

import system.IServiceLocator;

import java.util.concurrent.Callable;

/**
 * Observes a progression attribute and notifies a mission when it's finished observing.
 */
public abstract class DefaultProgressionObserver implements IProgressionObserver {
    /**
     * The service locator.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The number of times the observer should be called before the observer considers itself finished.
     */
    private final int times;
    /**
     * The action to be executed when the the observer is finished.
     */
    private final Callable<Void> action;
    /**
     * The mission related to be alerted when the observer is notificated {@link #times} times.
     */
    private Mission mission;
    /**
     * Used to count the number of times that the observer was notificated.
     */
    private double counter;

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final int times) {
        this.serviceLocator = serviceLocator;
        this.times = times;
        this.action = () -> null;
        this.counter = 0d;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final int times, final double counter) {
        this.serviceLocator = serviceLocator;
        this.times = times;
        this.action = () -> null;
        this.counter = counter;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final int times, final Callable<Void> action) {
        this.serviceLocator = serviceLocator;
        this.times = times;
        this.action = action;
        this.counter = 0d;
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ DefaultProgressionObserver(final IServiceLocator serviceLocator, final int times, final Callable<Void> action, final double counter) {
        this.serviceLocator = serviceLocator;
        this.times = times;
        this.action = action;
        this.counter = counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getProgression() {
        return counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void reset() {
        counter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setMission(final Mission mission) {
        this.mission = mission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getMaximumTimes() {
        return times;
    }

    /**
     * Executes everything that must be done when the mission is finished successfully.
     */
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

    /**
     * Can be used by subclasses to extend the functionality of {@link #alert()} and {@link #alert(double)}.
     */
    protected void finishedExtension() {

    }

    /**
     * @return The service locator
     */
    protected final IServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    /**
     * @return Literally {@code this;}
     */
    /* package */ abstract IProgressionObserver getThis();

    /**
     * Returns true if the observer has reached its goal (was notificated {@link #times} times.
     */
    private void checkFinished() {
        if (counter == times) {
            finished();
        }
    }
}
