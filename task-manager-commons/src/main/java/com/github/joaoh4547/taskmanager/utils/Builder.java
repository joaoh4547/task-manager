package com.github.joaoh4547.taskmanager.utils;

/**
 * An interface for classes that build objects of type T.
 * @param <T> the type of object being built
 */
public interface Builder<T> {

    /**
     * Builds an object of type T.
     *
     * @return the object of type T that is built
     */
    T build();
}
