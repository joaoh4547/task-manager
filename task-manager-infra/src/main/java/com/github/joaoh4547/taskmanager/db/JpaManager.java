package com.github.joaoh4547.taskmanager.db;

import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.OracleDialect;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class JpaManager {

    private static JpaManager instance;

    private final EntityManagerFactory entityManagerFactory;

    private JpaManager() {
        this.entityManagerFactory = create();
    }

    private EntityManagerFactory create() {
        Configuration hibernateConfiguration = new Configuration();
        Properties properties = new Properties();
        properties.put(AvailableSettings.JAKARTA_NON_JTA_DATASOURCE, DatabaseManager.getDataSource());
        properties.put(AvailableSettings.DIALECT, OracleDialect.class.getName());
        properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
        properties.put(AvailableSettings.SHOW_SQL, "true");
        properties.put(AvailableSettings.FORMAT_SQL, "true");
        properties.put(AvailableSettings.HIGHLIGHT_SQL, "true");
        hibernateConfiguration.setProperties(properties);

        for (Class<?> clazz :
                ReflectionUtils.getAllClassWithAnnotation(Entity.class, Converter.class)) {
            hibernateConfiguration.addAnnotatedClass(clazz);
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateConfiguration.getProperties()).build();

        return hibernateConfiguration.buildSessionFactory(serviceRegistry);

    }

    public static JpaManager getInstance() {
        if (instance == null) {
            instance = new JpaManager();
        }
        return instance;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }


}
