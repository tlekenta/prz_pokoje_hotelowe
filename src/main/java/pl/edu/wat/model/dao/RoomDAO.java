package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;
import pl.edu.wat.model.entities.Room;

import javax.persistence.EntityManager;
import java.util.List;

public class RoomDAO {

    public Room save(Room room) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();

        em.persist(room);

        em.getTransaction().commit();
        em.close();
        return room;
    }

    public List<Room> getRoomsList() {
        EntityManager em = EntityManagerFactory.getEntityManager();
//        em.getTransaction().begin();

        List<Room> list = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();

//        em.getTransaction().commit();
        em.close();
        return list;
    }
}
