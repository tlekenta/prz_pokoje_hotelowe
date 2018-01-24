package pl.edu.wat.controllers;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.services.ReservationService;
import pl.edu.wat.web.CurrencyService;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReservationsViewController implements Initializable {

    @FXML
    TableView<Reservation> reservationsTable;

    @FXML
    TableColumn<Reservation, LocalDate> dateFromColumn;

    @FXML
    TableColumn<Reservation, LocalDate> dateToColumn;

    @FXML
    TableColumn<Reservation, String> roomNumberColumn;

    @FXML
    TableColumn<Reservation, String> priceColumn;

    private ReservationService reservationService = ReservationService.getInstance();

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomNumberColumn.setCellValueFactory((cellData) ->
                new ReadOnlyStringWrapper(cellData.getValue().getRoom().getNumber())
        );
        priceColumn.setCellValueFactory((cellData) -> {
                double price =
                        cellData.getValue().getRoom().getPricePerNight()
                                * DAYS.between(cellData.getValue().getDateFrom(), cellData.getValue().getDateTo())
                                / CurrencyService.getCurrnecyPrice(asr.getCurrnecy());
                DecimalFormat df = new DecimalFormat("#.##");
                return new ReadOnlyStringWrapper(df.format(price) + " " + asr.getCurrnecy());
            }
        );

        reservationService.getReservationsList()
                .addListener((ListChangeListener<Reservation>) c -> reservationsTable.setItems((ObservableList<Reservation>) c.getList()));
    }
}
