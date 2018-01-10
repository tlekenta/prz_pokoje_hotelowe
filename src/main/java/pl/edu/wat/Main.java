package pl.edu.wat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUserAgentStylesheet(asr.getTheme());
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        Parent root = loader.load(getClass().getResource("rooms_view.fxml").openStream());

        primaryStage.setTitle("Pokoje hotelowe v1");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            EntityManagerFactory.close();
            ApplicationSettingsReader.saveSettings();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
