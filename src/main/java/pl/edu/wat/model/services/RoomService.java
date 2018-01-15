package pl.edu.wat.model.services;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import pl.edu.wat.model.dao.RoomDAO;
import pl.edu.wat.model.entities.Room;

import java.util.LinkedList;
import java.util.List;

public class RoomService implements ListChangeListener<Room>{
    private static RoomService instance;
    private RoomDAO roomDAO = new RoomDAO();

    private RoomService(){

    }

    public static RoomService getInstance(){
        if(instance == null){
            instance = new RoomService();
        }

        return instance;
    }

    public void updateRoomsList(ObservableList<Room> list){

    }

    public ObservableList<Room> getRoomsList(){
        List<Room> rooms = roomDAO.getRoomsList();

        return FXCollections.observableList(rooms);
    }

    @Override
    public void onChanged(Change<? extends Room> c) {
        System.out.println("Zmiana");
    }
}
