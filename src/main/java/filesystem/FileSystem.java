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
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new FileSystem());
    }

    private ClassLoader classLoader = getClass().getClassLoader();

    private FileSystem() {}

    @Override
    /** {@inheritDoc} */
    public List<String> readTextFile(String filename) throws FileNotFoundException {
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
    public InputStream readBinaryFile(String filename) throws FileNotFoundException {
        File file = getFile(filename);

        InputStream inputStream = new FileInputStream(
                file);
        return new BufferedInputStream(inputStream);
    }

    @Override
    /** {@inheritDoc} */
    public BufferedImage readImage(String filename) throws FileNotFoundException {
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
    public Clip readSound(String filename) throws FileNotFoundException {
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
    public void writeTextFile(String filename, String content) throws FileNotFoundException {
        File file = getFile(filename);

        try {
            Writer fileWriter = new FileWriter(file);
            Writer bufferedFileWriter = new BufferedWriter(fileWriter);
            bufferedFileWriter.write(content);
            bufferedFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    /** {@inheritDoc} */
    public OutputStream writeBinaryFile(String filename) throws FileNotFoundException {
        File file = getFile(filename);

        OutputStream outputStream = new FileOutputStream(file);
        return new BufferedOutputStream(outputStream);
    }

    @Override
    /** {@inheritDoc} */
    public File getFile(String filename) throws FileNotFoundException {
        assert filename != null;

        URL url = classLoader.getResource(filename);
        if (url == null) {
            throw new FileNotFoundException("The following file could not be found: \"" + filename + "\"");
        }
        return new File(url.getFile());
    }
}
