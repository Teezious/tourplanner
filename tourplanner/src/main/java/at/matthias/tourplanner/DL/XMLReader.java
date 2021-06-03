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

    public Map<String, String> readDbConfig(String path) {
        logger.info("Reading DB Config");
        HashMap<String, String> dbConfig = new HashMap<>();

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

    public String readXMLElement(String path, String element) {
        if (path.contains("file:")) {
            String[] cut = path.split(":");
            path = cut[1];
        }
        String result = null;
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
        return result;
    }

    public String getPath(String element) {
        String path = this.getClass().getResource("/config/paths.xml").toString();
        String fullPath = null;
        if (path.contains("file:")) {
            String[] cut = path.split(":");
            path = cut[1];
        }
        String result = null;
        File file = new File(path);
        logger.info("reading new XMLelement");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.parse(file);
            result = document.getElementsByTagName(element).item(0).getTextContent();

            fullPath = this.getClass().getResource(result).toString();
            if (fullPath.contains("file:")) {
                String[] cut = fullPath.split(":");
                fullPath = cut[1];
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Error reading XMLELement!" + e);
        }
        return fullPath;
    }
}
