package pl.edu.wat.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private Double totalPrice;

    @ManyToOne
    private Room room;

    public Reservation() {
        room = new Room();
    }
}
