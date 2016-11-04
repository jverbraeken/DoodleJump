package resources.audio;

import logging.ILogger;
import system.IServiceLocator;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.FileNotFoundException;

/**
 * Standard implementation of the AudioManager. Used to load an play audio.
 * <br>
 * It is not deemed necessary for all individual sounds to have a JavaDoc.
 */
public final class AudioManager implements IAudioManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        AudioManager.serviceLocator = sL;
        AudioManager.serviceLocator.provide(new AudioManager());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private AudioManager() {
        this.preload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preload() {
        Sound.preload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Sound sound) {
        sound.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Sound sound) {
        sound.clip.setFramePosition(0);
        sound.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Sound sound) {
        sound.clip.stop();
    }

    /**
     * Private enum with the paths to all files.
     */
    public enum Sound {
        BIJELI("sounds/bijeli.wav"),
        BLIZZARD("sounds/blizzard.wav"),
        BUBBLES1("sounds/bubbles-1.wav"),
        BUBBLES2("sounds/bubbles-2.wav"),
        CHILL("sounds/chill.wav"),
        COLLECT("sounds/collect.wav"),
        CRNARUPA("sounds/crnarupa.wav"),
        EGGMONSTERHIT("sounds/egg-monster-hit.wav"),
        EXPLODING_PLATFORM("sounds/explodingplatform.wav"),
        EXPLODING_PLATFORM2("sounds/explodingplatform2.wav"),
        FEDER("sounds/feder.wav"),
        JETPACK("sounds/jetpack.wav"),
        JUMP("sounds/jump.wav"),
        JUMP_ON_MONSTER("sounds/jumponmonster.wav"),
        LOMISE("sounds/lomise.wav"),
        MATCH_SOUND("sounds/match-sound.wav"),
        MONSTER_CRASH("sounds/monster-crash.wav"),
        MONSTER_BLIZU("sounds/monsterblizu.wav"),
        MONSTER_POGODAK("sounds/monsterpogodak.wav"),
        OOGAPUCANJE("sounds/ooga-pucanje.wav"),
        OOGAPUCANJE2("sounds/ooga-pucanje2.wav"),
        PADA("sounds/pada.wav"),
        PROPELLER("sounds/propeller.wav"),
        PUCANJE("sounds/pucanje.wav"),
        PUCANJE2("sounds/pucanje2.wav"),
        RAIN("sounds/rain.wav"),
        ROCKET("sounds/rocket.wav"),
        SNOWBALL_MONSTER_HIT("sounds/snowball-monster-hit.wav"),
        SNOWBALL_THROW("sounds/snowball-throw.wav"),
        SNOWBALL_THROW2("sounds/snowball-throw2.wav"),
        SOCCER_MONSTER_CRASH("sounds/soccer-monster-crash.wav"),
        SOCCER_MONSTER_HIT("sounds/soccer-monster-hit.wav"),
        SPRING_SHOES("sounds/springshoes.wav"),
        START("sounds/start.wav"),
        THUNDER("sounds/thunder.wav"),
        THEME_SONG("sounds/chaserthemesong.wav"),
        TRAMPOLINE("sounds/trampoline.wav"),
        UFO("sounds/ufo.wav"),
        UFO_POGODAK("sounds/ufopogodak.wav"),
        UNDERWATER_SHOOT("sounds/underwater-shoot.wav"),
        UNDERWATER_SHOOT2("sounds/underwater-shoot2.wav"),
        USAUGATEUFO("sounds/usaugateufo.wav"),
        WIN("sounds/win.wav");

        /**
         * A clip containing the sounds.
         */
        private Clip clip;

        /**
         * Preload a sound.
         * @param filepath The path of the file of the sound.
         */
        Sound(final String filepath) {
            ILogger logger = AudioManager.serviceLocator.getLoggerFactory().createLogger(AudioManager.class);
            try {
                this.clip = AudioManager.serviceLocator.getFileSystem().readSound(filepath);
                logger.info("Sound loaded: \"" + filepath + "\"");
            } catch (FileNotFoundException e) {
                logger.error(e);
            }
        }

        /**
         * Load all sounds into memory.
         */
        private static void preload() {
            values();
        }

        /**
         * Play a sound.
         */
        private void play() {
            this.clip.stop();
            this.clip.setFramePosition(0);
            this.clip.start();
        }
    }

}
