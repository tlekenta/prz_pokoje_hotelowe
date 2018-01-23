package pl.edu.wat.model.dao;

import pl.edu.wat.exceptions.EmptyDatabaseTableException;
import pl.edu.wat.model.entities.Room;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoomDAO implements IGenericDAO<Room> {

    @Override
    public List<Room> getList() {
        EntityManager em = emf.getEntityManager();

        List<Room> list = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();

        em.close();

        try {
            if(list.size() == 0) throw new EmptyDatabaseTableException("Rooms");
        } catch (EmptyDatabaseTableException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Room getById(Long id) {
        EntityManager em = emf.getEntityManager();

        TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.id = :arg1", Room.class);
        query.setParameter("arg1", id);

        Room room = query.getSingleResult();

        em.close();
        return room;
    }
}
