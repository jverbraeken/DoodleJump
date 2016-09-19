package resources;

import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

public final class Res implements IRes {

    private static transient IServiceLocator serviceLocator;
    private Map<IRes.sprites, String> sprites = new EnumMap<>(IRes.sprites.class);

    {
        String spritepath = "sprites/";
        // TODO this should be removed in the final version when all sprites are ready
        for (IRes.sprites sprite : IRes.sprites.values()) {
            sprites.put(sprite, spritepath + "unimplemented.jpg");
        }

        // Buttons
        sprites.put(IRes.sprites.menu, spritepath + "menu@2x.png");
        sprites.put(IRes.sprites.play, spritepath + "play@2x.png");
        sprites.put(IRes.sprites.playagain, spritepath + "playagain@2x.png");
        sprites.put(IRes.sprites.resume, spritepath + "resume@2x.png");

        // Covers
        sprites.put(IRes.sprites.background, spritepath + "bck@2x.png");
        sprites.put(IRes.sprites.pauseCover, spritepath + "pause-cover@2x.png");
        sprites.put(IRes.sprites.startCover, spritepath + "Default@2x.png");

        // Doodle
        sprites.put(IRes.sprites.doodleLeftAscend, spritepath + "blue-lik-left@2x.png");
        sprites.put(IRes.sprites.doodleLeftDescend, spritepath + "blue-lik-left-odskok@2x.png");
        sprites.put(IRes.sprites.doodleRightAscend, spritepath + "blue-lik-right@2x.png");
        sprites.put(IRes.sprites.doodleRightDescend, spritepath + "blue-lik-right-odskok@2x.png");

        // Kill screen
        sprites.put(IRes.sprites.gameOver, spritepath + "gameover@2x.png");
        sprites.put(IRes.sprites.killScreenBottom, spritepath + "kill-bottom@2x.png");


        // Numbers
        sprites.put(IRes.sprites.pause, spritepath + "pause.png");
        sprites.put(IRes.sprites.zero, spritepath + "0.png");
        sprites.put(IRes.sprites.one, spritepath + "1.png");
        sprites.put(IRes.sprites.two, spritepath + "2.png");
        sprites.put(IRes.sprites.three, spritepath + "3.png");
        sprites.put(IRes.sprites.four, spritepath + "4.png");
        sprites.put(IRes.sprites.five, spritepath + "5.png");
        sprites.put(IRes.sprites.six, spritepath + "6.png");
        sprites.put(IRes.sprites.seven, spritepath + "7.png");
        sprites.put(IRes.sprites.eight, spritepath + "8.png");
        sprites.put(IRes.sprites.nine, spritepath + "9.png");

        // Platforms
        sprites.put(IRes.sprites.platform1, spritepath + "platform-green@2x.png");

        // Powerups
        sprites.put(IRes.sprites.propeller, spritepath + "powerup-propeller@2x.png");
        sprites.put(IRes.sprites.rocket, spritepath + "powerup-rockets@2x.png");
        sprites.put(IRes.sprites.shield, spritepath + "powerup-shield@2x.png");
        sprites.put(IRes.sprites.spring, spritepath + "powerup-spring@2x.png");
        sprites.put(IRes.sprites.springUsed, spritepath + "powerup-spring-used@2x.png");
        sprites.put(IRes.sprites.trampoline, spritepath + "powerup-trampoline@2x.png");
        sprites.put(IRes.sprites.trampolineUsed, spritepath + "powerup-trampoline-used@2x.png");

        // Top bar
        sprites.put(IRes.sprites.scorebar, spritepath + "scorebar.png");
    }

    private Res() {
    }

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Res.serviceLocator = serviceLocator;
        serviceLocator.provide(new Res());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpritePath(IRes.sprites sprite) {
        return sprites.get(sprite);
    }

}