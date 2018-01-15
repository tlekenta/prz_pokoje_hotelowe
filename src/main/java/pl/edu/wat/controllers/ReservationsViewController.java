package pl.edu.wat.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.services.ReservationService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationsViewController implements Initializable, ListChangeListener<Reservation> {

    @FXML
    TableView<Reservation> reservationsTable;

    @FXML
    TableColumn<Reservation, LocalDate> dateFromColumn;

    @FXML
    TableColumn<Reservation, LocalDate> dateToColumn;

    @FXML
    TableColumn<Reservation, String> roomNumberColumn;

    private ReservationService reservationService = ReservationService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reservationService.getObservableList().addListener(this);

        dateFromColumn.setCellValueFactory(
                new PropertyValueFactory<>("dateFrom")
        );
        dateToColumn.setCellValueFactory(
                new PropertyValueFactory<>("dateTo")
        );
        roomNumberColumn.setCellValueFactory((cellData) ->
                new ReadOnlyStringWrapper(cellData.getValue().getRoom().getNumber())
        );

        reservationService.getReservationsList();
    }

    @Override
    public void onChanged(Change<? extends Reservation> c) {
        reservationsTable.setItems((ObservableList<Reservation>) c.getList());
    }
}
