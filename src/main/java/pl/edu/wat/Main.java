package pl.edu.wat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Locale locale = new Locale("pl", "PL");
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        Parent root = loader.load(getClass().getResource("sample.fxml").openStream());

        primaryStage.setTitle("Pokoje hotelowe v1");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->
            EntityManagerFactory.close()
        );
    }


    public static void main(String[] args) {
        launch(args);
    }
}
