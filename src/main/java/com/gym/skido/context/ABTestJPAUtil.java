package com.gym.skido.context;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ABTestJPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "ABTESTSSKIDO";

    private static EntityManagerFactory factory;


    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
