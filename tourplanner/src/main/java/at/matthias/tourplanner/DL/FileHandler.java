package at.matthias.tourplanner.DL;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class);

    private FileHandler() {
        throw new IllegalArgumentException("Utility Class");
    }
    public static void remove(String path) {
        try {
            if (Files.exists(Paths.get(path))) {
                Files.delete(Paths.get(path));
                logger.info("Successfully deleted File");
            } else {
                logger.warn("File does not exist");
            }
        } catch (Exception e) {
            logger.error("Error deleting File" + e);
        }
    }

    public static String read(String path) {
        StringBuilder statements = new StringBuilder();
        if (path.contains("file:")) {
            String[] cut = path.split(":");
            path = cut[1];
        }
        try (FileReader file = new FileReader(path);) {
            logger.info("reading File...");
            int i;
            while ((i = file.read()) != -1) {
                statements.append((char)i);
            }
            return statements.toString();
        } catch (IOException e) {
            logger.error("Error reading File" + e);
        }
        logger.warn("read String is null");
        return null;
    }
}
