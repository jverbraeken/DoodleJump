package progression;

import java.util.concurrent.Callable;

public class ProgressionObserver implements
        IDisappearingPowerupObserver,
        IEquipmentPowerupObserver,
        IHeightObserver,
        IJetpackUsedObserver,
        IJumpablePowerupObserver,
        IPickupObserver,
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
    private int counter = 0;

    /* package */ ProgressionObserver(final int times, final Callable<Void> action) {
        this.times = times;
        this.action = action;
    }

    /* package */ ProgressionObserver(final int times, final Callable<Void> action, int counter) {
        this(times, action);
        this.counter = counter;
    }

    @Override
    public void alert() {
        counter++;
        if (counter >= times) {
            try {
                action.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getProgression() {
        return counter;
    }
}
