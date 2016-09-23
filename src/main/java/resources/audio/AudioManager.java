package resources.audio;

import logging.ILogger;
import system.IServiceLocator;

import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;

/**
 * Standard implementation of the AudioManager. Used to load an play audio.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public final class AudioManager implements IAudioManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        AudioManager.serviceLocator = serviceLocator;
        serviceLocator.provide(new AudioManager());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private AudioManager() {
        preload();
    }

    /** {@inheritDoc} */
    @Override
    public void preload() {
        Sound.preload();
    }

    /** {@inheritDoc} */
    @Override
    public void playBijeli() {
        Sound.BIJELI.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playBlizzard() {
        Sound.BLIZZARD.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playBubbles1() {
        Sound.BUBBLES1.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playBubbles2() {
        Sound.BUBBLES2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playChill() {
        Sound.CHILL.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playCollect() {
        Sound.COLLECT.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playCrnarupa() {
        Sound.CRNARUPA.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playEggmonsterhit() {
        Sound.EGGMONSTERHIT.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playExplodingplatform() {
        Sound.EXPLODINGPLATFORM.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playExplodingplatform2() {
        Sound.EXPLODINGPLATFORM2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playFeder() {
        Sound.FEDER.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playJetpack() {
        Sound.JETPACK.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playJump() {
        Sound.JUMP.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playJumponmonster() {
        Sound.JUMPONMONSTER.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playLomise() {
        Sound.LOMISE.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playMatchsound() {
        Sound.MATCHSOUND.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playMonstercrash() {
        Sound.MONSTERCRASH.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playMonsterblizu() {
        Sound.MONSTERBLIZU.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playMonsterpogodak() {
        Sound.MONSTERPOGODAK.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playOogapucanje() {
        Sound.OOGAPUCANJE.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playOogapucanje2() {
        Sound.OOGAPUCANJE2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playPada() {
        Sound.PADA.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playPropeller() {
        Sound.PROPELLER.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playPucanje() {
        Sound.PUCANJE.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playPucanje2() {
        Sound.PUCANJE2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playRain() {
        Sound.RAIN.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playRocket() {
        Sound.ROCKET.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSnowballmonsterhit() {
        Sound.SNOWBALLMONSTERHIT.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSnowballthrow() {
        Sound.SNOWBALLTHROW.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSnowballthrow2() {
        Sound.SNOWBALLTHROW2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSoccermonstercrash() {
        Sound.SOCCERMONSTERCRASH.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSoccermonsterhit() {
        Sound.SOCCERMONSTERHIT.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playSpringshoes() {
        Sound.SPRINGSHOES.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playStart() {
        Sound.START.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playThunder() {
        Sound.THUNDER.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playTrampoline() {
        Sound.TRAMPOLINE.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playUfo() {
        Sound.UFO.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playUfopogodak() {
        Sound.UFOPOGODAK.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playUnderwatershoot() {
        Sound.UNDERWATERSHOOT.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playUnderwatershoot2() {
        Sound.UNDERWATERSHOOT2.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playUsaugateufo() {
        Sound.USAUGATEUFO.play();
    }

    /** {@inheritDoc} */
    @Override
    public void playWin() { Sound.WIN.play(); }


    private enum Sound {
        BIJELI("sounds/bijeli.wav"),
        BLIZZARD("sounds/blizzard.wav"),
        BUBBLES1("sounds/bubbles-1.wav"),
        BUBBLES2("sounds/bubbles-2.wav"),
        CHILL("sounds/chill.wav"),
        COLLECT("sounds/collect.wav"),
        CRNARUPA("sounds/crnarupa.wav"),
        EGGMONSTERHIT("sounds/egg-monster-hit.wav"),
        EXPLODINGPLATFORM("sounds/explodingplatform.wav"),
        EXPLODINGPLATFORM2("sounds/explodingplatform2.wav"),
        FEDER("sounds/feder.wav"),
        JETPACK("sounds/jetpack.wav"),
        JUMP("sounds/jump.wav"),
        JUMPONMONSTER("sounds/jumponmonster.wav"),
        LOMISE("sounds/lomise.wav"),
        MATCHSOUND("sounds/match-sound.wav"),
        MONSTERCRASH("sounds/monster-crash.wav"),
        MONSTERBLIZU("sounds/monsterblizu.wav"),
        MONSTERPOGODAK("sounds/monsterpogodak.wav"),
        OOGAPUCANJE("sounds/ooga-pucanje.wav"),
        OOGAPUCANJE2("sounds/ooga-pucanje2.wav"),
        PADA("sounds/pada.wav"),
        PROPELLER("sounds/propeller.wav"),
        PUCANJE("sounds/pucanje.wav"),
        PUCANJE2("sounds/pucanje2.wav"),
        RAIN("sounds/rain.wav"),
        ROCKET("sounds/rocket.wav"),
        SNOWBALLMONSTERHIT("sounds/snowball-monster-hit.wav"),
        SNOWBALLTHROW("sounds/snowball-throw.wav"),
        SNOWBALLTHROW2("sounds/snowball-throw2.wav"),
        SOCCERMONSTERCRASH("sounds/soccer-monster-crash.wav"),
        SOCCERMONSTERHIT("sounds/soccer-monster-hit.wav"),
        SPRINGSHOES("sounds/springshoes.wav"),
        START("sounds/start.wav"),
        THUNDER("sounds/thunder.wav"),
        TRAMPOLINE("sounds/trampoline.wav"),
        UFO("sounds/ufo.wav"),
        UFOPOGODAK("sounds/ufopogodak.wav"),
        UNDERWATERSHOOT("sounds/underwater-shoot.wav"),
        UNDERWATERSHOOT2("sounds/underwater-shoot2.wav"),
        USAUGATEUFO("sounds/usaugateufo.wav"),
        WIN("sounds/win.wav");

        private Clip clip;

        Sound(String filepath) {
            ILogger LOGGER = serviceLocator.getLoggerFactory().createLogger(AudioManager.class);
            try {
                LOGGER.info("Sound loaded: \"" + filepath + "\"");
                clip = serviceLocator.getFileSystem().readSound(filepath);
            } catch (FileNotFoundException e) {
                LOGGER.error(e);
            }
        }

        public void play() {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }

        /**
         * OPTIONAL: loads all sounds into memory.
         */
        public static void preload() {
            values();
        }
    }

}
