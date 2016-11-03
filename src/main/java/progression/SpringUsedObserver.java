package progression;

import objects.powerups.ISpringCreatedObserver;
import objects.powerups.Spring;
import system.IServiceLocator;

import java.util.concurrent.Callable;

/**
 * Observes a Spring and notifies a mission when it's finished observing.
 */
public final class SpringUsedObserver extends DefaultProgressionObserver implements ISpringUsedObserver, ISpringCreatedObserver {

    {
        super.getServiceLocator().getPowerupFactory().addObserver(this);
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     */
    /* package */ SpringUsedObserver(final IServiceLocator serviceLocator, final int times) {
        super(serviceLocator, times);
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ SpringUsedObserver(final IServiceLocator serviceLocator, final int times, final double counter) {
        super(serviceLocator, times, counter);
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     */
    /* package */ SpringUsedObserver(final IServiceLocator serviceLocator, final int times, final Callable<Void> action) {
        super(serviceLocator, times, action);
    }

    /**
     * Create a default progression observer to get notifications for i.e. powerups.
     *
     * @param serviceLocator The servicelocator
     * @param times          The amount of times the progression should be raised before the goal is achieved
     * @param action         The action that should be executed after the goal is achieved
     * @param counter        The starting value for the counter, counting to {@code} times. 0 by default
     */
    /* package */ SpringUsedObserver(final IServiceLocator serviceLocator, final int times, final Callable<Void> action, final double counter) {
        super(serviceLocator, times, action, counter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    /* package */ IProgressionObserver getThis() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertSpringCreated(final Spring spring) {
        spring.addObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void alertSpringUsed() {
        super.alert();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void finishedExtension() {
        super.getServiceLocator().getPowerupFactory().removeObserver(this);
    }

}
