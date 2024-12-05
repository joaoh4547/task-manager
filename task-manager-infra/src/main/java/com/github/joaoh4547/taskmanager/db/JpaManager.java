package com.github.joaoh4547.taskmanager.db;

import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;

import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class JpaManager {

  private static JpaManager instance;

  private EntityManagerFactory entityManagerFactory;

  private JpaManager() {
  }

  public static JpaManager getInstance() {
    if (instance == null) {
      instance = new JpaManager();
    }
    return instance;
  }

  public void init() {
    this.entityManagerFactory = create();
  }

  private EntityManagerFactory create() {
    Configuration hibernateConfiguration = new Configuration();
    Properties properties = new Properties();
    properties.put(AvailableSettings.JAKARTA_NON_JTA_DATASOURCE,
                   DatabaseManager.getDataSource());
    properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
    properties.put(AvailableSettings.SHOW_SQL, "true");
    properties.put(AvailableSettings.FORMAT_SQL, "true");
    properties.put(AvailableSettings.HIGHLIGHT_SQL, "true");
    properties.put(AvailableSettings.USE_SQL_COMMENTS, "true");
    properties.put(AvailableSettings.JAKARTA_TRANSACTION_TYPE, "RESOURCE_LOCAL");
    hibernateConfiguration.setProperties(properties);

    for (Class<?> clazz : ReflectionUtils
        .getAllClassWithAnnotation(Entity.class, Converter.class)) {
      hibernateConfiguration.addAnnotatedClass(clazz);
    }

    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(hibernateConfiguration.getProperties()).build();

    return hibernateConfiguration.buildSessionFactory(serviceRegistry);

  }

  public EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      init();
    }
    return entityManagerFactory;
  }

  public EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

}
