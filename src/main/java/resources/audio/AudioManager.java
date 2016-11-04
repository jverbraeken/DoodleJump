package resources.audio;

import logging.ILogger;
import system.IServiceLocator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     * A fake clip to return when a clip is requested from the clips map to prevent errors.
     */
    private static Clip fakeClip = new Clip() {

        @Override
        public void open(AudioFormat format, byte[] data, int offset, int bufferSize) throws LineUnavailableException {
        }

        @Override
        public void open(AudioInputStream stream) throws LineUnavailableException, IOException {
        }

        @Override
        public int getFrameLength() {
            return 0;
        }

        @Override
        public long getMicrosecondLength() {
            return 0;
        }

        @Override
        public void setFramePosition(int frames) {
        }

        @Override
        public void setMicrosecondPosition(long microseconds) {
        }

        @Override
        public void setLoopPoints(int start, int end) {
        }

        @Override
        public void loop(int count) {
        }

        @Override
        public void drain() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public AudioFormat getFormat() {
            return null;
        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public int available() {
            return 0;
        }

        @Override
        public int getFramePosition() {
            return 0;
        }

        @Override
        public long getLongFramePosition() {
            return 0;
        }

        @Override
        public long getMicrosecondPosition() {
            return 0;
        }

        @Override
        public float getLevel() {
            return 0;
        }

        @Override
        public Line.Info getLineInfo() {
            return null;
        }

        @Override
        public void open() throws LineUnavailableException {
        }

        @Override
        public void close() {
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public Control[] getControls() {
            return new Control[0];
        }

        @Override
        public boolean isControlSupported(Control.Type control) {
            return false;
        }

        @Override
        public Control getControl(Control.Type control) {
            return null;
        }

        @Override
        public void addLineListener(LineListener listener) {
        }

        @Override
        public void removeLineListener(LineListener listener) {
        }

    };
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
        final Clip clip = AudioManager.clips.getOrDefault(sound, AudioManager.fakeClip);
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Sounds sound) {
        final Clip clip = AudioManager.clips.getOrDefault(sound, AudioManager.fakeClip);
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Sounds sound) {
        final Clip clip = AudioManager.clips.getOrDefault(sound, AudioManager.fakeClip);
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
