package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public interface IGenericDAO<T> {
    EntityManagerFactory emf = new EntityManagerFactory();

    default T save(T toPersist) {
        EntityManager em = emf.getEntityManager();
        em.getTransaction().begin();

        em.persist(toPersist);

        em.getTransaction().commit();
        em.close();
        return toPersist;
    }

    default void delete(T toRemove) {
        EntityManager em = emf.getEntityManager();
        em.getTransaction().begin();

        em.remove(em.contains(toRemove) ? toRemove : em.merge(toRemove));

        em.getTransaction().commit();
        em.close();
    }

    T getById(Long id);

    List<T> getList();

}
