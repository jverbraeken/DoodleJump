package filesystem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logging.ILogger;
import rendering.Renderer;
import system.Game;
import system.IServiceLocator;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for {@link IFileSystem}. Suitable for Windows, MacOS and some Linux distributions.
 */
public final class FileSystem implements IFileSystem {

    /**
     * The font used when the font requested could not be found.
     */
    private static final Font DEFAULT_FONT = new Font("serif", Font.PLAIN, 24);
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The writer to the log files.
     */
    private final Writer logWriter;

    /**
     * Prevents instantiation from outside the class.
     */
    private FileSystem() {
        // If the LOGFILE is not found, the game should either crash on the exception or not crash at all (so also
        // not when something is logged. Therefore we provide an empty interface instead of null to prevent
        // a {@link NullPointerException}.
        Writer fw = new Writer() {
            @Override
            public void write(final char[] cbuf, final int off, final int len) throws IOException {
            }

            @Override
            public void flush() throws IOException {
            }

            @Override
            public void close() throws IOException {
            }
        };
        File logFile = new File(Game.LOGFILE_NAME);

        try {
            fw = new FileWriter(logFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logWriter = new BufferedWriter(fw);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        FileSystem.serviceLocator = sL;
        sL.provide(new FileSystem());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readResourceFile(final String filename) throws FileNotFoundException {
        File file = getResourceFile(filename);
        List<String> result = new ArrayList<>();

        String line;
        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readProjectFile(final String filename) throws FileNotFoundException {
        File file;
        try {
            file = getProjectFile(filename);
        } catch (IOException e) {
            throw new FileNotFoundException(filename + " was not found by the FileSystem");
        }

        List<String> result = new ArrayList<>();

        String line;
        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream readBinaryFile(final String filename) throws FileNotFoundException {
        File file = getResourceFile(filename);
        InputStream inputStream = new FileInputStream(file);
        return new BufferedInputStream(inputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage readImage(final String filename) throws FileNotFoundException {
        File file = getResourceFile(filename);

        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Clip readSound(final String filename) throws FileNotFoundException {
        File file = getResourceFile(filename);

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            return clip;
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeResourceFile(final String filename, final String content) throws FileNotFoundException {
        File file = getResourceFile(filename);
        try (final OutputStream fs = new FileOutputStream(file);
             final Writer ow = new OutputStreamWriter(fs, StandardCharsets.UTF_8);
             final Writer bufferedFileWriter = new BufferedWriter(ow)) {
            bufferedFileWriter.write(content);
            bufferedFileWriter.close();
            ow.close();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeProjectFile(final String filename, final String content) throws FileNotFoundException {
        File file;
        try {
            file = getProjectFile(filename);
        } catch (IOException e) {
            throw new FileNotFoundException(filename + " was not found by the FileSystem");
        }

        try (final OutputStream fs = new FileOutputStream(file);
             final Writer ow = new OutputStreamWriter(fs, StandardCharsets.UTF_8);
             final Writer bufferedFileWriter = new BufferedWriter(ow)) {
            bufferedFileWriter.write(content);
            bufferedFileWriter.close();
            ow.close();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFile(final String filename) {
        File file = new File(filename);
        boolean success = file.delete();

        if (!success) {
            // TODO If logger is a field of FileSystem, FileSystem references LoggerFactory which is created AFTER
            // FileSystem. Consider a two-step initialisation of dependent objects.
            ILogger logger = FileSystem.serviceLocator.getLoggerFactory().createLogger(this.getClass());
            logger.error("The file \"" + filename + "\" could not be deleted!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFile(final String filename) {
        File file = null;
        try {
            file = getProjectFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (final OutputStream outputStream = new FileOutputStream(file);
             final Writer w = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             final PrintWriter pw = new PrintWriter(w, false)) {
            pw.flush();
            pw.close();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(final String content) {
        try {
            logWriter.write(content + "\n");
            // Definitely not efficient, but because an application normally crashes soon after a helpful log message
            // is logged we want to take this performance penalty anyway.
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream writeBinaryFile(final String filename) throws FileNotFoundException {
        File file = getResourceFile(filename);
        OutputStream outputStream = new FileOutputStream(file);
        return new BufferedOutputStream(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getResourceFile(final String filename) throws FileNotFoundException {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        String newFilename = filename.replaceAll("\\\\", "/");

        if (newFilename.charAt(0) != '/') {
            newFilename = "/" + newFilename;
        }

        URL url = getClass().getResource(newFilename);
        if (url == null) {
            throw new FileNotFoundException("The following file could not be found: \"" + newFilename + "\"");
        }
        return new File(url.getFile());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getProjectFile(final String filename) throws IOException {
        File f = new File(filename);
        f.createNewFile();

        return new File(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseJson(final String filename, final Type type) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        readProjectFile(filename).forEach(sb::append);
        String json = sb.toString();

        return (new Gson()).fromJson(json, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeJson(final IToJsonSerializable image) {
        final Gson gson = new Gson();
        final String result = gson.toJson(image);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font getFont(String name) {
        if (name == null) {
            return DEFAULT_FONT;
        }

        Font font;
        try {
            File fontFile = getResourceFile("fonts/" + name);
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();

            ge.registerFont(font);
        } catch (Exception ex) {
            System.err.println(name + " not loaded.  Using serif font.");
            font = DEFAULT_FONT;
        }
        return font;
    }
}
