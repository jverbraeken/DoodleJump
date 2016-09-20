package resources.audio;

import system.IServiceLocator;

import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;

public final class AudioManager implements IAudioManager {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new AudioManager());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private AudioManager() {
        preload();
    }

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
            try {
                clip = serviceLocator.getFileSystem().readSound(filepath);
            } catch (FileNotFoundException|NullPointerException e) {
                /* TODO log the file was not found */
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

    @Override
    /** {@inheritDoc} */
    public void preload() { Sound.preload(); }

    @Override
    /** {@inheritDoc} */
    public void playBijeli() { Sound.BIJELI.play(); }

    @Override
    /** {@inheritDoc} */
    public void playBlizzard() { Sound.BLIZZARD.play(); }

    @Override
    /** {@inheritDoc} */
    public void playBubbles1() { Sound.BUBBLES1.play(); }

    @Override
    /** {@inheritDoc} */
    public void playBubbles2() { Sound.BUBBLES2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playChill() { Sound.CHILL.play(); }

    @Override
    /** {@inheritDoc} */
    public void playCollect() { Sound.COLLECT.play(); }

    @Override
    /** {@inheritDoc} */
    public void playCrnarupa() { Sound.CRNARUPA.play(); }

    @Override
    /** {@inheritDoc} */
    public void playEggmonsterhit() { Sound.EGGMONSTERHIT.play(); }

    @Override
    /** {@inheritDoc} */
    public void playExplodingplatform() { Sound.EXPLODINGPLATFORM.play(); }

    @Override
    /** {@inheritDoc} */
    public void playExplodingplatform2() { Sound.EXPLODINGPLATFORM2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playFeder() { Sound.FEDER.play(); }

    @Override
    /** {@inheritDoc} */
    public void playJetpack() { Sound.JETPACK.play(); }

    @Override
    /** {@inheritDoc} */
    public void playJump() { Sound.JUMP.play(); }

    @Override
    /** {@inheritDoc} */
    public void playJumponmonster() { Sound.JUMPONMONSTER.play(); }

    @Override
    /** {@inheritDoc} */
    public void playLomise() { Sound.LOMISE.play(); }

    @Override
    /** {@inheritDoc} */
    public void playMatchsound() { Sound.MATCHSOUND.play(); }

    @Override
    /** {@inheritDoc} */
    public void playMonstercrash() { Sound.MONSTERCRASH.play(); }

    @Override
    /** {@inheritDoc} */
    public void playMonsterblizu() { Sound.MONSTERBLIZU.play(); }

    @Override
    /** {@inheritDoc} */
    public void playMonsterpogodak() { Sound.MONSTERPOGODAK.play(); }

    @Override
    /** {@inheritDoc} */
    public void playOogapucanje() { Sound.OOGAPUCANJE.play(); }

    @Override
    /** {@inheritDoc} */
    public void playOogapucanje2() { Sound.OOGAPUCANJE2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playPada() { Sound.PADA.play(); }

    @Override
    /** {@inheritDoc} */
    public void playPropeller() { Sound.PROPELLER.play(); }

    @Override
    /** {@inheritDoc} */
    public void playPucanje() { Sound.PUCANJE.play(); }

    @Override
    /** {@inheritDoc} */
    public void playPucanje2() { Sound.PUCANJE2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playRain() { Sound.RAIN.play(); }

    @Override
    /** {@inheritDoc} */
    public void playRocket() { Sound.ROCKET.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSnowballmonsterhit() { Sound.SNOWBALLMONSTERHIT.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSnowballthrow() { Sound.SNOWBALLTHROW.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSnowballthrow2() { Sound.SNOWBALLTHROW2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSoccermonstercrash() { Sound.SOCCERMONSTERCRASH.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSoccermonsterhit() { Sound.SOCCERMONSTERHIT.play(); }

    @Override
    /** {@inheritDoc} */
    public void playSpringshoes() { Sound.SPRINGSHOES.play(); }

    @Override
    /** {@inheritDoc} */
    public void playStart() { Sound.START.play(); }

    @Override
    /** {@inheritDoc} */
    public void playThunder() { Sound.THUNDER.play(); }

    @Override
    /** {@inheritDoc} */
    public void playTrampoline() { Sound.TRAMPOLINE.play(); }

    @Override
    /** {@inheritDoc} */
    public void playUfo() { Sound.UFO.play(); }

    @Override
    /** {@inheritDoc} */
    public void playUfopogodak() { Sound.UFOPOGODAK.play(); }

    @Override
    /** {@inheritDoc} */
    public void playUnderwatershoot() { Sound.UNDERWATERSHOOT.play(); }

    @Override
    /** {@inheritDoc} */
    public void playUnderwatershoot2() { Sound.UNDERWATERSHOOT2.play(); }

    @Override
    /** {@inheritDoc} */
    public void playUsaugateufo() { Sound.USAUGATEUFO.play(); }

    @Override
    /** {@inheritDoc} */
    public void playWin() { Sound.WIN.play(); }
}
