package pl.edu.wat.model.services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.wat.model.dao.RoomDAO;
import pl.edu.wat.model.entities.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RoomService {
    private static RoomService instance = new RoomService();
    private RoomDAO roomDAO = new RoomDAO();
    ExecutorService executorService;

    private RoomService(){
        executorService = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    public static RoomService getInstance(){
        return instance;
    }

    public ObservableList<Room> getRoomsList(){
        final ObservableList<Room> observableList = FXCollections.observableList(new LinkedList<>());

        executorService.execute(() -> {
            List<Room> rooms = roomDAO.getList();
            observableList.addAll(rooms);
        });

        return observableList;
    }

    public Future<Room> getById(Long number) {
        return executorService.submit(() -> roomDAO.getById(number));
    }

    public ObservableValue<Room> save(Room room) {
        final SimpleObjectProperty<Room> roomSimpleObjectProperty = new SimpleObjectProperty<>();
        executorService.execute(() -> {
            roomSimpleObjectProperty.set(roomDAO.save(room));
        });
        return roomSimpleObjectProperty;
    }
}
