package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;
import pl.edu.wat.exceptions.EmptyDatabaseTableException;
import pl.edu.wat.model.entities.Room;

import javax.persistence.EntityManager;
import java.util.List;

public class RoomDAO implements GenericDAO<Room> {

    @Override
    public List<Room> getList() {
        EntityManager em = EntityManagerFactory.getEntityManager();

        List<Room> list = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();

        em.close();

        try {
            if(list.size() == 0) throw new EmptyDatabaseTableException("Rooms");
        } catch (EmptyDatabaseTableException e) {
            e.printStackTrace();
        }
        return list;
    }
}