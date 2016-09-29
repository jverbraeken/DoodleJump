package resources;

import system.Game;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Resources class, containing information about sprites.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public final class Res implements IRes {

    /**
     * The sprite path used to find the sprites.
     */
    private static final String SPRITE_PATH = "sprites/";

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        sL.provide(new Res());
    }

    /**
     * A map mapping Sprites enum to String containing the path to the sprite.
     */
    private final Map<Sprites, String> sprites = new EnumMap<>(Sprites.class);

    // Initializes the sprites.
    { resetSkin(); }

    /**
     * Prevent instantiation of Res.
     */
    private Res() {
    }

    /** {@inheritDoc} */
    @Override
    public String getSpritePath(final Sprites sprite) {
        return sprites.get(sprite);
    }

    /** {@inheritDoc} */
    @Override
    public void setSkin(final Game.Modes mode) {
        switch (mode) {
            case regular:
                resetSkin();
                break;
            case space:
                setSpaceSkin();
                break;
            case underwater:
                setUnderwaterSkin();
                break;
            default:
                resetSkin();
                break;
        }
    }

    /**
     * Reset the skin to the regular settings.
     */
    private void resetSkin() {
        // TODO this should be removed in the final version when all Sprites are ready
        for (Sprites sprite : Sprites.values()) {
            sprites.put(sprite, SPRITE_PATH + "unimplemented.jpg");
        }

        // Buttons
        sprites.put(Sprites.menu, SPRITE_PATH + "menu@2x.png");
        sprites.put(Sprites.play, SPRITE_PATH + "play@2x.png");
        sprites.put(Sprites.playAgain, SPRITE_PATH + "playAgain@2x.png");
        sprites.put(Sprites.resume, SPRITE_PATH + "resume@2x.png");
        sprites.put(Sprites.chooseMode, SPRITE_PATH + "mode-button@2x.png");

        // Covers
        sprites.put(Sprites.background, SPRITE_PATH + "bck@2x.png");
        sprites.put(Sprites.pauseCover, SPRITE_PATH + "pause-cover@2x.png");
        sprites.put(Sprites.startCover, SPRITE_PATH + "Default@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, SPRITE_PATH + "blue-lik-Left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, SPRITE_PATH + "blue-lik-Left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, SPRITE_PATH + "blue-lik-Right@2x.png");
        sprites.put(Sprites.doodleRightDescend, SPRITE_PATH + "blue-lik-Right-odskok@2x.png");

        // Kill screen
        sprites.put(Sprites.gameOver, SPRITE_PATH + "gameover@2x.png");
        sprites.put(Sprites.killScreenBottom, SPRITE_PATH + "kill-bottom@2x.png");

        // Numbers
        sprites.put(Sprites.pause, SPRITE_PATH + "pause.png");
        sprites.put(Sprites.zero, SPRITE_PATH + "0.png");
        sprites.put(Sprites.one, SPRITE_PATH + "1.png");
        sprites.put(Sprites.two, SPRITE_PATH + "2.png");
        sprites.put(Sprites.three, SPRITE_PATH + "3.png");
        sprites.put(Sprites.four, SPRITE_PATH + "4.png");
        sprites.put(Sprites.five, SPRITE_PATH + "5.png");
        sprites.put(Sprites.six, SPRITE_PATH + "6.png");
        sprites.put(Sprites.seven, SPRITE_PATH + "7.png");
        sprites.put(Sprites.eight, SPRITE_PATH + "8.png");
        sprites.put(Sprites.nine, SPRITE_PATH + "9.png");

        // Platforms
        sprites.put(Sprites.platform1, SPRITE_PATH + "platform-green@2x.png");

        // Powerups
        sprites.put(Sprites.propeller, SPRITE_PATH + "powerup-propeller@2x.png");
        sprites.put(Sprites.rocket, SPRITE_PATH + "powerup-rockets@2x.png");
        sprites.put(Sprites.shield, SPRITE_PATH + "powerup-shield@2x.png");
        sprites.put(Sprites.spring, SPRITE_PATH + "powerup-spring@2x.png");
        sprites.put(Sprites.springUsed, SPRITE_PATH + "powerup-spring-used@2x.png");
        sprites.put(Sprites.trampoline, SPRITE_PATH + "powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, SPRITE_PATH + "powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(Sprites.scoreBar, SPRITE_PATH + "scoreBar.png");

        // Choose mode icons
        sprites.put(Sprites.storyMode, SPRITE_PATH + "story-mode@4x.png");
        sprites.put(Sprites.regularMode, SPRITE_PATH + "regular-mode@4x.png");
        sprites.put(Sprites.darknessMode, SPRITE_PATH + "darkness-mode@4x.png");
        sprites.put(Sprites.invertMode, SPRITE_PATH + "invert-mode@4x.png");
        sprites.put(Sprites.spaceMode, SPRITE_PATH + "space-mode@4x.png");
        sprites.put(Sprites.underwaterMode, SPRITE_PATH + "underwater-mode@4x.png");
    }

    /**
     * Set the skin to space style.
     */
    private void setSpaceSkin() {
        resetSkin();

        // Covers
        sprites.put(Sprites.background, SPRITE_PATH + "space-bck@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, SPRITE_PATH + "space-left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, SPRITE_PATH + "space-left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, SPRITE_PATH + "space-right@2x.png");
        sprites.put(Sprites.doodleRightDescend, SPRITE_PATH + "space-right-odskok@2x.png");

        // Platforms
        sprites.put(Sprites.platform1, SPRITE_PATH + "space-platform@2x.png");

        // Power-ups
        sprites.put(Sprites.trampoline, SPRITE_PATH + "space-powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, SPRITE_PATH + "space-powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(Sprites.scoreBar, SPRITE_PATH + "space-scoreBar@2x.png");
    }

    /**
     * Set the skin to underwater style.
     */
    private void setUnderwaterSkin() {
        resetSkin();

        // Covers
        sprites.put(Sprites.background, SPRITE_PATH + "underwater-bck2@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, SPRITE_PATH + "underwater-left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, SPRITE_PATH + "underwater-left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, SPRITE_PATH + "underwater-right@2x.png");
        sprites.put(Sprites.doodleRightDescend, SPRITE_PATH + "underwater-right-odskok@2x.png");

        // Platforms
        sprites.put(Sprites.platform1, SPRITE_PATH + "underwater-platform@2x.png");

        // Power-ups
        sprites.put(Sprites.spring, SPRITE_PATH + "underwater-powerup-spring@2x.png");
        sprites.put(Sprites.springUsed, SPRITE_PATH + "underwater-powerup-spring-used@2x.png");
        sprites.put(Sprites.trampoline, SPRITE_PATH + "underwater-powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, SPRITE_PATH + "underwater-powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(Sprites.scoreBar, SPRITE_PATH + "underwater-scorebar@2x.png");
    }

}
