package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.InitDatabase;
import pl.edu.wat.Main;
import pl.edu.wat.view.ReservationAddView;
import pl.edu.wat.view.ReservationsView;
import pl.edu.wat.view.RoomsView;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.application.Application.setUserAgentStylesheet;

public class MainController implements Initializable {

    @FXML
    MenuBar topMenu;

    @FXML
    Menu viewMenu;

    @FXML
    RoomsView roomsView;

    @FXML
    ReservationsView reservationsView;

    @FXML
    ReservationAddView reservationAddView;

    ImageView checkedIcon;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("../view/icons/checked.png"));
        checkedIcon = new ImageView(image);
        viewMenu.getItems().get(0).setGraphic(checkedIcon);
    }

    public void switchView(ActionEvent event) {
        Object o = event.getSource();
        if(o instanceof MenuItem) {
            MenuItem source = (MenuItem) o;
            int index = 0;
            for(MenuItem menuItem: viewMenu.getItems()) {
                menuItem.setGraphic(null);
                if(menuItem.equals(source)) {
                    showView(index);
                    menuItem.setGraphic(checkedIcon);
                }
                index++;
            }
        }
    }

    private void showView(int index) {
        hideAll();
        switch (index) {
            case 0:
                roomsView.setVisible(true);
                break;
            case 1:
                reservationsView.setVisible(true);
                break;
            case 2:
                reservationAddView.setVisible(true);
                break;
        }
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
        setUserAgentStylesheet(asr.getTheme());

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
