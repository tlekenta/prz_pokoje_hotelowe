package pl.edu.wat;

import javafx.application.Preloader;
import javafx.stage.Stage;
import pl.edu.wat.model.dao.RoomDAO;
import pl.edu.wat.model.entities.Room;

public class InitDatabase extends Preloader {
    RoomDAO roomDAO = new RoomDAO();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room room = new Room();
        room.setNumber("1");
        room.setNumberOfPersons(2);
        room.setNumberOfBeds(1);
        roomDAO.save(room);

        room = new Room();
        room.setNumber("2");
        room.setNumberOfPersons(3);
        room.setNumberOfBeds(2);
        roomDAO.save(room);


        room = new Room();
        room.setNumber("3");
        room.setNumberOfPersons(4);
        room.setNumberOfBeds(2);
        roomDAO.save(room);

    }
}
