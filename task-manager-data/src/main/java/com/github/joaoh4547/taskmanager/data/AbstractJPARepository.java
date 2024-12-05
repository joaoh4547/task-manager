package com.github.joaoh4547.taskmanager.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.ehcache.Cache;
import org.ehcache.Cache.Entry;

import com.github.joaoh4547.taskmanager.cache.EhcacheManager;
import com.github.joaoh4547.taskmanager.db.JpaManager;
import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;

import jakarta.persistence.*;

/**
 * AbstractJPARepository is an abstract class that provides basic CRUD
 * operations for entities using the Java Persistence API (JPA). This class
 * implements the Repository interface and provides a set of synchronized
 * methods to interact with the database.
 *
 * @param <T> The type of entity.
 * @param <R> The type of key used to identify the entity.
 */
public abstract class AbstractJPARepository<T, R>
  implements Repository<T, R> {

  /**
   * A cache for storing entities and their corresponding keys, intended to
   * improve performance by reducing the number of database accesses. This cache
   * is expected to be initialized along with the entity manager factory in the
   * {@code init} method.
   */
  private Cache<R, T> cache;

  /**
   * Represents the factory for creating and managing {@link EntityManager}
   * instances.
   * <p>
   * This factory is responsible for setting up the persistence context and
   * providing entity managers that interact with the underlying database. It is
   * initialized in the {@code init()} method by calling
   * {@link #configureEntityManagerFactory()}.
   * </p>
   */
  private EntityManagerFactory entityManagerFactory;

  /**
   * Initializes the AbstractJPARepository. This constructor calls the
   * {@code init} method to configure the EntityManagerFactory and create the
   * cache required for managing entities and their lifecycle.
   */
  protected AbstractJPARepository() {
    init();
  }

  /**
   * Initializes the necessary components for the repository. This method
   * configures the EntityManagerFactory and creates a cache to manage entities
   * and their lifecycle within the repository.
   */
  void init() {
    entityManagerFactory = configureEntityManagerFactory();
    cache = createCache();
  }

  /**
   * Creates and returns a cache for entities managed by this repository. The
   * cache is created using the EhcacheManager instance, with the cache name
   * derived from the entity class' name, and the key and value types specified
   * by the repository's key and entity classes.
   *
   * @return A Cache instance for storing entities managed by this repository.
   */
  private Cache<R, T> createCache() {
    return EhcacheManager.getInstance()
        .createCache(getEntityClass().getName(), getKeyClass(),
                     getEntityClass());
  }

  /**
   * Configures and retrieves the EntityManagerFactory instance used to interact
   * with the persistence context.
   *
   * @return The configured EntityManagerFactory instance.
   */
  private EntityManagerFactory configureEntityManagerFactory() {
    return JpaManager.getInstance().getEntityManagerFactory();
  }

  /**
   * Retrieves a synchronized instance of EntityManager.
   *
   * @return An instance of EntityManager created by the EntityManagerFactory.
   */
  private synchronized EntityManager getEntityManager() {
    return getEntityManagerFactory().createEntityManager();
  }

  /**
   * Retrieves the configured EntityManagerFactory instance.
   *
   * @return The configured EntityManagerFactory instance.
   */
  private EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  /**
   * Retrieves the class type of the entity managed by this repository.
   *
   * @return The class type of the entity.
   */
  private Class<T> getEntityClass() {
    return ReflectionUtils.getGenericParamAt(getClass(), 0);
  }

  /**
   * Retrieves the class type of the key used in the repository.
   *
   * @return The class type of the key.
   */
  private Class<R> getKeyClass() {
    return ReflectionUtils.getGenericParamAt(getClass(), 1);
  }

  @Override
  public synchronized T find(R key) {
    if (cache.containsKey(key)) {
      return cache.get(key);
    }
    EntityManager em = getEntityManager();
    return em.find(getEntityClass(), key);
  }

  @Override
  public synchronized T save(T entity) {
    EntityTransaction t = null;
    try (EntityManager em = getEntityManager()) {
      t = em.getTransaction();
      t.begin();
      T merged = em.merge(entity);
      t.commit();
      cache.put(extractKey(entity), merged);
      return merged;
    } catch (Exception e) {
      if (t != null && t.isActive()) {
        t.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  /**
   * Extracts the key from the given entity using reflection to find the field
   * annotated with {@link jakarta.persistence.Id}.
   *
   * @param entity The entity from which to extract the key.
   * @return The extracted key if the field exists and is of the correct type,
   *         or null otherwise.
   * @throws RuntimeException If an exception occurs while accessing the field.
   */
  private R extractKey(T entity) {
    Field f = ReflectionUtils.getFieldWithAnnotation(getEntityClass(),
                                                     Id.class);
    if (f != null) {
      f.setAccessible(true);
      try {
        Object key = f.get(entity);
        if (ReflectionUtils.isSubtypeOf(key.getClass(), getKeyClass())) {
          return getKeyClass().cast(key);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  @Override
  public synchronized void deleteByKey(R key) {
    EntityManager em = getEntityManager();
    T entity = em.find(getEntityClass(), key);
    if (entity != null) {
      EntityTransaction t = em.getTransaction();
      t.begin();
      em.remove(entity);
      t.commit();
      cache.remove(key);
    }
  }

  @Override
  public synchronized void delete(T entity) {
    EntityManager em = getEntityManager();
    if (!em.contains(entity)) {
      entity = em.merge(entity);
    }
    EntityTransaction t = em.getTransaction();
    t.begin();
    em.remove(entity);
    t.commit();
    cache.remove(extractKey(entity));
  }

  @Override
  public synchronized boolean exists(R key) {
    if (cache.containsKey(key)) {
      return true;
    }
    EntityManager em = getEntityManager();
    T entity = em.find(getEntityClass(), key);
    return entity != null;
  }

  @Override
  public synchronized Collection<T> loadAll() {
    Collection<T> results = new ArrayList<>();
    if (!cache.iterator().hasNext()) {
      TypedQuery<T> typedQuery = getEntityManager()
          .createQuery("select p from Process p", getEntityClass());
      results = typedQuery.getResultList();
    } else {
      for (Entry<R, T> entry : cache) {
        results.add(entry.getValue());
      }
    }
    return results;
  }
}
