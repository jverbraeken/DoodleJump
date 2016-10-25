package progression;

import rendering.TextAlignment;
import system.IServiceLocator;

/**
 * <b>IMMUTABLE</b>
 * <p>
 * This class represents a mission that the player can complete.
 */
public final class Mission {
    /**
     * The offset for text of the mission entry drawn at the pause menu.
     */
    private static final int TEXT_Y_OFFSET = 65;

    /**
     * The type of the mission.
     */
    private final MissionType type;
    /**
     * The observer associated with the mission.
     */
    private final IProgressionObserver observer;
    /**
     * The service locator
     */
    private final IServiceLocator serviceLocator;
    /**
     * The text drawn for the mission
     */
    private final String message;

    /**
     * Prevents instantiation from outside the package.
     */
    /* package */ Mission(final IServiceLocator serviceLocator, final MissionType type, final String message, final IProgressionObserver observer) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.observer = observer;
        this.message = message;
    }

    public MissionType getType() {
        return this.type;
    }

    public int getMaximumTimes() {
        return observer.getMaximumTimes();
    }

    /**
     * Renders the mission info on the screen.
     *
     * @param y The y-position at which the mission should be rendered.
     */
    public void render(int y) {
        serviceLocator.getRenderer().drawSpriteHUD(serviceLocator.getSpriteFactory().getAchievementSprite(), 0, y);
        serviceLocator.getRenderer().drawTextHUD(serviceLocator.getConstants().getGameWidth() / 2, y + TEXT_Y_OFFSET, this.message, TextAlignment.center);
    }

    /* package */ void alertStartOver() {
        observer.reset();
    }

    /* package */ void alertFinished() {
        serviceLocator.getProgressionManager().alertMissionFinished(this);
    }
}
