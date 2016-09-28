package resources;

import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Resources class, containing information about sprites.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public final class Res implements IRes {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Res.serviceLocator = sL;
        Res.serviceLocator.provide(new Res());
    }

    /**
     * A map mapping Sprites enum to String containing the path to the sprite.
     */
    private Map<Sprites, String> sprites = new EnumMap<>(Sprites.class);

    {
        String spritePath = "sprites/";
        // TODO this should be removed in the final version when all Sprites are ready
        for (Sprites sprite : Sprites.values()) {
            sprites.put(sprite, spritePath + "unimplemented.jpg");
        }

        // Buttons
        sprites.put(Sprites.menu, spritePath + "menu@2x.png");
        sprites.put(Sprites.play, spritePath + "play@2x.png");
        sprites.put(Sprites.playagain, spritePath + "playagain@2x.png");
        sprites.put(Sprites.resume, spritePath + "resume@2x.png");
        sprites.put(Sprites.chooseMode, spritePath + "mode-button@2x.png");

        // Covers
        sprites.put(Sprites.background, spritePath + "bck@2x.png");
        sprites.put(Sprites.pauseCover, spritePath + "pause-cover@2x.png");
        sprites.put(Sprites.startCover, spritePath + "Default@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, spritePath + "blue-lik-Left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, spritePath + "blue-lik-Left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, spritePath + "blue-lik-Right@2x.png");
        sprites.put(Sprites.doodleRightDescend, spritePath + "blue-lik-Right-odskok@2x.png");

        // Kill screen
        sprites.put(Sprites.gameOver, spritePath + "gameover@2x.png");
        sprites.put(Sprites.killScreenBottom, spritePath + "kill-bottom@2x.png");

        // Numbers
        sprites.put(Sprites.pause, spritePath + "pause.png");
        sprites.put(Sprites.zero, spritePath + "0.png");
        sprites.put(Sprites.one, spritePath + "1.png");
        sprites.put(Sprites.two, spritePath + "2.png");
        sprites.put(Sprites.three, spritePath + "3.png");
        sprites.put(Sprites.four, spritePath + "4.png");
        sprites.put(Sprites.five, spritePath + "5.png");
        sprites.put(Sprites.six, spritePath + "6.png");
        sprites.put(Sprites.seven, spritePath + "7.png");
        sprites.put(Sprites.eight, spritePath + "8.png");
        sprites.put(Sprites.nine, spritePath + "9.png");

        // Platforms
        sprites.put(Sprites.platform1, spritePath + "platform-green@2x.png");

        // Powerups
        sprites.put(Sprites.propeller, spritePath + "powerup-propeller@2x.png");
        sprites.put(Sprites.rocket, spritePath + "powerup-rockets@2x.png");
        sprites.put(Sprites.shield, spritePath + "powerup-shield@2x.png");
        sprites.put(Sprites.spring, spritePath + "powerup-spring@2x.png");
        sprites.put(Sprites.springUsed, spritePath + "powerup-spring-used@2x.png");
        sprites.put(Sprites.springShoes, spritePath + "powerup-springshoes-3@2x.png");
        sprites.put(Sprites.trampoline, spritePath + "powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, spritePath + "powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(Sprites.scorebar, spritePath + "scorebar.png");

        // Choose mode icons
        sprites.put(Sprites.storyMode, spritePath + "story-mode@4x.png");
        sprites.put(Sprites.regularMode, spritePath + "regular-mode@4x.png");
        sprites.put(Sprites.darknessMode, spritePath + "darkness-mode@4x.png");
        sprites.put(Sprites.invertMode, spritePath + "invert-mode@4x.png");
        sprites.put(Sprites.spaceMode, spritePath + "space-mode@4x.png");
        sprites.put(Sprites.underwaterMode, spritePath + "underwater-mode@4x.png");
    }

    /**
     * Prevent instantiation of Res.
     */
    private Res() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpritePath(final Sprites sprite) {
        return sprites.get(sprite);
    }

}