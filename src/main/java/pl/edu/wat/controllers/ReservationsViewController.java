package pl.edu.wat.controllers;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.ReservationService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReservationsViewController implements Initializable {

    @FXML
    TableView<Reservation> reservationsList;

    @FXML
    TableColumn<Reservation, LocalDate> dateFromColumn;

    @FXML
    TableColumn<Reservation, LocalDate> dateToColumn;

    @FXML
    TableColumn<Reservation, String> roomNumberColumn;

    private ReservationService reservationService = ReservationService.getInstance();

    private ObservableList<Reservation> reservations = FXCollections.observableList(Collections.emptyList());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateFromColumn.setCellValueFactory(
                new PropertyValueFactory<>("dateFrom")
        );
        dateToColumn.setCellValueFactory(
                new PropertyValueFactory<>("dateTo")
        );
        roomNumberColumn.setCellValueFactory((cellData) ->
                new ReadOnlyStringWrapper(cellData.getValue().getRoom().getNumber())
        );

        reservations = reservationService.getReservationsList();

        reservationsList.setItems(reservations);

    }
}
