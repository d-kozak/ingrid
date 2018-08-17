package premun.mps.ingrid.tranformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Utils {
    static String loadFileContent(String filename) throws IOException {
        String path = InlineRulesAlgorithmTest.class.getResource(filename)
                                                    .getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
