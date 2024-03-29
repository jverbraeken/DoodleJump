package filesystem;

import com.google.gson.Gson;
import logging.ILogger;
import system.Game;
import system.IServiceLocator;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            public void write(@Nonnull final char[] cbuf, final int off, final int len) throws IOException {
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
            fw = new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.logWriter = new BufferedWriter(fw);
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
        FileSystem.serviceLocator = sL;
        FileSystem.serviceLocator.provide(new FileSystem());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readResourceFile(final String filename) throws FileNotFoundException {
        File file = this.getResourceFile(filename);
        return readFile(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readProjectFile(final String filename) throws FileNotFoundException {
        File file;
        try {
            file = this.getProjectFile(filename);
        } catch (IOException e) {
            throw new FileNotFoundException(filename + " was not found by the FileSystem");
        }

        return readFile(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream readBinaryFile(final String filename) throws FileNotFoundException {
        File file = this.getResourceFile(filename);
        InputStream inputStream = new FileInputStream(file);
        return new BufferedInputStream(inputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage readImage(final String filename) throws FileNotFoundException {
        File file = this.getResourceFile(filename);

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
        File file = this.getResourceFile(filename);

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
        File file = this.getResourceFile(filename);
        writeFile(file, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeProjectFile(final String filename, final String content) throws IOException {
        File file = this.getProjectFile(filename);
        writeFile(file, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFile(final String filename) {
        File file = new File(filename);
        boolean success = file.delete();

        ILogger logger = FileSystem.serviceLocator.getLoggerFactory().createLogger(this.getClass());
        logger.error("The file \"" + filename + "\" has been deleted successfully=" + success);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFile(final String filename) {
        File file = null;
        try {
            file = this.getProjectFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert file != null;
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
            this.logWriter.write(content + "\n");
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream writeBinaryFile(final String filename) throws FileNotFoundException {
        File file = this.getResourceFile(filename);
        OutputStream outputStream = new FileOutputStream(file);
        return new BufferedOutputStream(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getResourceFile(final String filename) throws FileNotFoundException, IllegalArgumentException {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        String newFilename = filename.replaceAll("\\\\", "/");

        if (newFilename.charAt(0) != '/') {
            newFilename = '/' + newFilename;
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
        File file = new File(filename);
        // The only reason we do this is to suppress a FindBugs warning
        final boolean didntExist = file.createNewFile();
        if (didntExist) {
            System.out.println("New file called \"" + filename + "\" created");
        }

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

        return new Gson().fromJson(json, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeJson(final Object image) {
        final Gson gson = new Gson();
        return gson.toJson(image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font getFont(final String name) {
        Font font;
        try {
            File fontFile = getResourceFile("fonts/" + name);
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();

            ge.registerFont(font);
        } catch (IOException | FontFormatException ex) {
            System.err.println(name + " not loaded.  Using serif font.");
            font = DEFAULT_FONT;
        }
        return font;
    }

    /**
     * Writes the text to a file.
     *
     * @param file The file to which {@code content} should be written
     * @param content The text that should be written to {@code file}
     */
    private void writeFile(final File file, final String content) {
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
     * Writes the content of a file.
     *
     * @param file The file that must be read
     * @return A list containing the lines of the file
     */
    private List<String> readFile(final File file) {
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
}
