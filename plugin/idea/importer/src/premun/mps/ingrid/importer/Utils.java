package premun.mps.ingrid.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Helper utility methods
 *
 * @author dkozak
 */
public class Utils {

    /**
     * Reads the content of a file and returs it as string
     *
     * @param path path of file to read
     * @return content of the file
     */
    static String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
