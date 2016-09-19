package filesystem;

import system.IServiceLocator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for {@link IFileSystem}. Suitable for Windows, MacOS and some Linux distributions.
 */
public final class FileSystem implements IFileSystem {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        FileSystem.serviceLocator = serviceLocator;
        FileSystem.serviceLocator.provide(new FileSystem());
    }

    private ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Prevents instantiation from outside the class.
     */
    private FileSystem() {

    }

    @Override
    /** {@inheritDoc} */
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

    @Override
    /** {@inheritDoc} */
    public InputStream readBinaryFile(final String filename) throws FileNotFoundException {
        File file = getFile(filename);

        InputStream inputStream = new FileInputStream(
                file);
        return new BufferedInputStream(inputStream);
    }

    @Override
    /** {@inheritDoc} */
    public BufferedImage readImage(final String filename) throws FileNotFoundException {
        File file = getFile(filename);

        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /** {@inheritDoc} */
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

    @Override
    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void appendToTextFile(String filename, String content) throws FileNotFoundException {
        try {
            Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public OutputStream writeBinaryFile(final String filename) throws FileNotFoundException {
        File file = getFile(filename);

        OutputStream outputStream = new FileOutputStream(file);
        return new BufferedOutputStream(outputStream);
    }

    @Override
    /** {@inheritDoc} */
    public File getFile(final String filename) throws FileNotFoundException {
        assert filename != null;

        URL url = classLoader.getResource(filename);
        if (url == null) {
            throw new FileNotFoundException("The following file could not be found: \"" + filename + "\"");
        }
        return new File(url.getFile());
    }
}
