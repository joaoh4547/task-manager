package com.github.joaoh4547.taskmanager.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;

/**
 * Manages repositories and provides access to them. This class caches instances
 * of repositories and creates new ones if they are not already cached.
 */
public class RepositoryManager {

  /**
   * A synchronized set that stores instances of repositories. This set serves
   * as a cache to manage and provide access to various repository instances.
   * Repositories are cached to avoid redundant instantiations and to manage
   * repository lifecycle within the application.
   */
  private final Set<Repository<?, ?>> CACHE_STORE = Collections
      .synchronizedSet(new HashSet<>());

  /**
   * Registers a repository into the cache store.
   *
   * @param repository The repository instance to be cached.
   */
  private void register(Repository<?, ?> repository) {
    CACHE_STORE.add(repository);
  }

  /**
   * Retrieves a repository of the specified type from the cache. If the
   * repository is not present in the cache, a new instance is created,
   * registered, and then returned.
   *
   * @param <T>        The type of entity the repository manages.
   * @param <R>        The type of key used to identify entities in the
   *                   repository.
   * @param repository The class type of the repository to be retrieved.
   * @return An instance of the repository.
   */
  public <T, R> Repository<T, R> getRepo(Class<Repository<T, R>> repository) {
    Optional<Repository<T, R>> optRepo = CACHE_STORE.stream()
        .filter(r -> r.getClass().equals(repository)).map(repository::cast)
        .findFirst();
    if (optRepo.isPresent()) {
      return optRepo.get();
    } else {
      Repository<T, R> r = ReflectionUtils.newInstance(repository);
      register(r);
      return r;
    }
  }

}
