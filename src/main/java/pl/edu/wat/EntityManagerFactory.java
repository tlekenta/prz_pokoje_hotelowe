package pl.edu.wat;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
    private static javax.persistence.EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("mysql_ds");

    public static EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }

    public static void close(){
        emFactory.close();
    }
}
