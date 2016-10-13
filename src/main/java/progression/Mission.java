package progression;

import resources.sprites.ISprite;
import system.IRenderable;
import system.IServiceLocator;

/**
 * <b>IMMUTABLE</b>
 *
 * This class represents a mission that the player can complete.
 */
public final class Mission {
    private static final int MESSAGE_TEXT_OFFSET = 84 / 2;
    private static final int MESSAGE_OFFSET = 150;
    private static final int MESSAGE_SPACE_BETWEEN = 200;
    private final MissionType type;
    private final int times;
    private final IProgressionObserver observer;
    private final IServiceLocator serviceLocator;
    private final String message;

    /**
     * Prevents instantiation from outside the package.
     */
    /* package */ Mission(final IServiceLocator serviceLocator, final MissionType type, final int times, final IProgressionObserver observer, final String message) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.times = times;
        this.observer = observer;
        this.message = message;
    }

    public MissionType getType() {
        return this.type;
    }

    public int getMaximumTimes() {
        return times;
    }

    public int getProgression() {
        return this.observer.getProgression();
    }

    /**
     * Renders the mission info on the screen.
     *
     * @param number The position of the mission info box in the mission list. Must be between 0 (included)
     *               and 2 (included).
     */
    public void render(int number) {
        assert number >= 0 && number < 3;
        serviceLocator.getRenderer().drawSpriteHUD(serviceLocator.getSpriteFactory().getAchievementSprite(), 0, MESSAGE_OFFSET + number * MESSAGE_SPACE_BETWEEN);
        serviceLocator.getRenderer().drawTextHUD(serviceLocator.getConstants().getGameWidth() / 2, MESSAGE_OFFSET + number * MESSAGE_SPACE_BETWEEN, this.message);
    }
}
