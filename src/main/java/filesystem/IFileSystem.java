package filesystem;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * This interface defines a file system. All file-access should be led by an implementation of this interface.
 */
public interface IFileSystem {

    /**
     * Reads a text file and returns the contents as a list of Strings.
     *
     * @param filename The full file-path of the text file
     * @return A List of Strings containing the lines of the text file
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    List<String> readTextFile(String filename) throws FileNotFoundException;

    /**
     * Reads a binary file and returns an InputStream that can be used to read through the file.
     *
     * @param filename The full file-path of the binary file
     * @return An InputStream providing access to the binary data
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    InputStream readBinaryFile(String filename) throws FileNotFoundException;

    /**
     * Reads and returns an image.
     *
     * @param filename The full file-path of the image
     * @return An Image embedding the image on the disk
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    BufferedImage readImage(String filename) throws FileNotFoundException;

    /**
     * Reads and returns a sound.
     *
     * @param filename The full file-path of the sound
     * @return A Clip embedding the sound on the disk
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    Clip readSound(String filename) throws FileNotFoundException;

    /**
     * Writes {@code content} to the text-file given by the filepath {@code filename}. The path to the file must exist.
     *
     * @param filename The full path to the file
     * @param content  The text to write to the file
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    void writeTextFile(String filename, String content) throws FileNotFoundException;

    /**
     * Writes {@code content} to the end of the log file.
     * @param filename The name of the log file.
     * @param content The text to write to the file.
     * @throws FileNotFoundException Thrown when the log file could not be found.
     */
    void log(String filename, String content) throws FileNotFoundException;

    /**
     * Writes {@code exception} to the end of the log file.
     * @param filename The name of the log file.
     * @param exception The exception to write to the file.
     * @throws FileNotFoundException Thrown when the log file could not be found.
     */
    void log(String filename, Exception exception) throws FileNotFoundException;

    /**
     * Returns an {@link OutputStream} that can be used to write binary data to the binary file. The path to the file must exist.
     * <br />
     * <br />
     * <b><font color="red">Warning:</font> The file MUST be closed explicitly to prevent resource leaks</b>
     *
     * @param filename The full path to the file
     * @return An OutputStream that can be used to write binary data to the text file
     * @throws FileNotFoundException Thrown when the file could not be found
     */
    OutputStream writeBinaryFile(String filename) throws FileNotFoundException;

    /**
     * Loads the file specified by {@code filename} and checks if it is a valid file.
     *
     * @param filename The name of the file
     * @return A {@link File} class embedding the specified file
     * @throws FileNotFoundException Thrown when the file specified was not found
     */
    File getFile(String filename) throws FileNotFoundException;

}
