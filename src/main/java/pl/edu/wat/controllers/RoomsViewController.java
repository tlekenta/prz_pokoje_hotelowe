package pl.edu.wat.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.RoomService;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class RoomsViewController implements Initializable {
    @FXML TableView<Room> roomsList;
    @FXML TableColumn<Room, String> numberColumn;
    @FXML TableColumn<Room, Integer> numberOfPersonsColumn;
    @FXML TableColumn<Room, Integer> numberOfBedsColumn;

    private RoomService roomService = RoomService.getInstance();
    private ObservableList<Room> rooms = FXCollections.observableList(new LinkedList<>());

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

        rooms = roomService.getRoomsList();
        rooms.addListener(roomService);

        roomsList.setItems(rooms);
    }


}
