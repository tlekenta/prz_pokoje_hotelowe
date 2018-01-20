package pl.edu.wat.model.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pl.edu.wat.model.dao.RoomDAO;
import pl.edu.wat.model.entities.Room;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomService {
    private static RoomService instance = new RoomService();
    private RoomDAO roomDAO = new RoomDAO();
    ExecutorService executorService;
    @Getter private final ObservableList<Room> observableList = FXCollections.observableList(new LinkedList<>());

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

    public void getRoomsList(){
        executorService.execute(() -> {
            List<Room> rooms = roomDAO.getList();
            observableList.clear();
            observableList.addAll(rooms);
        });

    }

    public Room getById(Long number) {
        return roomDAO.getById(number);
    }
}
