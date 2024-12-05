package com.github.joaoh4547.taskmanager.cache;

import java.net.URI;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.clustered.client.config.ClusteringServiceConfiguration;
import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import com.github.joaoh4547.taskmanager.config.ApplicationContext;

/**
 * The EhcacheManager class is responsible for managing the lifecycle and
 * configuration of an Ehcache CacheManager. It provides methods for creating
 * and initializing the CacheManager, as well as creating individual caches with
 * specific configurations.
 */
public class EhcacheManager {

  /**
   * Singleton instance of the EhcacheManager class. This instance is lazily
   * initialized and provides access to the methods for managing the lifecycle
   * and configuration of the Ehcache CacheManager.
   */
  private static EhcacheManager instance;

  /**
   * Manages the Ehcache CacheManager instance. Responsible for creating,
   * initializing, and providing access to the CacheManager. This instance is
   * used to configure and build caches with specific settings.
   */
  private CacheManager cacheManager;

  /**
   * Provides a singleton instance of the EhcacheManager class. This method
   * ensures that only one instance of EhcacheManager is created (lazy
   * initialization) and returns this instance for use by other components to
   * manage caches.
   *
   * @return The singleton instance of the EhcacheManager class.
   */
  public static EhcacheManager getInstance() {
    if (instance == null) {
      instance = new EhcacheManager();
    }
    return instance;
  }

  /**
   * Creates and initializes a new CacheManager instance.
   *
   * @return the newly created and initialized CacheManager instance
   */
  public CacheManager create() {
    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(getConfig()).build(true);
    return cacheManager;
  }

  /**
   * Initializes the Ehcache CacheManager by creating a new instance using the
   * configured settings. This method sets the CacheManager instance to be used
   * by the EhcacheManager for managing caches and their configurations.
   */
  public void init() {
    cacheManager = create();
  }

  /**
   * Configures and returns a ClusteringServiceConfiguration instance for
   * setting up clustered caching. Utilizes the
   * ClusteringServiceConfigurationBuilder to create the configuration with a
   * cluster URI and automatic creation on reconnect.
   *
   * @return a ClusteringServiceConfiguration instance configured for clustered
   *         caching
   */
  private ClusteringServiceConfiguration getConfig() {
    return ClusteringServiceConfigurationBuilder.cluster(createClusterURI())
        .autoCreateOnReconnect(c -> c).build();
  }

  /**
   * Creates a cluster URI using the Terracotta URL from the application
   * context.
   *
   * @return the created cluster URI
   */
  private URI createClusterURI() {
    return URI.create(getTerracottaUrl());
  }

  /**
   * Retrieves the Terracotta URL from the application context.
   *
   * @return The Terracotta URL used for connecting to the Terracotta server.
   */
  private String getTerracottaUrl() {
    return ApplicationContext.getTerracotaUrl();
  }

  /**
   * Creates a cache with the specified name, key type, and value type. If the
   * CacheManager is not yet initialized, it initializes it before creating the
   * cache.
   *
   * @param cacheName The name of the cache to be created.
   * @param keyType   The class type of the keys used in the cache.
   * @param valueType The class type of the values stored in the cache.
   * @param <T>       The type of the keys used in the cache.
   * @param <K>       The type of the values stored in the cache.
   * @return A Cache instance configured with the specified name, key type, and
   *         value type.
   */
  public <T, K> Cache<T, K> createCache(String cacheName, Class<T> keyType,
                                        Class<K> valueType) {
    CacheManager manager = cacheManager;
    if (manager == null) {
      init();
      manager = cacheManager;
    }

    return manager.createCache(cacheName,
                               createCacheConfig(keyType, valueType));
  }

  /**
   * Creates a cache configuration for the specified key and value types.
   *
   * @param <T>       The type of the keys used in the cache.
   * @param <K>       The type of the values stored in the cache.
   * @param keyType   The class type of the keys used in the cache.
   * @param valueType The class type of the values stored in the cache.
   * @return A CacheConfiguration instance configured with the specified key and
   *         value types.
   */
  private <T, K> CacheConfiguration<T, K> createCacheConfig(Class<T> keyType,
                                                            Class<K> valueType) {
    return CacheConfigurationBuilder
        .newCacheConfigurationBuilder(keyType, valueType,
                                      createPoolConfig())
        .build();
  }

  /**
   * Creates and configures a new ResourcePools instance for caching. This
   * configuration includes a heap size of 2 MB and a clustered resource pool.
   *
   * @return A configured ResourcePools instance.
   */
  private ResourcePools createPoolConfig() {
    return ResourcePoolsBuilder.newResourcePoolsBuilder()
        .heap(20, MemoryUnit.MB)
        .with(ClusteredResourcePoolBuilder.clustered()).build();
  }

}
