package pl.edu.wat.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.controllers.MenuController;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TopMenu extends AnchorPane {

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public TopMenu() throws IOException {
        //TODO zaimplementować Initializable??
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        //loader.setControllerFactory(param -> new MenuController()); //TODO co to robi????
        getChildren().add(loader.load(getClass().getResource("top_menu.fxml").openStream()));
    }
}
