package pl.edu.wat;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
    private static javax.persistence.EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("mysql_ds");

    public EntityManager getEntityManager(){
        if(!emFactory.isOpen()) {
            emFactory = Persistence.createEntityManagerFactory("mysql_ds");
        }
        return emFactory.createEntityManager();
    }

    public static void close(){
        if(emFactory.isOpen())
            emFactory.close();
    }
}
