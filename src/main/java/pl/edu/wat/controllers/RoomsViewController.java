package pl.edu.wat.controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.RoomService;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsViewController implements Initializable {
    @FXML TableView<Room> roomsTable;
    @FXML TableColumn<Room, String> numberColumn;
    @FXML TableColumn<Room, Integer> numberOfPersonsColumn;
    @FXML TableColumn<Room, Integer> numberOfBedsColumn;
    @FXML TableColumn<Room, Double> priceColumn;

    private RoomService roomService = RoomService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberColumn.setCellValueFactory(
                new PropertyValueFactory<>("number")
        );
        numberOfPersonsColumn.setCellValueFactory(
                new PropertyValueFactory<>("numberOfPersons")
        );
        numberOfBedsColumn.setCellValueFactory(
                new PropertyValueFactory<>("numberOfBeds")
        );
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("pricePerNight")
        );

        roomService.getRoomsList()
                .addListener((ListChangeListener<Room>) c -> roomsTable.setItems((ObservableList<Room>) c.getList()));
    }
}
