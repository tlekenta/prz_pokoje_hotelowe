package pl.edu.wat;

import javafx.application.Preloader;
import javafx.stage.Stage;
import pl.edu.wat.model.dao.ReservationsDAO;
import pl.edu.wat.model.dao.RoomDAO;
import pl.edu.wat.model.entities.Reservation;
import pl.edu.wat.model.entities.Room;

import java.time.LocalDate;

public class InitDatabase extends Preloader {
    RoomDAO roomDAO = new RoomDAO();
    ReservationsDAO reservationsDAO = new ReservationsDAO();

    @Override
    public void start(Stage primaryStage) throws Exception {
        initDatabase();
    }

    public void initDatabase() {
        Room room1 = new Room();
        room1.setNumber("1");
        room1.setNumberOfPersons(2);
        room1.setNumberOfBeds(1);
        roomDAO.save(room1);

        Room room2 = new Room();
        room2.setNumber("2");
        room2.setNumberOfPersons(3);
        room2.setNumberOfBeds(2);
        roomDAO.save(room2);


        Room room3 = new Room();
        room3.setNumber("3");
        room3.setNumberOfPersons(4);
        room3.setNumberOfBeds(2);
        roomDAO.save(room3);

        Reservation reservation1 = new Reservation();
        reservation1.setDateFrom(LocalDate.of(2018, 2, 1));
        reservation1.setDateTo(LocalDate.of(2018, 2, 8));
        reservation1.setRoom(room1);
        reservationsDAO.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setDateFrom(LocalDate.of(2018, 1, 16));
        reservation2.setDateTo(LocalDate.of(2018, 1, 28));
        reservation2.setRoom(room2);
        reservationsDAO.save(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setDateFrom(LocalDate.of(2018, 1, 14));
        reservation3.setDateTo(LocalDate.of(2018, 1, 21));
        reservation3.setRoom(room3);
        reservationsDAO.save(reservation3);
    }
}
