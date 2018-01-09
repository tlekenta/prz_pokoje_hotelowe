package pl.edu.wat.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.wat.model.Room;

import java.util.LinkedList;
import java.util.List;

public class RoomService {
    private static RoomService instance;

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
        List<Room> rooms = new LinkedList<>();

        Room room = new Room();
        room.setNumber("1");
        room.setNumberOfPersons(2);
        room.setNumberOfBeds(1);
        rooms.add(room);

        room = new Room();
        room.setNumber("2");
        room.setNumberOfPersons(3);
        room.setNumberOfBeds(2);
        rooms.add(room);

        room = new Room();
        room.setNumber("3");
        room.setNumberOfPersons(4);
        room.setNumberOfBeds(2);
        rooms.add(room);

        return FXCollections.observableList(rooms);
    }
}
