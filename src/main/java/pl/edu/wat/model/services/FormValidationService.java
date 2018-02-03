package pl.edu.wat.model.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import pl.edu.wat.model.entities.Reservation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormValidationService {
    private static Logger logger = Logger.getLogger(FormValidationService.class);

    public enum ReservationError {ROOM_IS_EMPTY, DATE_TO_IS_EMPTY, DATE_FROM_IS_EMPTY, ROOM_IS_NOT_AVAILABLE, INCORRECT_DATES}

    private RoomService roomService = RoomService.getInstance();

    private ExecutorService executorService;

    public FormValidationService() {
        executorService = Executors.newSingleThreadExecutor(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    public ObservableList<ReservationError> validReservationForm(Reservation reservation) {
        ObservableList<ReservationError> observableList = FXCollections.observableList(new LinkedList<>());

        executorService.execute(() -> {
            List<ReservationError> errors = new ArrayList<>();
            if (reservation.getRoom() == null || reservation.getRoom().getNumber() == null)
                errors.add(ReservationError.ROOM_IS_EMPTY);

            if (reservation.getDateFrom() == null)
                errors.add(ReservationError.DATE_FROM_IS_EMPTY);

            if (reservation.getDateTo() == null)
                errors.add(ReservationError.DATE_TO_IS_EMPTY);

            if (errors.isEmpty()) {
                try {
                    List<Reservation> reservations = roomService
                            .getById(reservation.getRoom().getId()).get()
                            .getReservations();
                    for (Reservation res : reservations) {
                        if (reservation.getDateFrom().isBefore(res.getDateTo()) && reservation.getDateTo().isAfter(res.getDateFrom())) {
                            errors.add(ReservationError.ROOM_IS_NOT_AVAILABLE);
                            break;
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Błąd podczas walidacji formularza", e);
                }
            }

            if (reservation.getDateTo() != null & reservation.getDateFrom() != null) {
                if (reservation.getDateTo().compareTo(reservation.getDateFrom()) <= 0) {
                    errors.add(ReservationError.INCORRECT_DATES);
                }

            }

            observableList.addAll(errors);
        });

        return observableList;
    }
}
