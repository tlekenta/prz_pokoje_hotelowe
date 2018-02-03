package pl.edu.wat.model.services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.wat.model.dao.ReservationsDAO;
import pl.edu.wat.model.entities.Reservation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReservationService {
    private static ReservationService instance = new ReservationService();
    private ReservationsDAO reservationsDAO = new ReservationsDAO();
    ExecutorService executorService;

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

    public ObservableList<Reservation> getReservationsList() {
        final ObservableList<Reservation> observableList = FXCollections.observableList(new LinkedList<>());

        executorService.execute(() -> {
            List<Reservation> reservations = reservationsDAO.getList();
            observableList.clear();
            observableList.addAll(reservations);
        });

        return observableList;
    }

    public ObservableValue<Reservation> save(Reservation reservation) {
        final SimpleObjectProperty<Reservation> reservationObservableValue = new SimpleObjectProperty<>();
        executorService.execute(() ->
            reservationObservableValue.set(reservationsDAO.save(reservation))
        );

        return reservationObservableValue;
    }

    public void delete(Reservation toRemove) {
        executorService.execute(() -> {
            reservationsDAO.delete(toRemove);
        });
    }

}
