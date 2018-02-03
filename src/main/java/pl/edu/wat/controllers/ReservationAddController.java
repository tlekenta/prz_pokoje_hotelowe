package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.AlertEvent;
import pl.edu.wat.exceptions.IncorrectReservationDatesException;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.FormValidationService;
import pl.edu.wat.model.services.ReservationService;
import pl.edu.wat.model.services.RoomService;
import pl.edu.wat.web.CurrencyService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ReservationAddController implements Initializable {
    private static Logger logger = Logger.getLogger(ReservationAddController.class);

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

    @FXML
    Button button;

    @FXML
    Label labelNights;

    @FXML
    Label labelPrice;

    @FXML
    Label errorsLabel;

    private Reservation reservation = new Reservation();
    private ReservationService reservationService = ReservationService.getInstance();
    private RoomService roomService = RoomService.getInstance();
    private ApplicationSettingsReader asr = new ApplicationSettingsReader();
    private FormValidationService validationService = new FormValidationService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(rightPane);
            loader.setResources(resources);
            loader.load(getClass().getResource("../view/reservations_view.fxml").openStream());
        } catch (IOException e) {
            logger.error("Błąd podczas ładowania widoku dodawania rezerwacji", e);
        }

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
                try {
                    return roomService.getById(Long.valueOf(id)).get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Błąd podczas pobierania informacji o pokoju", e);
                }
                return null;
            }
        });

        roomService.getRoomsList()
                .addListener((ListChangeListener<Room>) c -> Platform.runLater(() -> roomsBox.setItems((ObservableList<Room>) c.getList())));
        mainPane.getScene().widthProperty().addListener((observable, oldValue, newValue) -> this.updateWidth(newValue.doubleValue()));
        mainPane.getScene().heightProperty().addListener((observable, oldValue, newValue) -> this.updateHeight(newValue.doubleValue()));
        this.updateWidth(mainPane.getWidth());
        this.updateHeight(mainPane.getHeight());

        button.addEventHandler(AlertEvent.RESERVATION_ADD_SUCCESS, AlertController.getInstance());

        labelPrice.setText("0 " + asr.getCurrnecy());

        errorsLabel.setWrapText(true);
        errorsLabel.setVisible(false);

    }

    public void roomSelected() {
        reservation.setRoom(roomsBox.getValue());
        updateLabels();
    }

    public void dateFromSelected() {
        if(dateFromPicker.getValue() == null)
            return;

        reservation.setDateFrom(dateFromPicker.getValue().plusDays(1));
        updateLabels();
    }

    public void dateToSelected() {
        if(dateToPicker.getValue() == null)
            return;

        reservation.setDateTo(dateToPicker.getValue().plusDays(1));
        updateLabels();
    }

    public void save() {
        validate().addListener((c, old, newV) -> {
            if(!newV)
                return;
            int daysBetween = dateToPicker.getValue().compareTo(dateFromPicker.getValue());
            reservation.setTotalPrice(daysBetween * reservation.getRoom().getPricePerNight());
            reservationService.save(reservation)
                    .addListener((observable, oldValue, newValue) -> Platform.runLater(() -> button.fireEvent(new AlertEvent(AlertEvent.RESERVATION_ADD_SUCCESS))));
            reservation = new Reservation();
            roomsBox.setValue(null);
            dateFromPicker.setValue(null);
            dateToPicker.setValue(null);
            labelPrice.setText("0 " + asr.getCurrnecy());
            labelNights.setText("0");
        });
    }

    void updateWidth(double newWidth) {
        AnchorPane.setRightAnchor(leftPane, newWidth / 2);
        AnchorPane.setLeftAnchor(rightPane, newWidth / 2);
    }

    void updateHeight(double height) {

    }

    private void updateLabels() {
        ObservableList<FormValidationService.ReservationError> errorList = validationService.validReservationForm(reservation);
        errorList.addListener((ListChangeListener<? super FormValidationService.ReservationError>) c -> {
            if(errorList.contains(FormValidationService.ReservationError.INCORRECT_DATES)) {
                try {
                    throw new IncorrectReservationDatesException();
                } catch (IncorrectReservationDatesException e) {
                    logger.warn("Data końca rezerwacji jest wcześniejsza lub równa dacie początku rezerwacji", e);
                    labelNights.setText("0");
                    labelPrice.setText("0 " + asr.getCurrnecy());
                }
            } else if(!errorList.contains(FormValidationService.ReservationError.DATE_FROM_IS_EMPTY)
                    && !errorList.contains(FormValidationService.ReservationError.DATE_TO_IS_EMPTY)) {
                int daysBetween = dateToPicker.getValue().compareTo(dateFromPicker.getValue());
                labelNights.setText(String.valueOf(daysBetween));
                if(roomsBox.getValue() != null) {
                    double price = daysBetween * roomsBox.getValue().getPricePerNight() / CurrencyService.getCurrnecyPrice(asr.getCurrnecy());
                    DecimalFormat df = new DecimalFormat("#.## ");
                    labelPrice.setText(df.format(price) + asr.getCurrnecy());
                }
            }
        });
    }

    private ObservableBooleanValue validate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.lang", asr.getLanguage());
        errorsLabel.setText("");
        StringBuilder sb = new StringBuilder("");
        List<FormValidationService.ReservationError> errorList = new LinkedList<>();
        ObservableBooleanValue isValid = new SimpleBooleanProperty(errorList.isEmpty());
        validationService.validReservationForm(reservation).addListener((ListChangeListener<? super FormValidationService.ReservationError>) c -> {
             errorList.addAll(c.getList());
            if(errorList.contains(FormValidationService.ReservationError.ROOM_IS_EMPTY)) {
                sb.append(resourceBundle.getString("reservations.add.error1")).append("\n");
            }

            if(errorList.contains(FormValidationService.ReservationError.DATE_TO_IS_EMPTY)) {
                sb.append(resourceBundle.getString("reservations.add.error2")).append("\n");
            }

            if(errorList.contains(FormValidationService.ReservationError.DATE_FROM_IS_EMPTY)) {
                sb.append(resourceBundle.getString("reservations.add.error3")).append("\n");
            }

            if(errorList.contains(FormValidationService.ReservationError.INCORRECT_DATES)) {
                sb.append(resourceBundle.getString("reservations.add.error4")).append("\n");
            }

            if(errorList.contains(FormValidationService.ReservationError.ROOM_IS_NOT_AVAILABLE)) {
                sb.append(resourceBundle.getString("reservations.add.error5")).append("\n");
            }

            Platform.runLater(() -> {
                errorsLabel.setText(sb.toString());
                errorsLabel.setVisible(!errorList.isEmpty());
            });
        });

        return isValid;
    }
}
