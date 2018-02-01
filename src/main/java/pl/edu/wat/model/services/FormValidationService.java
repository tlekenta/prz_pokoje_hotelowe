package pl.edu.wat.model.services;

import pl.edu.wat.model.entities.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FormValidationService {
    public enum ReservationError {ROOM_IS_EMPTY, DATE_TO_IS_EMPTY, DATE_FROM_IS_EMPTY, ROOM_IS_NOT_AVAILABLE, INCORRECT_DATES}

    private RoomService roomService = RoomService.getInstance();

    public List<ReservationError> validReservationForm(Reservation reservation) {
        List<ReservationError> errors = new ArrayList<>();

        if(reservation.getRoom() == null || reservation.getRoom().getNumber() == null)
            errors.add(ReservationError.ROOM_IS_EMPTY);

        if(reservation.getDateFrom() == null)
            errors.add(ReservationError.DATE_FROM_IS_EMPTY);

        if(reservation.getDateTo() == null)
            errors.add(ReservationError.DATE_TO_IS_EMPTY);

        if(errors.isEmpty()){
            try {
                List<Reservation> reservations = roomService
                        .getById(reservation.getRoom().getId())
                        .get()
                        .getReservations();
                for(Reservation res: reservations) {
                    if(reservation.getDateFrom().isBefore(res.getDateTo()) && reservation.getDateTo().isAfter(res.getDateFrom())) {
                        errors.add(ReservationError.ROOM_IS_NOT_AVAILABLE);
                        break;
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if(reservation.getDateTo() != null & reservation.getDateFrom() != null) {
            if(reservation.getDateTo().compareTo(reservation.getDateFrom()) <= 0) {
                errors.add(ReservationError.INCORRECT_DATES);
            }

        }

        return new ArrayList<>(errors);
    }
}
