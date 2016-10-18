package filesystem;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * This interface defines a file system. All file-access should be led by an implementation of this interface.
 */
public interface IFileSystem {

    /**
     * Reads a resource text file and returns the contents as a list of Strings.
     *
     * @param filename The full file-path of the text file.
     * @return A List of Strings containing the lines of the text file.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    List<String> readResourceFile(final String filename) throws FileNotFoundException;

    /**
     * Reads a project text file and returns the contents as a list of Strings.
     *
     * @param filename The full file-path of the text file.
     * @return A List of Strings containing the lines of the text file.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    List<String> readProjectFile(final String filename) throws FileNotFoundException;

    /**
     * Reads a binary file and returns an InputStream that can be used to read through the file.
     *
     * @param filename The full file-path of the binary file.
     * @return An InputStream providing access to the binary data.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    InputStream readBinaryFile(final String filename) throws FileNotFoundException;

    /**
     * Reads and returns an image.
     *
     * @param filename The full file-path of the image.
     * @return An Image embedding the image on the disk.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    BufferedImage readImage(final String filename) throws FileNotFoundException;

    /**
     * Reads and returns a sound.
     *
     * @param filename The full file-path of the sound.
     * @return A Clip embedding the sound on the disk.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    Clip readSound(final String filename) throws FileNotFoundException;

    /**
     * Writes {@code content} to the resource text-file given by the filepath {@code filename}. The path to the file must
     * exist.
     *
     * @param filename The full path to the file.
     * @param content  The text to write to the file.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    void writeResourceFile(final String filename, final String content) throws FileNotFoundException;

    /**
     * Writes {@code content} to the project text-file given by the filepath {@code filename}. The path to the file
     * must exist.
     *
     * @param filename The full path to the file.
     * @param content  The text to write to the file.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    void writeProjectFile(final String filename, final String content) throws FileNotFoundException;

    /**
     * Deletes the file specified by {@code filename} from the disk.
     *
     * @param filename The file you want to delete.
     */
    void deleteFile(final String filename);

    /**
     * Removes the content of the file specified by {@code filename} but does not delete the file itself.
     *
     * @param filename The file you want to clear.
     */
    void clearFile(final String filename);

    /**
     * Writes {@code content} to the log file.
     *
     * @param content The text to write to the file.
     */
    void log(final String content);

    /**
     * Returns an {@link OutputStream} that can be used to write binary data to the binary file.
     * The path to the file must exist.
     * <p>
     * <b><font color="red">Warning:</font> The file MUST be closed explicitly to prevent resource leaks</b>
     *
     * @param filename The full path to the file.
     * @return An OutputStream that can be used to write binary data to the text file.
     * @throws FileNotFoundException Thrown when the file could not be found.
     */
    OutputStream writeBinaryFile(final String filename) throws FileNotFoundException;

    /**
     * Loads the resource file specified by {@code filename} and checks if it is a valid file.
     *
     * @param filename The name of the file.
     * @return A {@link File} class embedding the specified file.
     * @throws FileNotFoundException Thrown when the file specified was not found.
     */
    File getResourceFile(final String filename) throws FileNotFoundException;

    /**
     * Loads the project file specified by {@code filename} and checks if it is a valid file.
     *
     * @param filename The name of the file.
     * @return A {@link File} class embedding the specified file.
     * @throws IOException If the file was not found and could not be created.
     */
    File getProjectFile(final String filename) throws IOException;

    /**
     * Parse a JSON file consisting of a single Json item.
     *
     * @param filename  The filepath to the Json file.
     * @param type The type of the resulting Json object.
     * @return An {@link Object} that must be up-casted to the desired Json class.
     * @throws FileNotFoundException Thrown when the Json file was not found.
     */
    Object parseJson(final String filename, final Type type) throws FileNotFoundException;

    /**
     * Serializes the image specified to a Json string.
     *
     * @param image The image that should be serialized
     * @return A string containing the serialized image
     */
    String serializeJson(final IToJsonSerializable image);

    /**
     * Gets a custom font from a *.ttf file.
     *
     * @param name The name of the ttf font file
     * @return The font loaded from the disk
     */
    Font getFont(final String name);
}
