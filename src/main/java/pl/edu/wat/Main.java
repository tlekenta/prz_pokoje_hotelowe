package pl.edu.wat;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        Parent root = loader.load(getClass().getResource("view/main_view.fxml").openStream());

        primaryStage.setTitle(ResourceBundle.getBundle("i18n.lang", locale).getString("title"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            EntityManagerFactory.close();
            ApplicationSettingsReader.saveSettings();
        });
    }


    public static void main(String[] args) throws IOException {
        if(args.length == 1) {
            if("dev".equals(args[0])) {
                EntityManagerFactory.enableDevmode();
            }
        }
        LauncherImpl.launchApplication(Main.class, InitDatabase.class, args);
    }
}
