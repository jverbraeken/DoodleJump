package resources.audio;

import system.IServiceLocator;

import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;

public final class AudioManager implements IAudioManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new AudioManager());
    }

    private AudioManager() {

    }

    public enum Sound {
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
            try {
                clip = serviceLocator.getFileSystem().readSound(filepath);
            } catch (FileNotFoundException e) {
                // TODO log the file was not found
                e.printStackTrace();
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
