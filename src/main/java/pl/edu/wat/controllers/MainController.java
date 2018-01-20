package pl.edu.wat.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.AlertEvent;
import pl.edu.wat.view.CustomView;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.application.Application.setUserAgentStylesheet;

public class MainController implements Initializable {

    @FXML
    MenuBar topMenu;

    @FXML
    Menu viewMenu;

    @FXML
    CustomView customView;

    ImageView checkedIcon;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("../view/icons/checked.png"));
        checkedIcon = new ImageView(image);
        viewMenu.getItems().get(0).setGraphic(checkedIcon);
        topMenu.addEventHandler(AlertEvent.LANGUAGE_CHANGE, AlertController.getInstance());
        topMenu.addEventHandler(AlertEvent.THEME_CHANGE, AlertController.getInstance());
        customView.getChildren().clear();
        try {
            customView.getChildren().add(FXMLLoader.load(getClass().getResource("../view/rooms_view.fxml"), resources));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchView(ActionEvent event) throws IOException {
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

    private void showView(int index) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        customView.getChildren().clear();
        switch (index) {
            case 0:
                customView.getChildren().add(loader.load(getClass().getResource("../view/rooms_view.fxml").openStream()));
                break;
            case 1:
                customView.getChildren().add(loader.load(getClass().getResource("../view/reservations_view.fxml").openStream()));
                break;
            case 2:
                customView.getChildren().add(loader.load(getClass().getResource("../view/reservation_add_view.fxml").openStream()));
                ReservationAddController controller =  loader.getController();
                customView.getScene().widthProperty().addListener(controller);
                controller.updateWidth(customView.getWidth() / 2);
                break;
        }
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
