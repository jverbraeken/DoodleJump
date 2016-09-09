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
        BIJELI("bijeli.mp3"),
        BLIZZARD("blizzard.mp3"),
        BUBBLES1("bubbles-1.mp3"),
        BUBBLES2("bubbles-2.mp3"),
        CHILL("chill.mp3"),
        COLLECT("collect.mp3"),
        CRNARUPA("crnarupa.mp3"),
        EGGMONSTERHIT("egg-monster-hit.mp3"),
        EXPLODINGPLATFORM("explodingplatform.mp3"),
        EXPLODINGPLATFORM2("explodingplatform2.mp3"),
        FEDER("feder.mp3"),
        JETPACK("jetpack.mp3"),
        JUMP("jump.mp3"),
        JUMPONMONSTER("jumponmonster.mp3"),
        LOMISE("lomise.mp3"),
        MATCHSOUND("match-sound.mp3"),
        MONSTERCRASH("monster-crash.mp3"),
        MONSTERBLIZU("monsterblizu.mp3"),
        MONSTERPOGODAK("monsterpogodak.mp3"),
        OOGAPUCANJE("ooga-pucanje.mp3"),
        OOGAPUCANJE2("ooga-pucanje2.mp3"),
        PADA("pada.mp3"),
        PROPELLOR("propellor.mp3"),
        PUCANJE("pucanje.mp3"),
        PUCANJE2("pucanje2.mp3"),
        RAIN("rain.mp3"),
        ROCKET("rocket.mp3"),
        SNOWBALLMONSTERHIT("snowball-monster-hit.mp3"),
        SNOWBALLTHROW("snowball-throw.mp3"),
        SNOWBALLTHROW2("snowball-throw2.mp3"),
        SOCCERMONSTERCRASH("soccer-monster-crash.mp3"),
        SOCCERMONSTERHIT("soccer-monster-hit.mp3"),
        SPRINGSHOES("springshoes.mp3"),
        START("start.mp3"),
        THUNDER("thunder.mp3"),
        TRAMPOLINE("trampoline.mp3"),
        UFO("ufo.mp3"),
        UFOPOGODAK("ufopogodak.mp3"),
        UNDERWATERSHOOT("underwater-shoot.mp3"),
        UNDERWATERSHOOT2("underwater-shoot2.mp3"),
        USAUGATEUFO("usaugateufo.mp3"),
        WIN("win.mp3")

        private Clip clip;

        Sound(String filepath) {
            try {
                clip = serviceLocator.getFileSystem().readSound(filepath);
            } catch (FileNotFoundException e) {
                // TODO log the file was not found
                e.printStackTrace();
            }
        }
    }
}
