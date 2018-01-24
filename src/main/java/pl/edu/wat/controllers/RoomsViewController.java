package pl.edu.wat.controllers;

import javafx.application.Platform;
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
import pl.edu.wat.model.entities.Room;
import pl.edu.wat.model.services.RoomService;
import pl.edu.wat.web.CurrencyService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class RoomsViewController implements Initializable {
    @FXML TableView<Room> roomsTable;
    @FXML TableColumn<Room, String> numberColumn;
    @FXML TableColumn<Room, Integer> numberOfPersonsColumn;
    @FXML TableColumn<Room, Integer> numberOfBedsColumn;
    @FXML TableColumn<Room, String> priceColumn;

    private RoomService roomService = RoomService.getInstance();

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priceColumn.setCellValueFactory((cellData) -> {
                    double price = cellData.getValue().getPricePerNight() / CurrencyService.getCurrnecyPrice(asr.getCurrnecy());
                    DecimalFormat df = new DecimalFormat("#.##");
                    return new ReadOnlyStringWrapper(df.format(price) + " " + asr.getCurrnecy());
                }
        );

        roomService.getRoomsList()
                .addListener((ListChangeListener<Room>) c -> Platform.runLater(() -> roomsTable.setItems((ObservableList<Room>) c.getList())));
    }
}
