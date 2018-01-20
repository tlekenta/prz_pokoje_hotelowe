package pl.edu.wat.model.dao;

import pl.edu.wat.EntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public interface IGenericDAO<T> {

    default T save(T toPersist) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        em.getTransaction().begin();

        em.persist(toPersist);

        em.getTransaction().commit();
        em.close();
        return toPersist;
    }

    T getById(Long id);

    List<T> getList();

}
