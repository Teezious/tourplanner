package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

public class FileHandler {
  private static final Logger logger = Logger.getLogger(FileHandler.class);

  private FileHandler() {
    throw new IllegalArgumentException("Utility Class");
  }
  // remove file at specified path
  public static void remove(String path) {
    try {
      // only remove when file exists
      if (Files.exists(Paths.get(path))) {
        Files.delete(Paths.get(path));
        logger.info("Successfully deleted File");
      } else {
        logger.warn("File does not exist " + path);
      }
    } catch (Exception e) {
      logger.error("Error deleting File" + e);
    }
  }
  // reads a file
  // specify path to filename
  public static String read(String path) {
    StringBuilder statements = new StringBuilder();
    // cut "file:" extension to read it
    if (path.contains("file:")) {
      String[] cut = path.split(":");
      path = cut[1];
    }
    try (FileReader file = new FileReader(path);) {
      logger.info("reading File...");
      int i;
      while ((i = file.read()) != -1) {
        statements.append((char) i);
      }
      return statements.toString();
    } catch (IOException e) {
      logger.error("Error reading File" + e);
    }
    logger.warn("read String is null");
    return null;
  }
  // export a tour
  // path specifies file where the tour should be saved
  public static void exportTour(String path, TourItem t) {
    logger.info("Export Tour .....");
    try {
      // only export when directory exists
      if (Files.exists(Paths.get(path))) {
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(t); // turn touritem to string
        if (!path.endsWith("/")) {
          logger.info("Does not end with /");
          path = path + "/"; // add slash for filename
        }
        String filepath = path + t.getName() + ".xml";
        File file = new File(filepath);
        FileWriter fileWriter = new FileWriter(file); // write to file
        fileWriter.write(xml);
        fileWriter.close();
      } else {
        logger.warn("Export path does not exist " + path);
      }
    } catch (IOException e) {
      logger.error("Error exporting Tour! " + e);
    }
  }

  // import a tour
  // path specifies file where the tour can be found
  public static void importTour(String path) {
    try {
      // only import when file exists
      if (Files.exists(Paths.get(path))) {
        XmlMapper mapper = new XmlMapper();
        File file = new File(path);
        String xml = inputStreamToString(new FileInputStream(file)); // open file get stream
        if (xml != null) {
          TourItem t = mapper.readValue(xml, TourItem.class); // convert to tour item
          Tourhandler th = new Tourhandler();
          if (t != null) {
            th.add(
                t.getName(), t.getStart(), t.getEnd(), t.getDescription()); // add tour item to db
          }
        }
      } else {
        logger.warn("Import path does not exist");
      }
    } catch (IOException e) {
      logger.error("Error importing file! " + e);
    }
  }

  // converts a inputstream to a string
  public static String inputStreamToString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    String line;
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();
    return sb.toString();
  }
}
