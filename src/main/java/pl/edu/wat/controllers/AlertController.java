package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.Getter;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.InitDatabase;
import pl.edu.wat.Main;
import pl.edu.wat.events.AlertEvent;

import java.util.Optional;
import java.util.ResourceBundle;

public class AlertController implements EventHandler<AlertEvent> {
    @Getter private static final AlertController instance = new AlertController();
    private ApplicationSettingsReader asr = new ApplicationSettingsReader();
    private enum AlertType {THEME, LANG, CURRENCY, RES_ADD_SUCCESS}


    private AlertController() {
    }

    @Override
    public void handle(AlertEvent event) {
        Object object = event.getSource();
        Control source;
        if(object instanceof MenuBar) {
            source = (MenuBar) object;
        } else if(object instanceof Button) {
            source = (Button) object;
        } else {
            return;
        }

        EventType<? extends Event> eventType = event.getEventType();
        if(eventType.equals(AlertEvent.THEME_CHANGE)) {
            showInformationDialog(AlertType.THEME);
        } else if(eventType.equals(AlertEvent.LANGUAGE_CHANGE)) {
            showRestartConfirmationDialog(AlertType.LANG, source.getScene().getWindow());
        } else if(eventType.equals(AlertEvent.CURRENCY_CHANGE)) {
            showRestartConfirmationDialog(AlertType.CURRENCY, source.getScene().getWindow());
        } else if(eventType.equals(AlertEvent.RESERVATION_ADD_SUCCESS)) {
            showInformationDialog(AlertType.RES_ADD_SUCCESS);
        }

    }

    private void showInformationDialog(AlertType type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ResourceBundle bundle = ResourceBundle
                .getBundle("i18n.lang", asr.getLanguage());

        alert.setTitle(bundle.getString("info.title.info"));
        alert.setHeaderText(bundle.getString("info." + type.name().toLowerCase() + ".header"));
        alert.setContentText(bundle.getString("info." + type.name().toLowerCase() + ".content"));

        alert.show();
    }

    private void showRestartConfirmationDialog(AlertType type, Window stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle
                .getBundle("i18n.lang", asr.getLanguage());

        alert.setTitle(bundle.getString("info.title.info"));
        alert.setHeaderText(bundle.getString("info." + type.name().toLowerCase() + ".header"));
        alert.setContentText(bundle.getString("info." + type.name().toLowerCase() + ".content"));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            stage.fireEvent(
                    new WindowEvent(
                            stage,
                            WindowEvent.WINDOW_CLOSE_REQUEST
                    )
            );
            Platform.runLater(() -> {
                try {
                    new InitDatabase().initDatabase();
                    new Main().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
