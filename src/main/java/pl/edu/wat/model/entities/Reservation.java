package pl.edu.wat.model.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @ManyToOne
    private Room room;

    public Reservation() {
        room = new Room();
    }
}
