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
    public static final int ROOM_ADD_VIEW = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resources);
        loader.setRoot(topMenu);
        loadView(loader, "menu_view");

        loader = new FXMLLoader();
        loader.setResources(resources);
        loader.setRoot(customView);
        loadView(loader, "rooms_view");

        topMenu.addEventHandler(ChangeViewEvent.ROOMS_VIEW, this);
        topMenu.addEventHandler(ChangeViewEvent.RESERVATIONS_ADD_VIEW, this);
        topMenu.addEventHandler(ChangeViewEvent.RESERVATIONS_VIEW, this);
        topMenu.addEventHandler(ChangeViewEvent.ROOM_ADD_VIEW, this);
    }

    private void showView(int index) {
        FXMLLoader loader = new FXMLLoader();
        Locale locale = asr.getLanguage();
        loader.setResources(ResourceBundle.getBundle("i18n.lang", locale));
        loader.setRoot(customView);
        customView.getChildren().clear();
        switch (index) {
            case ROOMS_VIEW:
                loadView(loader, "rooms_view");
                break;
            case RESERVATIONS_VIEW:
                loadView(loader, "reservations_view");
                break;
            case RESERVATIONS_ADD_VIEW:
                loadView(loader, "reservation_add_view");
                break;
            case ROOM_ADD_VIEW:
                loadView(loader, "room_add_view");
                break;
        }
    }

    private void loadView(FXMLLoader loader, String name) {
        try {
            loader.load(getClass().getResource("../view/" + name + ".fxml").openStream());
        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania widoku " + name);
            e.printStackTrace();
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
        } else if(event.getEventType().equals(ChangeViewEvent.ROOM_ADD_VIEW)) {
            showView(ROOM_ADD_VIEW);
        }
    }
}
