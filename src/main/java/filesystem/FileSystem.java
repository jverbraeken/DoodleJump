package filesystem;

import system.Game;
import system.IServiceLocator;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for {@link IFileSystem}. Suitable for Windows, MacOS and some Linux distributions.
 */
public final class FileSystem implements IFileSystem {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;
    /**
     * The writer to the log files.
     */
    private final Writer logWriter;

    /**
     * Prevents instantiation from outside the class.
     */
    private FileSystem() {
        // If the LOGFILE is not found, the game should either crash on the exception or not crash at all (so also
        // not when something is logged. Therefore we provide an emtpy interface instead of null to prevent
        // a {@link NullPointerException}.
        Writer fw = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        };
        try {
            fw = new FileWriter(Game.LOGFILE_NAME, true);
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
        FileSystem.sL = sL;
        sL.provide(new FileSystem());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> readTextFile(final String filename) throws FileNotFoundException {
        File file = getFile(filename);

        Reader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> result = new ArrayList<>();

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
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
        File file = getFile(filename);

        InputStream inputStream = new FileInputStream(
                file);
        return new BufferedInputStream(inputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage readImage(final String filename) throws FileNotFoundException {
        File file = getFile(filename);

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
        File file = getFile(filename);

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
    public void writeTextFile(final String filename, final String content) throws FileNotFoundException {
        File file = getFile(filename);

        try {
            Writer fileWriter = new FileWriter(file);
            Writer bufferedFileWriter = new BufferedWriter(fileWriter);
            bufferedFileWriter.write(content);
            bufferedFileWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFile(final String filename) {
        (new File(filename)).delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearFile(final String filename) {
        try (final FileWriter fw = new FileWriter(filename, false);
             final PrintWriter pw = new PrintWriter(fw, false)) {
            pw.flush();
            pw.close();
            fw.close();
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
        File file = getFile(filename);

        OutputStream outputStream = new FileOutputStream(file);
        return new BufferedOutputStream(outputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getFile(String filename) throws FileNotFoundException {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        filename.replaceAll("\\\\", "/");

        if (filename.charAt(0) != '/') {
            filename = "/" + filename;
        }

        URL url = getClass().getResource(filename);
        if (url == null) {
            throw new FileNotFoundException("The following file could not be found: \"" + filename + "\"");
        }
        return new File(url.getFile());
    }

}
