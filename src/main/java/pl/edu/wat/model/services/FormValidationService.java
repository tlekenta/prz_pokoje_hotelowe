package pl.edu.wat.model.services;

import pl.edu.wat.model.entities.Reservation;

import java.util.ArrayList;
import java.util.List;

public class FormValidationService {
    public enum ReservationError {ROOM_IS_EMPTY, DATE_TO_IS_EMPTY, DATE_FROM_IS_EMPTY, ROOM_IS_NOT_AVAILABLE, INCORRECT_DATES}

    public List<ReservationError> validReservationForm(Reservation reservation) {
        List<ReservationError> errors = new ArrayList<>();

        if(reservation.getRoom().getNumber() == null)
            errors.add(ReservationError.ROOM_IS_EMPTY);

        if(reservation.getDateFrom() == null)
            errors.add(ReservationError.DATE_FROM_IS_EMPTY);

        if(reservation.getDateTo() == null)
            errors.add(ReservationError.DATE_TO_IS_EMPTY);

        if(reservation.getDateTo() != null & reservation.getDateFrom() != null) {
            if(reservation.getDateTo().compareTo(reservation.getDateFrom()) <= 0) {
                errors.add(ReservationError.INCORRECT_DATES);
            }

        }

        return errors;
    }
}
