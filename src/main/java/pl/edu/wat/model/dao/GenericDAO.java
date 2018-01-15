package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public interface GenericDAO<T> {

    default T save(T toPersist) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();

        em.persist(toPersist);

        em.getTransaction().commit();
        em.close();
        return toPersist;
    }

    List<T> getList();

}
