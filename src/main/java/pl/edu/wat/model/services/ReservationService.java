package pl.edu.wat.model.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import pl.edu.wat.model.dao.ReservationsDAO;
import pl.edu.wat.model.entities.Reservation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReservationService {
    private static ReservationService instance = new ReservationService();
    private ReservationsDAO reservationsDAO = new ReservationsDAO();
    ExecutorService executorService;
    @Getter private final ObservableList<Reservation> observableList = FXCollections.observableList(new LinkedList<>());

    private ReservationService() {
        executorService = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return  t;
        });
    }

    public static ReservationService getInstance() {
        return instance;
    }

    public void getReservationsList() {
        executorService.execute(() -> {
            List<Reservation> reservations = reservationsDAO.getList();
            observableList.clear();
            observableList.addAll(reservations);
        });
    }

    public Reservation save(Reservation reservation) {
        return reservationsDAO.save(reservation);
    }

}
