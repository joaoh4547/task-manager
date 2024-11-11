package com.github.joaoh4547.taskmanager.core.data;

/**
 * An interface representing a generic repository for entities.
 *
 * @param <T> The type of entity stored in the repository.
 * @param <K> The type of key used to identify entities in the repository.
 */
public interface Repository<T, K> {

    /**
     * Retrieves the entity corresponding to the provided key from the repository.
     *
     * @param key The key used to identify the entity.
     * @return The entity that matches the provided key, or null if no entity is found.
     */
    T find(K key);

    /**
     * Saves the entity to the repository.
     *
     * @param entity The entity to be saved.
     */
    void save(T entity);

    /**
     * Deletes the entity in the repository identified by the provided key.
     *
     * @param key The key used to identify the entity to be deleted.
     */
    void deleteByKey(K key);

    /**
     * Deletes the specified entity from the repository.
     *
     * @param entity The entity to be deleted.
     */
    void delete(T entity);

    /**
     * Generates a new key to be used for identifying entities in the repository.
     *
     * @return A new key of type K.
     */
    K newKey();

    /**
     * Checks if an entity with the provided key exists in the repository.
     *
     * @param key The key of the entity to check for existence.
     * @return True if an entity with the key exists, false otherwise.
     */
    boolean exists(K key);


}
