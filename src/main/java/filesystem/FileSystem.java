package filesystem;

import audio.IAudioManager;
import system.IServiceLocator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The default implementation for {@link IFileSystem}. Suitable for Windows, MacOS and some Linux distributions.
 */
public final class FileSystem implements IFileSystem {
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new FileSystem());
    }

    private FileSystem() {}

    @Override
    /** {@inheritDoc} */
    public List<String> readTextFile(String filename) throws FileNotFoundException {
        File file = validateFile(filename);

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
        File file = validateFile(filename);

        InputStream inputStream = new FileInputStream(file);
        InputStream bufferedInputStream = new BufferedInputStream(inputStream);
        return bufferedInputStream;
    }

    @Override
    /** {@inheritDoc} */
    public void writeTextFile(String filename, String content) throws FileNotFoundException {
        File file = validateFile(filename);

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
        File file = validateFile(filename);

        OutputStream outputStream = new FileOutputStream(file);
        OutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        return bufferedOutputStream;
    }

    private File validateFile(String filename) throws FileNotFoundException {
        assert filename != null;
        File file = new File(filename);
        if (!file.isFile()) {
            throw new FileNotFoundException("The following file could not be found: \"" + filename + "\"");
        }
        return file;
    }
}
