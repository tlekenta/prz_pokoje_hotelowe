package pl.edu.wat.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.AlertEvent;
import pl.edu.wat.events.ChangeViewEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.setUserAgentStylesheet;

public class MenuController implements Initializable {
    @FXML
    MenuBar menuBar;

    @FXML
    Menu viewMenu;

    @FXML
    Menu settingsMenu;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    private ImageView checkedIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("../view/icons/checked.png"));
        checkedIcon = new ImageView(image);
        viewMenu.getItems().get(0).setGraphic(checkedIcon);

        menuBar.addEventHandler(AlertEvent.LANGUAGE_CHANGE, AlertController.getInstance());
        menuBar.addEventHandler(AlertEvent.THEME_CHANGE, AlertController.getInstance());
    }

    @FXML
    void switchView(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;
        int i = 0;
        for(MenuItem menuItem: viewMenu.getItems()) {
            menuItem.setGraphic(null);
            if(menuItem.equals(item)) {
                menuItem.setGraphic(checkedIcon);
                menuBar.fireEvent(ChangeViewEvent.createEvent(i));
            }
            i++;
        }
    }

    @FXML
    void changeTheme(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;

        asr.setTheme(item.getText());
        setUserAgentStylesheet(asr.getTheme());

        menuBar.fireEvent(new AlertEvent(AlertEvent.THEME_CHANGE));
    }

    @FXML
    void changeLanguage(Event e){
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

        menuBar.fireEvent(new AlertEvent(AlertEvent.LANGUAGE_CHANGE));
    }
}
