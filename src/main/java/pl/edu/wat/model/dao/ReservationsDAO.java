package pl.edu.wat.model.dao;

import org.apache.log4j.Logger;
import pl.edu.wat.exceptions.EmptyDatabaseTableException;
import pl.edu.wat.model.entities.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReservationsDAO implements IGenericDAO<Reservation> {
    Logger logger = Logger.getLogger(ReservationsDAO.class);

    @Override
    public List<Reservation> getList() {
        EntityManager em = emf.getEntityManager();

        List<Reservation> list = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();

        em.close();

        try {
            if(list.size() == 0) throw new EmptyDatabaseTableException("Reservations");
        } catch (EmptyDatabaseTableException e) {
            logger.warn(e);
        }
        return list;
    }

    @Override
    public Reservation getById(Long id) {
        EntityManager em = emf.getEntityManager();

        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r WHERE r.id = :arg1", Reservation.class);
        query.setParameter("arg1", id);

        Reservation reservation = query.getSingleResult();

        em.close();
        return reservation;
    }

}
