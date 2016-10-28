package progression;

import rendering.TextAlignment;
import system.IServiceLocator;

import java.awt.Point;

/**
 * <b>IMMUTABLE</b>
 * <p>
 * This class represents a mission that the player can complete.
 */
public final class Mission {
    private static final int TEXT_Y_OFFSET = 65;

    private final MissionType type;
    private final int times;
    private final IProgressionObserver[] observers;
    private final IServiceLocator serviceLocator;
    private final String message;

    /**
     * Prevents instantiation from outside the package.
     */
    /* package */ Mission(final IServiceLocator serviceLocator, final MissionType type, final int times, final String message, final IProgressionObserver... observers) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.observers = observers;
        this.message = message;
    }

    public MissionType getType() {
        return this.type;
    }

    public int getMaximumTimes() {
        return times;
    }

    /**
     * Renders the mission info on the screen.
     *
     * @param y The y-position at which the mission should be rendered.
     */
    public void render(int y) {
        serviceLocator.getRenderer().drawSpriteHUD(serviceLocator.getSpriteFactory().getAchievementSprite(), new Point(0, y));
        serviceLocator.getRenderer().drawTextHUD(new Point(serviceLocator.getConstants().getGameWidth() / 2, y + TEXT_Y_OFFSET), this.message, TextAlignment.center);
    }

    /* package */ void alertStartOver() {
        for (IProgressionObserver observer : observers) {
            observer.reset();
        }
    }

    /* package */ void alertFinished() {
        serviceLocator.getProgressionManager().alertMissionFinished(this);
    }
}
