package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.InitDatabase;
import pl.edu.wat.Main;
import pl.edu.wat.view.ReservationsView;
import pl.edu.wat.view.RoomsView;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    MenuBar topMenu;

    @FXML
    MenuItem menuItemRooms;

    @FXML
    MenuItem menuItemReservations;

    @FXML
    RoomsView roomsView;

    @FXML
    ReservationsView reservationsView;

    ImageView checkedIcon;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("../view/icons/checked.png"));
        checkedIcon = new ImageView(image);
        menuItemRooms.setGraphic(checkedIcon);
    }

    public void showRooms() {
        hideAll();
        roomsView.setVisible(true);
        menuItemRooms.setGraphic(checkedIcon);
        menuItemReservations.setGraphic(null);
    }

    public void showReservations() {
        hideAll();
        reservationsView.setVisible(true);
        menuItemReservations.setGraphic(checkedIcon);
        menuItemRooms.setGraphic(null);
    }

    private void hideAll() {
        AnchorPane parent = (AnchorPane) roomsView.getParent();
        for(Node node: parent.getChildren()) {
            node.setVisible(false);
        }
        topMenu.setVisible(true);
    }

    public void changeLanguage(Event e) {
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

    public void changeTheme(Event e) {
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;

        asr.setTheme(item.getText());
        showInfo("theme");
    }

    private void showInfo(String which) {
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
