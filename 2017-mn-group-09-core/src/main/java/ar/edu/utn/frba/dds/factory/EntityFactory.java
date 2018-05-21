package ar.edu.utn.frba.dds.factory;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.configuration.EnviromentType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by pablomorrone on 27/8/17.
 */
public class EntityFactory {

    private static EntityManagerFactory emFactory = null;

    private static EntityManagerFactory getFactory() {
        if (emFactory == null) {
            EnviromentType type = EnviromentConfigurationHelper.getEnv();
            switch (EnviromentConfigurationHelper.getEnv()){
                case TEST:
                     emFactory = Persistence.createEntityManagerFactory("dondeInvertirTest");
                     break;
                case APP:
                     emFactory = Persistence.createEntityManagerFactory("dondeInvertir");
                     break;
                default:
                    emFactory = Persistence.createEntityManagerFactory("dondeInvertir");
                    break;
            }
        }
        return emFactory;
    }

    public static EntityManager getEntityManager(){
        return getFactory().createEntityManager();
    }
}
