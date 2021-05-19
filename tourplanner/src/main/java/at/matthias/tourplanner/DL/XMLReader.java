package at.matthias.tourplanner.DL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLReader {
    public HashMap<String, String> readDbConfig(String path) {
        HashMap<String, String> dbConfig = new HashMap<>();

        String dburl = this.readXMLElement(path, "dburl");
        String dbname = this.readXMLElement(path, "dbname");
        String user = this.readXMLElement(path, "user");
        String pwd = this.readXMLElement(path, "password");

        dbConfig.put("user", user);
        dbConfig.put("password", pwd);
        dbConfig.put("dburl", dburl);
        dbConfig.put("dbname", dbname);

        return dbConfig;
    }

    public String readXMLElement(String path, String element) {
        String result = null;
        if (path.contains("file:")) {
            String[] cut = path.split(":");
            path = cut[1];
        }
        System.out.println(path);
        File file = new File(path);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.parse(file);
            result = document.getElementsByTagName(element).item(0).getTextContent();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
