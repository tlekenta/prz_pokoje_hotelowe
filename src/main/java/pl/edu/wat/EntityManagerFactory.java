package pl.edu.wat;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
    private static String data_source = "h2_ds";
    private static javax.persistence.EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(data_source);

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

    public static void enableDevmode(){
        data_source = "mysql_ds";
        close();
        emFactory = Persistence.createEntityManagerFactory(data_source);
    }
}
