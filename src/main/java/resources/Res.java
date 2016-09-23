package resources;

import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public final class Res implements IRes {

    private static transient IServiceLocator serviceLocator;
    private Map<Sprites, String> sprites = new EnumMap<>(Sprites.class);

    {
        String spritepath = "sprites/";
        // TODO this should be removed in the final version when all Sprites are ready
        for (Sprites sprite : Sprites.values()) {
            sprites.put(sprite, spritepath + "unimplemented.jpg");
        }

        // Buttons
        sprites.put(Sprites.menu, spritepath + "menu@2x.png");
        sprites.put(Sprites.play, spritepath + "play@2x.png");
        sprites.put(Sprites.playagain, spritepath + "playagain@2x.png");
        sprites.put(Sprites.resume, spritepath + "resume@2x.png");

        // Covers
        sprites.put(Sprites.background, spritepath + "bck@2x.png");
        sprites.put(Sprites.pauseCover, spritepath + "pause-cover@2x.png");
        sprites.put(Sprites.startCover, spritepath + "Default@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, spritepath + "blue-lik-Left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, spritepath + "blue-lik-Left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, spritepath + "blue-lik-Right@2x.png");
        sprites.put(Sprites.doodleRightDescend, spritepath + "blue-lik-Right-odskok@2x.png");

        // Kill screen
        sprites.put(Sprites.gameOver, spritepath + "gameover@2x.png");
        sprites.put(Sprites.killScreenBottom, spritepath + "kill-bottom@2x.png");


        // Numbers
        sprites.put(Sprites.pause, spritepath + "pause.png");
        sprites.put(Sprites.zero, spritepath + "0.png");
        sprites.put(Sprites.one, spritepath + "1.png");
        sprites.put(Sprites.two, spritepath + "2.png");
        sprites.put(Sprites.three, spritepath + "3.png");
        sprites.put(Sprites.four, spritepath + "4.png");
        sprites.put(Sprites.five, spritepath + "5.png");
        sprites.put(Sprites.six, spritepath + "6.png");
        sprites.put(Sprites.seven, spritepath + "7.png");
        sprites.put(Sprites.eight, spritepath + "8.png");
        sprites.put(Sprites.nine, spritepath + "9.png");

        // Platforms
        sprites.put(Sprites.platform1, spritepath + "platform-green@2x.png");

        // Powerups
        sprites.put(Sprites.propeller, spritepath + "powerup-propeller@2x.png");
        sprites.put(Sprites.rocket, spritepath + "powerup-rockets@2x.png");
        sprites.put(Sprites.shield, spritepath + "powerup-shield@2x.png");
        sprites.put(Sprites.spring, spritepath + "powerup-spring@2x.png");
        sprites.put(Sprites.springUsed, spritepath + "powerup-spring-used@2x.png");
        sprites.put(Sprites.trampoline, spritepath + "powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, spritepath + "powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(Sprites.scorebar, spritepath + "scorebar.png");
    }

    /**
     * Prevent instantiation of Res.
     */
    private Res() {
    }

    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Res.serviceLocator = sL;
        Res.serviceLocator.provide(new Res());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpritePath(final Sprites sprite) {
        return sprites.get(sprite);
    }
}