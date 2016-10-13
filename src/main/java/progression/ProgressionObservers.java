package progression;

import objects.powerups.IPowerup;

import java.util.HashSet;
import java.util.Set;

/**
 * Lists all kind of progression observers.
 */
public enum ProgressionObservers {
    disappearingPowerup(IDisappearingPowerupObserver.class),
    equipmentPowerup(IEquipmentPowerupObserver.class),
    height(IHeightObserver.class),
    jetpack(IJetpackUsedObserver.class),
    jumpable(IJumpablePowerupObserver.class),
    powerup(IPowerupObserver.class),
    progression(IProgressionObserver.class),
    propeller(IPropellerUsedObserver.class),
    sizeDown(ISizeDownUsedObserver.class),
    sizeUp(ISizeUpUserObserver.class),
    springShoes(ISpringShoesUsedObserver.class),
    spring(ISpringUsedObserver.class),
    trampoline(ITrampolineJumpedObserver.class);

    private final Set<IProgressionObserver> observers = new HashSet<>();
    private final Class<?> myClass; // class is not allowed as variable name
    private final Class<?> mySuperClass;

    <T1 extends IProgressionObserver> ProgressionObservers(Class<T1> myClass) {
        this.myClass = myClass;
        if (myClass != IProgressionObserver.class) {
            this.mySuperClass = myClass.getSuperclass();
        }
        else
        {
            this.mySuperClass = null;
        }
    }

    /* package */ void addObserver(IProgressionObserver observer) {
        observers.add(observer);
    }

    /* package */ void alertObservers() {
        for (IProgressionObserver observer : observers) {
            observer.alert();
        }
    }

    /* package */ void alertObservers(final double amount) {
        for (IProgressionObserver observer : observers) {
            observer.alert(amount);
        }
    }
}
