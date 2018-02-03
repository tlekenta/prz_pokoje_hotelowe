package pl.edu.wat.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.RoomService;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomAddController implements Initializable {

    @FXML
    TextField roomNumberField;

    @FXML
    TextField numberOfPersonsField;

    @FXML
    TextField numberOfBedsField;

    @FXML
    TextField pricePerNightField;

    @FXML
    Button button;

    private List<TextField> textFields;

    private List<Room> existingRooms;

    private RoomService roomService = RoomService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = new LinkedList<>();
        textFields.add(roomNumberField);
        textFields.add(numberOfPersonsField);
        textFields.add(numberOfBedsField);
        textFields.add(pricePerNightField);
        for(TextField textField: textFields) {
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue) validateField(textField);
            });
        }

        roomService.getRoomsList()
                .addListener((ListChangeListener<? super Room>) c -> existingRooms = new ArrayList<>(c.getList()));
    }

    @FXML
    void save(Event e) {
        boolean isFormValid = true;
        for(TextField textField: textFields) {
            isFormValid &= validateField(textField);
        }
        if(!isFormValid)
            return;

        Room room = new Room();
        room.setNumber(roomNumberField.getText());
        room.setNumberOfPersons(Integer.valueOf(numberOfPersonsField.getText()));
        room.setNumberOfBeds(Integer.valueOf(numberOfBedsField.getText()));
        room.setPricePerNight(Double.valueOf(pricePerNightField.getText()));
        roomService.save(room);
    }

    @FXML
    void isValid(Event event) {
        Platform.runLater(() -> {

            Object object = event.getSource();
            TextField fieldToValidate;
            if(object instanceof TextField) {
                fieldToValidate = (TextField) object;
            } else {
                return;
            }

            validateField(fieldToValidate);

        });
    }

    private boolean validateField(TextField fieldToValidate) {
        boolean isValid = true;

        if(fieldToValidate.getText().isEmpty()){
            isValid = false;
        }

        if(fieldToValidate == roomNumberField) {
            isValid = fieldToValidate.getText().matches("\\d+[A-Z]*");
            for(Room room: existingRooms) {
                if(room.getNumber().equals(fieldToValidate.getText())) {
                    isValid = false;
                    break;
                }
            }
        } else if(fieldToValidate == numberOfPersonsField || fieldToValidate == numberOfBedsField) {
            isValid = fieldToValidate.getText().matches("\\d+");
        } else if(fieldToValidate == pricePerNightField) {
            isValid = fieldToValidate.getText().matches("\\d+(\\.\\d{2})?");
        }

        if (isValid) {
            fieldToValidate.setStyle("-fx-border-color: green");
        } else {
            fieldToValidate.setStyle("-fx-border-color: red");
        }

        return isValid;
    }
}
