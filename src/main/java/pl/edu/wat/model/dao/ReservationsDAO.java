package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;
import pl.edu.wat.exceptions.EmptyDatabaseTableException;
import pl.edu.wat.model.entities.Reservation;

import javax.persistence.EntityManager;
import java.util.List;

public class ReservationsDAO implements GenericDAO<Reservation> {

    @Override
    public List<Reservation> getList() {
        EntityManager em = EntityManagerFactory.getEntityManager();

        List<Reservation> list = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();

        em.close();

        try {
            if(list.size() == 0) throw new EmptyDatabaseTableException("Reservations");
        } catch (EmptyDatabaseTableException e) {
            e.printStackTrace();
        }
        return list;
    }

}
