package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import pl.edu.wat.ApplicationSettingsReader;
import pl.edu.wat.events.AlertEvent;
import pl.edu.wat.exceptions.IncorrectReservationDatesException;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.ReservationService;
import pl.edu.wat.model.services.RoomService;
import pl.edu.wat.web.CurrencyService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ReservationAddController implements Initializable {

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

    private Reservation reservation = new Reservation();
    private ReservationService reservationService = ReservationService.getInstance();
    private RoomService roomService = RoomService.getInstance();
    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(rightPane);
            loader.setResources(resources);
            loader.load(getClass().getResource("../view/reservations_view.fxml").openStream());
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
                return roomService.getById(Long.valueOf(id)).getValue();
            }
        });

        roomService.getRoomsList()
                .addListener((ListChangeListener<Room>) c -> Platform.runLater(() -> roomsBox.setItems((ObservableList<Room>) c.getList())));
        mainPane.getScene().widthProperty().addListener((observable, oldValue, newValue) -> this.updateWidth(newValue.doubleValue()));
        mainPane.getScene().heightProperty().addListener((observable, oldValue, newValue) -> this.updateHeight(newValue.doubleValue()));
        this.updateWidth(mainPane.getWidth());
        this.updateHeight(mainPane.getHeight());

        button.addEventHandler(AlertEvent.RESERVATION_ADD_SUCCESS, AlertController.getInstance());
        button.setDisable(true);

        labelPrice.setText("0 " + asr.getCurrnecy());

    }

    public void roomSelected() {
        reservation.setRoom(roomsBox.getValue());
        if(dateToPicker.getValue() != null && dateFromPicker.getValue() != null) {
            updateLabels();
        }
        isValid();
    }

    public void dateFromSelected() {
        if(dateFromPicker.getValue() == null)
            return;

        reservation.setDateFrom(dateFromPicker.getValue().plusDays(1));

        if(dateToPicker.getValue() != null) {
            updateLabels();
        }
        isValid();
    }

    public void dateToSelected() {
        if(dateToPicker.getValue() == null)
            return;

        reservation.setDateTo(dateToPicker.getValue().plusDays(1));

        if(dateFromPicker.getValue() != null) {
            updateLabels();
        }
        isValid();
    }

    public void save() {
        if(!isValid())
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
    }

    void updateWidth(double newWidth) {
        AnchorPane.setRightAnchor(leftPane, newWidth / 2);
        AnchorPane.setLeftAnchor(rightPane, newWidth / 2);
    }

    void updateHeight(double height) {

    }

    private void updateLabels() {
        int daysBetween = dateToPicker.getValue().compareTo(dateFromPicker.getValue());
        if(daysBetween <= 0) {
            try {
                throw new IncorrectReservationDatesException();
            } catch (IncorrectReservationDatesException e) {
                e.printStackTrace();
                System.out.println("Data końca rezerwacji jest wcześniejsza lub równa dacie początku rezerwacji");
                labelNights.setText("0");
                labelPrice.setText("0 " + asr.getCurrnecy());
            }
        } else {
            labelNights.setText(String.valueOf(daysBetween));
            if(roomsBox.getValue() != null) {
                double price = daysBetween * roomsBox.getValue().getPricePerNight() / CurrencyService.getCurrnecyPrice(asr.getCurrnecy());
                DecimalFormat df = new DecimalFormat("#.## ");
                labelPrice.setText(df.format(price) + asr.getCurrnecy());
            }
        }
    }

    private boolean isValid() {
        boolean isValid = false;

        if(roomsBox.getValue() == null) {
            isValid = false;
        } else if(dateFromPicker.getValue() != null && dateToPicker.getValue() != null) {
            isValid = dateToPicker.getValue().compareTo(dateFromPicker.getValue()) > 0;
        }

        button.setDisable(!isValid);
        return isValid;
    }
}
