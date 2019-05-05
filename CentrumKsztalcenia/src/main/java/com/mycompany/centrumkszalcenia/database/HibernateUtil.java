/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centrumkszalcenia.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Boker
 */
public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
 
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();
 
            return metadata.getSessionFactoryBuilder().build();
        }
        catch(Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}