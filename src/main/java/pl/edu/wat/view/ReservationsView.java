package pl.edu.wat.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.ApplicationSettingsReader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReservationsView extends AnchorPane {

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public ReservationsView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        getChildren().add(loader.load(getClass().getResource("reservations_view.fxml").openStream()));
    }
}