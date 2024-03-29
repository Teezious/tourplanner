package at.matthias.tourplanner.DL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLReader {
  private static final Logger logger = Logger.getLogger(XMLReader.class);

  // path specifies path to db config
  public Map<String, String> readDbConfig(String path) {
    logger.info("Reading DB Config");
    HashMap<String, String> dbConfig = new HashMap<>();

    // read the db config elements
    String dburl = readXMLElement(path, "dburl");
    String dbname = readXMLElement(path, "dbname");
    String user = readXMLElement(path, "user");
    String pwd = readXMLElement(path, "password");

    dbConfig.put("user", user);
    dbConfig.put("password", pwd);
    dbConfig.put("dburl", dburl);
    dbConfig.put("dbname", dbname);

    return dbConfig;
  }
  // path specifies xml file to be read
  // element specifiex which element should be read
  public String readXMLElement(String path, String element) {
    String result = null;
    if (path != null && element != null) {
      if (path.contains("file:")) {
        String[] cut = path.split(":");
        path = cut[1];
      }

      File file = new File(path);
      logger.info("reading new XMLelement");
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      try {
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(file);
        result = document.getElementsByTagName(element).item(0).getTextContent();

      } catch (ParserConfigurationException | SAXException | IOException e) {
        logger.error("Error reading XMLELement!" + e);
      }
    }
    return result;
  }

  // element specifies which path should be gotten
  // function returns absolute path
  public String getFullPath(String element) {
    String fullPath = "";
    if (element != null) {
      String path = this.getClass().getResource("/config/paths.xml").toString();
      // remove "file:"
      if (path.contains("file:")) {
        String[] cut = path.split(":");
        path = cut[1];
      }
      String result = "";
      File file = new File(path);
      logger.info("reading new XMLelement");
      if (file != null) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
          DocumentBuilder db = factory.newDocumentBuilder();
          Document document = db.parse(file);
          result = document.getElementsByTagName(element).item(0).getTextContent();
          if (result != null) {
            fullPath = this.getClass().getResource(result).toString();
          }
          // remove "file:"
          if (fullPath.contains("file:")) {
            String[] cut = fullPath.split(":");
            fullPath = cut[1];
          }

        } catch (ParserConfigurationException | SAXException | IOException e) {
          logger.error("Error reading XMLELement!" + e);
        }
      }
    }

    return fullPath;
  }
  // element specifies which path should be gotten
  // function returns relative path
  public String getPath(String element) {
    String result = "";
    // remove "file:"
    if (element != null) {
      String path = this.getClass().getResource("/config/paths.xml").toString();
      if (path.contains("file:")) {
        String[] cut = path.split(":");
        path = cut[1];
      }

      File file = new File(path);
      logger.info("reading new XMLelement");
      if (file != null) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
          DocumentBuilder db = factory.newDocumentBuilder();
          Document document = db.parse(file);
          result = document.getElementsByTagName(element).item(0).getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException e) {
          logger.error("Error reading XMLELement!" + e);
        }
      }
    }

    return result;
  }
}
