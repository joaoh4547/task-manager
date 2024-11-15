package com.github.joaoh4547.taskmanager.data;

import com.github.joaoh4547.taskmanager.db.JpaManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

/**
 * An abstract class that serves as a base class for JDBC repositories.
 * It implements the Repository interface to provide common repository functionalities.
 *
 * @param <T> The type of entity stored in the repository.
 * @param <R> The type of key used to identify entities in the repository.
 */
public abstract class AbstractRepository<T, R> implements Repository<T, R> {


    private final Class<T> entityClass;

    private EntityManagerFactory getEntityManagerFactory() {
        return JpaManager.getInstance().getEntityManagerFactory();
    }

    private EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    protected AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private Class<T> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public T find(R key) {
        EntityManager em = getEntityManager();
        return em.find(entityClass, key);
    }

    @Override
    public void save(T entity) {
        try (EntityManager em = getEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(entity);
            t.commit();
        }
    }

    @Override
    public void deleteByKey(R key) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, key);
        if (entity != null) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.remove(entity);
            t.commit();
        }
    }

    @Override
    public void delete(T entity) {
        EntityManager em = getEntityManager();
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.remove(entity);
        t.commit();
    }

    @Override
    public boolean exists(R key) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, key);
        return entity != null;
    }


}
