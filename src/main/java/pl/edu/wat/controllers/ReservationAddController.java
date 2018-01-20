package pl.edu.wat.controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.ReservationService;
import pl.edu.wat.model.services.RoomService;
import pl.edu.wat.view.ReservationsView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationAddController implements Initializable, ListChangeListener<Room> {

    @FXML
    AnchorPane mainPane;

    @FXML
    AnchorPane leftPane;

    @FXML
    AnchorPane rightPane;

    @FXML
    ComboBox<Room> roomsBox;

    @FXML
    DatePicker dateFromPicker;

    @FXML
    DatePicker dateToPicker;

    private Reservation reservation = new Reservation();
    private ReservationService reservationService = ReservationService.getInstance();
    private RoomService roomService = RoomService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ReservationsView reservationsView =  new ReservationsView();
            rightPane.getChildren().add(reservationsView);
            AnchorPane.setLeftAnchor(reservationsView, 0.0);
            AnchorPane.setRightAnchor(reservationsView, 0.0);
            AnchorPane.setBottomAnchor(reservationsView, 0.0);
            AnchorPane.setTopAnchor(reservationsView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //tymczasowe roaziwązanie, trzeba to ładniej zrobić
        roomsBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room object) {
                StringBuilder sb = new StringBuilder();
                sb.append(object.getId());
                sb.append(". Nr. ");
                sb.append(object.getNumber());
                return sb.toString();
            }

            @Override
            public Room fromString(String string) {
                String id = string.split("\\.")[0];
                return roomService.getById(Long.valueOf(id));
            }
        });

        roomService.getObservableList().addListener(this);
        roomService.getRoomsList();

    }

    public void roomSelected() {
        reservation.setRoom(roomsBox.getValue());
    }

    public void dateFromSelected() {
        reservation.setDateFrom(dateFromPicker.getValue().plusDays(1));
    }

    public void dateToSelected() {
        reservation.setDateTo(dateToPicker.getValue().plusDays(1));
    }

    public void save() {
        reservationService.save(reservation);
    }

    void updateWidth(double newWidth) {
        AnchorPane.setRightAnchor(leftPane, newWidth / 2);
        AnchorPane.setLeftAnchor(rightPane, newWidth / 2);
    }

    void updateHeight(double height) {

    }

    @Override
    public void onChanged(Change<? extends Room> c) {
        roomsBox.setItems((ObservableList<Room>) c.getList());
    }
}
