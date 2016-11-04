package resources.audio;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import logging.ILogger;
import system.IServiceLocator;

import javax.annotation.Nonnull;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
        public void setFramePosition(int frames) {
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
        public void setMicrosecondPosition(long microseconds) {
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
     * A map that maps enum values to clips.
     */
    private final LoadingCache<Sounds, Clip> soundsCache;
    /**
     * The logger for the AudioManager.
     */
    private ILogger logger;

    /**
     * Prevents instantiation from outside the class.
     */
    private AudioManager() {
        this.logger = AudioManager.serviceLocator.getLoggerFactory().createLogger(this.getClass());
        soundsCache = CacheBuilder.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build(
                        new CacheLoader<Sounds, Clip>() {
                            @Override
                            public Clip load(@Nonnull final Sounds sound) {
                                return loadClip(sound);
                            }
                        }
                );
        this.preload();
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
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
     * {@inheritDoc}
     */
    @Override
    public void preload() {
        for (Sounds sound : Sounds.values()) {
            soundsCache.put(sound, loadClip(sound));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Sounds sound) {
        assert sound != null;
        try {
            final Clip clip = this.soundsCache.get(sound);
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
            logger.info("Playing the sound: " + sound.toString());
        } catch (ExecutionException e) {
            this.logger.error(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Sounds sound) {
        assert sound != null;
        try {
            final Clip clip = this.soundsCache.get(sound);
            clip.stop();
            clip.setFramePosition(0);
            logger.info("Stopped the sound: " + sound.toString());
        } catch (ExecutionException e) {
            this.logger.error(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Sounds sound) {
        assert sound != null;
        try {
            final Clip clip = this.soundsCache.get(sound);
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            logger.info("Looping the sound: " + sound.toString());
        } catch (ExecutionException e) {
            this.logger.error(e);
        }
    }

    /**
     * Load a clip given an enum value from Sounds.
     *
     * @param sound The sound that must be loaded
     * @return The clip
     */
    private Clip loadClip(final Sounds sound) {
        try {
            final String filePath = sound.getFilepath();
            final Clip clip = AudioManager.serviceLocator.getFileSystem().readSound(filePath);
            logger.info("Sound loaded: \"" + filePath + "\"");
            return clip;
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
        return AudioManager.fakeClip;
    }
}
