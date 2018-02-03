package pl.edu.wat;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ApplicationSettingsReader {
    private static Logger logger = Logger.getLogger(ApplicationSettingsReader.class);
    private static Map<String, String> settingsList;
    private static final String PATH_TO_SETTINGS = "src/main/resources/pl/edu/wat/settings/application_settings.xml";

    public ApplicationSettingsReader(){
        try {
            if(settingsList == null)
                settingsList = loadSettings();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error("Błąd podczas wczytywania ustawień aplikacji", e);
        }
    }

    public String getTheme(){
        return settingsList.get("theme");
    }

    public void setTheme(String newTheme){
        settingsList.put("theme", newTheme);
    }

    public Locale getLanguage(){
        String language = settingsList.get("language");

        return new Locale(language.toLowerCase(), language.toUpperCase());
    }

    public synchronized void setLanguage(String language){
        settingsList.put("language", language);
    }

    public String getCurrnecy(){
        return settingsList.get("currency");
    }

    public void setCurrency(String currency){
        settingsList.put("currency", currency);
    }

    private Map loadSettings() throws ParserConfigurationException, IOException, SAXException {
        File xml = new File(PATH_TO_SETTINGS);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document =  dBuilder.parse(xml);

        return documentToMap(document);
    }

    static void saveSettings() {
        try {
            Document document = mapToDocument(settingsList);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.transform(new DOMSource(document),
                    new StreamResult(new FileOutputStream(PATH_TO_SETTINGS)));
        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e){
            logger.error("Błąd podczas zapisywanmia ustawień aplikacji", e);
        }
    }

    private static Document mapToDocument(Map<String, String> settings) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();
        Element root = document.createElement("root");
        Element e;


        for(Map.Entry<String, String> entry: settings.entrySet()){
            e = document.createElement("setup");
            e.setAttribute("key", entry.getKey());
            e.setAttribute("value", entry.getValue());

            root.appendChild(e);
        }

        document.appendChild(root);

        return document;
    }

    private Map<String, String> documentToMap(Document document){
        NodeList nodeList = document.getElementsByTagName("setup");
        Map<String, String> settings = new HashMap<>();

        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                settings.put(element.getAttribute("key"), element.getAttribute("value"));
            }
        }

        return settings;
    }

}
