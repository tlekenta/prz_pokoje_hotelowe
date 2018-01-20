package pl.edu.wat.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.AlertEvent;
import pl.edu.wat.view.ReservationAddView;
import pl.edu.wat.view.ReservationsView;
import pl.edu.wat.view.RoomsView;

import java.net.URL;
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
        topMenu.addEventHandler(AlertEvent.LANGUAGE_CHANGE, AlertController.getInstance());
        topMenu.addEventHandler(AlertEvent.THEME_CHANGE, AlertController.getInstance());
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
        topMenu.fireEvent(new AlertEvent(AlertEvent.LANGUAGE_CHANGE));
    }

    public void changeTheme(Event e) {
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;

        asr.setTheme(item.getText());
        setUserAgentStylesheet(asr.getTheme());

        topMenu.fireEvent(new AlertEvent(AlertEvent.THEME_CHANGE));
    }

}
