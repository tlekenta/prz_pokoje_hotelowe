package pl.edu.wat.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    private String number;

    private Integer numberOfPersons;

    private Integer numberOfBeds;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;
}
