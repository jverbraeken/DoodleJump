package progression;

import rendering.IRenderer;
import rendering.TextAlignment;
import resources.IRes;
import system.IServiceLocator;

import java.awt.Point;

/**
 * <b>IMMUTABLE</b>
 * <p>This class represents a mission that the player can complete.</p>
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
     * The service locator.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The text drawn for the mission.
     */
    private final String message;

    /**
     * Prevents instantiation from outside the package.
     *
     * @param serviceLocator The service locator
     * @param type           The type of the mission
     * @param message        The message to be drawn in the PauseScreen
     * @param observer       The observer responsible for observing the progression attribute associated with the mission
     */
    /* package */ Mission(final IServiceLocator serviceLocator, final MissionType type, final String message, final IProgressionObserver observer) {
        this.serviceLocator = serviceLocator;
        this.type = type;
        this.observer = observer;
        this.message = message;
    }

    /**
     * @return The type of the mission.
     */
    public MissionType getType() {
        return this.type;
    }

    /**
     * @return The maximum amount of times its observer must be notified before the mission is considered finished.
     */
    /* package */ int getMaximumTimes() {
        return this.observer.getMaximumTimes();
    }

    /**
     * Renders the mission info on the screen.
     *
     * @param y The y-position at which the mission should be rendered.
     */
    public void render(final int y) {
        final IRenderer renderer = this.serviceLocator.getRenderer();
        renderer.drawSpriteHUD(serviceLocator.getSpriteFactory().getSprite(IRes.Sprites.achievement), new Point(0, y));
        renderer.drawTextHUD(new Point(serviceLocator.getConstants().getGameWidth() / 2, y + TEXT_Y_OFFSET), this.message, TextAlignment.center);
    }

    /**
     * Called when the mission should restart.
     */
    /* package */ void alertStartOver() {
        this.observer.reset();
    }

    /**
     * Called when the mission is finished.
     */
    /* package */ void alertFinished() {
        this.serviceLocator.getProgressionManager().alertMissionFinished(this);
    }

}
