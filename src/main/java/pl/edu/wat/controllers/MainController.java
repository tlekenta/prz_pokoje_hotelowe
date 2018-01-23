package pl.edu.wat.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.ChangeViewEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventHandler<ChangeViewEvent> {

    @FXML
    MenuBar topMenu;

    @FXML
    AnchorPane customView;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public static final int ROOMS_VIEW = 0;
    public static final int RESERVATIONS_VIEW = 1;
    public static final int RESERVATIONS_ADD_VIEW = 2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resources);
        loader.setRoot(topMenu);
        try {
            loader.load(getClass().getResource("../view/menu_view.fxml").openStream());
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania widoku menu");
            e.printStackTrace();
        }

        topMenu.addEventHandler(ChangeViewEvent.ROOMS_VIEW, this);
        topMenu.addEventHandler(ChangeViewEvent.RESERVATIONS_ADD_VIEW, this);
        topMenu.addEventHandler(ChangeViewEvent.RESERVATIONS_VIEW, this);


        customView.getChildren().clear();
        try {
            customView.getChildren().add(FXMLLoader.load(getClass().getResource("../view/rooms_view.fxml"), resources));
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania widoku pokoi");
            e.printStackTrace();
        }
    }

    private void showView(int index) {
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        customView.getChildren().clear();
        switch (index) {
            case ROOMS_VIEW:
                try {
                    customView.getChildren().add(loader.load(getClass().getResource("../view/rooms_view.fxml").openStream()));
                } catch (IOException e) {
                    System.out.println("Błąd podczas ładowania widoku pokoi");
                    e.printStackTrace();
                }
                break;
            case RESERVATIONS_VIEW:
                try {
                    customView.getChildren().add(loader.load(getClass().getResource("../view/reservations_view.fxml").openStream()));
                } catch (IOException e) {
                    System.out.println("Błąd podczas ładowania widoku rezerwacji");
                    e.printStackTrace();
                }
                break;
            case RESERVATIONS_ADD_VIEW:
                try {
                    customView.getChildren().add(loader.load(getClass().getResource("../view/reservation_add_view.fxml").openStream()));
                } catch (IOException e) {
                    System.out.println("Błąd podczas ładowania widoku dodawania rezerwacji");
                    e.printStackTrace();
                }
                ReservationAddController controller =  loader.getController();
                customView.getScene().widthProperty().addListener((observable, oldValue, newValue) -> controller.updateWidth(newValue.doubleValue()));
                customView.getScene().heightProperty().addListener((observable, oldValue, newValue) -> controller.updateHeight(newValue.doubleValue()));
                controller.updateWidth(customView.getWidth());
                controller.updateHeight(customView.getHeight());
                break;
        }
    }

    @Override
    public void handle(ChangeViewEvent event) {
        if(event.getEventType().equals(ChangeViewEvent.ROOMS_VIEW)) {
            showView(ROOMS_VIEW);
        } else if(event.getEventType().equals(ChangeViewEvent.RESERVATIONS_VIEW)) {
            showView(RESERVATIONS_VIEW);
        } else if(event.getEventType().equals(ChangeViewEvent.RESERVATIONS_ADD_VIEW)) {
            showView(RESERVATIONS_ADD_VIEW);
        }
    }
}
