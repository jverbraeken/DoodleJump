package resources.audio;

import logging.ILogger;
import system.IServiceLocator;

import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;
import java.util.EnumMap;

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
     * A map that maps enum values to clips.
     */
    private static EnumMap<Sounds, Clip> clips = new EnumMap<>(Sounds.class);
    /**
     * The logger for the AudioManager.
     */
    private ILogger logger;

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
        this.logger = AudioManager.serviceLocator.getLoggerFactory().createLogger(this.getClass());
        this.preload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preload() {
        this.loadClip(Sounds.BIJELI);
        this.loadClip(Sounds.BLIZZARD);
        this.loadClip(Sounds.BUBBLES1);
        this.loadClip(Sounds.BUBBLES2);
        this.loadClip(Sounds.CHILL);
        this.loadClip(Sounds.COLLECT);
        this.loadClip(Sounds.CRNARUPA);
        this.loadClip(Sounds.EGGMONSTERHIT);
        this.loadClip(Sounds.EXPLODING_PLATFORM);
        this.loadClip(Sounds.EXPLODING_PLATFORM2);
        this.loadClip(Sounds.FEDER);
        this.loadClip(Sounds.JETPACK);
        this.loadClip(Sounds.JUMP);
        this.loadClip(Sounds.JUMP_ON_MONSTER);
        this.loadClip(Sounds.LOMISE);
        this.loadClip(Sounds.MATCH_SOUND);
        this.loadClip(Sounds.MONSTER_CRASH);
        this.loadClip(Sounds.MONSTER_BLIZU);
        this.loadClip(Sounds.MONSTER_POGODAK);
        this.loadClip(Sounds.OOGAPUCANJE);
        this.loadClip(Sounds.OOGAPUCANJE2);
        this.loadClip(Sounds.PADA);
        this.loadClip(Sounds.PROPELLER);
        this.loadClip(Sounds.PUCANJE);
        this.loadClip(Sounds.PUCANJE2);
        this.loadClip(Sounds.RAIN);
        this.loadClip(Sounds.ROCKET);
        this.loadClip(Sounds.SNOWBALL_MONSTER_HIT);
        this.loadClip(Sounds.SNOWBALL_THROW);
        this.loadClip(Sounds.SNOWBALL_THROW2);
        this.loadClip(Sounds.SOCCER_MONSTER_CRASH);
        this.loadClip(Sounds.SOCCER_MONSTER_HIT);
        this.loadClip(Sounds.SPRING_SHOES);
        this.loadClip(Sounds.START);
        this.loadClip(Sounds.THUNDER);
        this.loadClip(Sounds.THEME_SONG);
        this.loadClip(Sounds.TRAMPOLINE);
        this.loadClip(Sounds.UFO);
        this.loadClip(Sounds.UFO_POGODAK);
        this.loadClip(Sounds.UNDERWATER_SHOOT);
        this.loadClip(Sounds.UNDERWATER_SHOOT2);
        this.loadClip(Sounds.USAUGATEUFO);
        this.loadClip(Sounds.WIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Sounds sound) {
        Clip clip = AudioManager.clips.get(sound);
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Sounds sound) {
        Clip clip = AudioManager.clips.get(sound);
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Sounds sound) {
        Clip clip = AudioManager.clips.get(sound);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Load a clip given an enum value from Sounds.
     *
     * @param sound The enum value.
     */
    private void loadClip(final Sounds sound) {
        try {
            String filePath = sound.getFilepath();
            Clip clip = AudioManager.serviceLocator.getFileSystem().readSound(filePath);
            AudioManager.clips.put(sound, clip);
            logger.info("Sound loaded: \"" + filePath + "\"");
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }

}
