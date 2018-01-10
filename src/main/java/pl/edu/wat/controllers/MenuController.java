package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import pl.edu.wat.ApplicationSettingsReader;

import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController {

    @FXML
    MenuBar topMenu;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public void changeLanguage(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;
        switch (item.getText()){
            case "Polski":
                asr.setLanguage("pl");
                break;
            case "English":
                asr.setLanguage("en");
                break;
            default:
                break;
        }
        showInfo("lang");
    }

    public void changeTheme(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;

        asr.setTheme(item.getText());
        showInfo("theme");
    }

    private void showInfo(String which){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle
                .getBundle("i18n.lang", asr.getLanguage());

        alert.setTitle(bundle.getString("info.title.info"));
        alert.setHeaderText(bundle.getString("info." + which + ".header"));
        alert.setContentText(bundle.getString("info." + which + ".content"));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Stage stage = (Stage)topMenu.getScene().getWindow();
            stage.fireEvent(
                new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
                )
            );
        }
    }

}
