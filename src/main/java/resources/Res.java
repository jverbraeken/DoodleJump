package resources;

import system.Game;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Resources class, containing information about sprites.
 * <br>
 * It is not deemed necessary for all individual resources to have a JavaDoc.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public final class Res implements IRes {

    /**
     * The sprite path used to find the sprites.
     */
    private static final String SPRITE_PATH = "sprites/";
    /**
     * A map mapping Sprites enum to String containing the path to the sprite.
     */
    private final Map<Sprites, String> sprites = new EnumMap<>(Sprites.class);

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        sL.provide(new Res());
    }

    /**
     * Prevent instantiation of Res.
     */
    private Res() {
        this.setDefaultSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpritePath(final Sprites sprite) {
        return this.sprites.get(sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSkin(final Game.Modes mode) {
        switch (mode) {
            case regular:
                this.setDefaultSkin();
                break;
            case space:
                this.setSpaceSkin();
                break;
            case underwater:
                this.setUnderwaterSkin();
                break;
            case darkness:
                this.setDarknessSkin();
                break;
            default:
                this.setDefaultSkin();
                break;
        }
    }

    /**
     * Reset the skin to the regular settings.
     */
    private void setDefaultSkin() {
        for (Sprites sprite : Sprites.values()) {
            sprites.put(sprite, SPRITE_PATH + "unimplemented.jpg");
        }

        // Buttons
        sprites.put(Sprites.menu, SPRITE_PATH + "menu@2x.png");
        sprites.put(Sprites.play, SPRITE_PATH + "play@2x.png");
        sprites.put(Sprites.multiplayer, SPRITE_PATH + "multiplayer@2x.png");
        sprites.put(Sprites.playAgain, SPRITE_PATH + "playagain@2x.png");
        sprites.put(Sprites.resume, SPRITE_PATH + "resume@2x.png");
        sprites.put(Sprites.scoreButton, SPRITE_PATH + "scores-on@2x.png");
        sprites.put(Sprites.chooseMode, SPRITE_PATH + "mode-button2@2x.png");
        sprites.put(Sprites.shop, SPRITE_PATH + "shop@2x.png");

        // Covers
        sprites.put(Sprites.background, SPRITE_PATH + "bck@2x.png");
        sprites.put(Sprites.pauseCover, SPRITE_PATH + "pause-cover-3@2x.png");
        sprites.put(Sprites.startCover, SPRITE_PATH + "Default@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, SPRITE_PATH + "blue-lik-left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, SPRITE_PATH + "blue-lik-left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, SPRITE_PATH + "blue-lik-right@2x.png");
        sprites.put(Sprites.doodleRightDescend, SPRITE_PATH + "blue-lik-right-odskok@2x.png");

        // Enemies
        sprites.put(Sprites.ordinaryMonster, SPRITE_PATH + "ordinaryMonster.png");
        sprites.put(Sprites.threeEyedMonster1, SPRITE_PATH + "threeEyedMonster1.png");
        sprites.put(Sprites.threeEyedMonster2, SPRITE_PATH + "threeEyedMonster2.png");
        sprites.put(Sprites.threeEyedMonster3, SPRITE_PATH + "threeEyedMonster3.png");
        sprites.put(Sprites.vampireMonster1, SPRITE_PATH + "vampireMonster1.png");
        sprites.put(Sprites.vampireMonster2, SPRITE_PATH + "vampireMonster2.png");
        sprites.put(Sprites.vampireMonster3, SPRITE_PATH + "vampireMonster3.png");
        sprites.put(Sprites.vampireMonster4, SPRITE_PATH + "vampireMonster4.png");
        sprites.put(Sprites.vampireMonster5, SPRITE_PATH + "vampireMonster5.png");

        // Stars
        sprites.put(Sprites.confusedStars1, SPRITE_PATH + "stars1@2x.png");
        sprites.put(Sprites.confusedStars2, SPRITE_PATH + "stars2@2x.png");
        sprites.put(Sprites.confusedStars3, SPRITE_PATH + "stars3@2x.png");

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
        sprites.put(Sprites.platformHorizontal, SPRITE_PATH + "platform-blue@2x.png");
        sprites.put(Sprites.platformVertical, SPRITE_PATH + "platform-gray@2x.png");
        sprites.put(Sprites.platformBroken1, SPRITE_PATH + "platform-broken1@2x.png");
        sprites.put(Sprites.platformBroken2, SPRITE_PATH + "platform-broken2@2x.png");
        sprites.put(Sprites.platformBroken3, SPRITE_PATH + "platform-broken3@2x.png");
        sprites.put(Sprites.platformBroken4, SPRITE_PATH + "platform-broken4@2x.png");

        // Powerups
        sprites.put(Sprites.propeller, SPRITE_PATH + "powerup-propeller@2x.png");
        sprites.put(Sprites.jetpack, SPRITE_PATH + "powerup-jetpack@2x.png");
        sprites.put(Sprites.spring, SPRITE_PATH + "powerup-spring@2x.png");
        sprites.put(Sprites.springUsed, SPRITE_PATH + "powerup-spring-used@2x.png");
        sprites.put(Sprites.springShoes, SPRITE_PATH + "powerup-springshoes-3@2x.png");
        sprites.put(Sprites.trampoline, SPRITE_PATH + "powerup-trampoline@2x.png");
        sprites.put(Sprites.trampolineUsed, SPRITE_PATH + "powerup-trampoline-used@2x.png");
        sprites.put(Sprites.shield, SPRITE_PATH + "powerup-shield@2x.png");
        sprites.put(Sprites.sizeUp, SPRITE_PATH + "powerup-size-up@2x.png");
        sprites.put(Sprites.sizeDown, SPRITE_PATH + "powerup-size-down@2x.png");
        sprites.put(Sprites.circusCannon, SPRITE_PATH + "circuscannon-unused.png");
        sprites.put(Sprites.circusCannonUsed, SPRITE_PATH + "circuscannon-used.png");
        sprites.put(Sprites.rocketLauncher, SPRITE_PATH + "rocketlauncher-unused.png");
        sprites.put(Sprites.rocketLauncherUsed, SPRITE_PATH + "rocketlauncher-used.png");
        sprites.put(Sprites.doubleSpring, SPRITE_PATH + "powerup-double-spring.png");
        sprites.put(Sprites.doubleSpringUsed, SPRITE_PATH + "powerup-double-spring-used.png");
        sprites.put(Sprites.titaniumSpring, SPRITE_PATH + "powerup-titanium-spring.png");
        sprites.put(Sprites.titaniumSpringUsed, SPRITE_PATH + "powerup-titanium-spring-used.png");

        // Passive

        sprites.put(Sprites.jetpack0, SPRITE_PATH + "jetpack-0@2x.png");
        sprites.put(Sprites.jetpack1, SPRITE_PATH + "jetpack-1@2x.png");
        sprites.put(Sprites.jetpack2, SPRITE_PATH + "jetpack-2@2x.png");
        sprites.put(Sprites.jetpack3, SPRITE_PATH + "jetpack-3@2x.png");
        sprites.put(Sprites.jetpack4, SPRITE_PATH + "jetpack-4@2x.png");
        sprites.put(Sprites.jetpack5, SPRITE_PATH + "jetpack-5@2x.png");
        sprites.put(Sprites.jetpack6, SPRITE_PATH + "jetpack-6@2x.png");
        sprites.put(Sprites.jetpack7, SPRITE_PATH + "jetpack-7@2x.png");
        sprites.put(Sprites.jetpack8, SPRITE_PATH + "jetpack-8@2x.png");
        sprites.put(Sprites.jetpack9, SPRITE_PATH + "jetpack-9@2x.png");
        sprites.put(Sprites.propeller0, SPRITE_PATH + "propeller-0@2x.png");
        sprites.put(Sprites.propeller1, SPRITE_PATH + "propeller-1@2x.png");
        sprites.put(Sprites.propeller2, SPRITE_PATH + "propeller-2@2x.png");


        // Score screen
        sprites.put(Sprites.scoreScreenBottom, SPRITE_PATH + "high-scores-bottom@2x.png");
        sprites.put(Sprites.scoreScreenLeft, SPRITE_PATH + "high-scores-left@2x.png");
        sprites.put(Sprites.scoreScreenTop, SPRITE_PATH + "high-scores-top@2x.png");

        // Top bar
        sprites.put(Sprites.scoreBar, SPRITE_PATH + "scorebar.png");

        // Choose mode icons
        sprites.put(Sprites.storyMode, SPRITE_PATH + "story-mode@4x.png");
        sprites.put(Sprites.regularMode, SPRITE_PATH + "regular-mode@4x.png");
        sprites.put(Sprites.darknessMode, SPRITE_PATH + "darkness-mode@4x.png");
        sprites.put(Sprites.invertMode, SPRITE_PATH + "invert-mode@4x.png");
        sprites.put(Sprites.spaceMode, SPRITE_PATH + "space-mode@4x.png");
        sprites.put(Sprites.underwaterMode, SPRITE_PATH + "underwater-mode@4x.png");

        // Coins
        sprites.put(Sprites.coin1, SPRITE_PATH + "coin1.png");
        sprites.put(Sprites.coin2, SPRITE_PATH + "coin2.png");
        sprites.put(Sprites.coin3, SPRITE_PATH + "coin3.png");
        sprites.put(Sprites.coin4, SPRITE_PATH + "coin4.png");
        sprites.put(Sprites.coin5, SPRITE_PATH + "coin5.png");
        sprites.put(Sprites.coin6, SPRITE_PATH + "coin6.png");
        sprites.put(Sprites.coin7, SPRITE_PATH + "coin7.png");
        sprites.put(Sprites.coin8, SPRITE_PATH + "coin8.png");
        sprites.put(Sprites.coin9, SPRITE_PATH + "coin9.png");
        sprites.put(Sprites.coin10, SPRITE_PATH + "coin10.png");

        // Missions
        sprites.put(Sprites.achievement, SPRITE_PATH + "achievement@2x.png");

        // Unimplemented
        sprites.put(Sprites.unimplemented, SPRITE_PATH + "unimplemented.jpg");
    }

    /**
     * Set the skin to space style.
     */
    private void setSpaceSkin() {
        this.setDefaultSkin();

        // Covers
        sprites.put(Sprites.startCover, SPRITE_PATH + "space-Default@2x.png");
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
        this.setDefaultSkin();

        // Covers
        sprites.put(Sprites.startCover, SPRITE_PATH + "underwater-Default@2x.png");
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

    /**
     * Set the skin to underwater style.
     */
    private void setDarknessSkin() {
        this.setDefaultSkin();

        // Covers
        sprites.put(Sprites.startCover, SPRITE_PATH + "darkness-Default@2x.png");
        sprites.put(Sprites.background, SPRITE_PATH + "darkness-bck@2x.png");

        // Doodle
        sprites.put(Sprites.doodleLeftAscend, SPRITE_PATH + "ghost-left@2x.png");
        sprites.put(Sprites.doodleLeftDescend, SPRITE_PATH + "ghost-left-odskok@2x.png");
        sprites.put(Sprites.doodleRightAscend, SPRITE_PATH + "ghost-right@2x.png");
        sprites.put(Sprites.doodleRightDescend, SPRITE_PATH + "ghost-right-odskok@2x.png");

        // Platforms
        sprites.put(Sprites.platform1, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformHorizontal, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformVertical, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformBroken1, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformBroken2, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformBroken3, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platformBroken4, SPRITE_PATH + "invisible-platform@2x.png");
        sprites.put(Sprites.platform4, SPRITE_PATH + "space-platform@2x.png");

        // Top bar
        sprites.put(Sprites.scoreBar, SPRITE_PATH + "space-scorebar@2x.png");
    }

}
