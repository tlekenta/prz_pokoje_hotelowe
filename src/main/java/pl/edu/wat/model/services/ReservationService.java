package pl.edu.wat.model.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.wat.model.dao.ReservationsDAO;
import pl.edu.wat.model.entities.Reservation;

import java.util.List;

public class ReservationService {
    private static ReservationService instance = new ReservationService();
    private ReservationsDAO reservationsDAO = new ReservationsDAO();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        return instance;
    }

    public ObservableList<Reservation> getReservationsList() {
        List<Reservation> reservations = reservationsDAO.getList();

        return FXCollections.observableList(reservations);
    }

}
