package com.github.joaoh4547.data;

/**
 * An abstract class that serves as a base class for JDBC repositories.
 * It implements the Repository interface to provide common repository functionalities.
 *
 * @param <T> The type of entity stored in the repository.
 * @param <R> The type of key used to identify entities in the repository.
 */
public abstract class AbstractJdbcRepository<T, R> implements Repository<T, R> {


}
