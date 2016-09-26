package filesystem;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonObject;
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
     * A classloader in order to load in resources.
     */
    private ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Prevents instantiation from outside the class.
     */
    private FileSystem() {

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
    public void appendToTextFile(final Writer writer, final String content) {
        try {
            writer.write(content + "\n");
            writer.flush();
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

    /** {@inheritDoc} */
    @Override
    public Object parseJson(String filename, Class<?> jsonClass) throws FileNotFoundException {
        assert jsonClass.isAnnotationPresent(JsonObject.class);
        StringBuilder sb = new StringBuilder();
        for (String string : readTextFile(filename)) {
            sb.append(string);
        }
        String json = sb.toString();

        Object result = null;
        try {
            result = LoganSquare.parse(json, jsonClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Object parseJsonList(String filename, Class<?> jsonClass) throws FileNotFoundException {
        assert jsonClass.isAnnotationPresent(JsonObject.class);
        StringBuilder sb = new StringBuilder();
        for (String string : readTextFile(filename)) {
            sb.append(string);
        }
        String json = sb.toString();

        Object result = null;
        try {
            result = LoganSquare.parseList(json, jsonClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Object parseJsonMap(String filename, Class<?> jsonClass) throws FileNotFoundException {
//        assert jsonClass.isAnnotationPresent(JsonObject.class);
        StringBuilder sb = new StringBuilder();
        for (String string : readTextFile(filename)) {
            sb.append(string);
        }
        String json = sb.toString();

        Object result = null;
        try {
            result = LoganSquare.parseMap(json, jsonClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
