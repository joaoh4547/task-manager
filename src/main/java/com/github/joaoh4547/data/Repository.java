package com.github.joaoh4547.data;

public interface Repository<T, K> {

    T find(K key);

    void save(T entity);

    void deleteByKey(K key);

    void delete(T entity);

    K newKey();

    boolean exists(K key);


}
