package pl.edu.wat.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.controllers.MenuController;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TopMenu extends AnchorPane {

    private MenuController controller;
    ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public TopMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        loader.setControllerFactory(param -> controller = new MenuController());
        getChildren().add(loader.load(getClass().getResource("top_menu.fxml").openStream()));
    }
}
