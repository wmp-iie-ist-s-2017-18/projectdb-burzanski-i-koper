/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centrumkszalcenia.database;

import com.mycompany.centrumkszalcenia.Utils.Alerts;
import javafx.application.Platform;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.postgresql.util.PSQLException;


/**
 *
 * @author Boker
 */
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();

                MetadataSources sources = new MetadataSources(registry);

                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (RuntimeException e) {
                PSQLException psqle = getCauseOfClass(e, PSQLException.class);
                Alerts.DatabaseConnectionAlert(psqle.getMessage());
                Platform.exit();
                System.exit(1);
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    static <E extends Throwable> E getCauseOfClass(RuntimeException e, Class<E> exceptionClass) {
        Throwable t = e;
        do {
            if (exceptionClass.isAssignableFrom(t.getClass())) {
                return (E) t;
            }
        } while ((t = t.getCause()) != null);
        throw e;
    }

    public static StandardServiceRegistry getRegistry() {
        return registry;
    }
}