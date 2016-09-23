package filesystem;

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
     * A classloader in order to load in resources.
     */
    private ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Prevents instantiation from outside the class.
     */
    private FileSystem() {

    }

    /**
     * Register the FileSystem into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        FileSystem.sL = sL;
        FileSystem.sL.provide(new FileSystem());
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
    public void appendToTextFile(final String filename, final String content) throws FileNotFoundException {
        try (final FileWriter fw = new FileWriter(filename, true);
             final BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content + "\n");
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
