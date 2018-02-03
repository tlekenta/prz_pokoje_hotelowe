package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.ChangeViewEvent;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.services.ReservationService;
import pl.edu.wat.web.CurrencyService;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;
import static pl.edu.wat.events.ChangeViewEvent.RESERVATIONS_VIEW;

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
                        cellData.getValue().getTotalPrice();
                DecimalFormat df = new DecimalFormat("#.##");
                return new ReadOnlyStringWrapper(df.format(price) + " " + asr.getCurrnecy());
            }
        );

        reservationService.getReservationsList()
                .addListener((ListChangeListener<Reservation>) c -> Platform.runLater(() -> reservationsTable.setItems((ObservableList<Reservation>) c.getList())));
    }

    public void deleteReservation(MouseEvent event){
        Node node = event.getPickResult().getIntersectedNode();
        if(node instanceof Text || (node instanceof TableCell && ((TableCell) node).getText() != null)){
            Reservation toRemove = reservationsTable.getSelectionModel().getSelectedItem();
            if(showConfrimationDialog()) {
                reservationService.delete(toRemove);
                reservationService.getReservationsList()
                        .addListener((ListChangeListener<Reservation>) c -> Platform.runLater(() -> reservationsTable.setItems((ObservableList<Reservation>) c.getList())));
            }
        }
    }

    private boolean showConfrimationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ResourceBundle bundle = ResourceBundle
                .getBundle("i18n.lang", asr.getLanguage());

        alert.setTitle(bundle.getString("info.title.info"));
        alert.setHeaderText(bundle.getString("info.res_del.header"));
        alert.setContentText(bundle.getString("info.res_del.content"));

        Optional<ButtonType> result = alert.showAndWait();

        return (result.isPresent() && result.get() == ButtonType.OK);
    }
}
