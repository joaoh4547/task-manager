package com.github.joaoh4547.taskmanager.core;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.joaoh4547.taskmanager.core.config.CacheConfigurator;
import com.github.joaoh4547.taskmanager.core.config.DistributedCacheConfig;
import com.github.joaoh4547.taskmanager.db.DataBaseContext;
import com.github.joaoh4547.taskmanager.db.JpaManager;
import com.github.joaoh4547.taskmanager.migration.MigratorManager;
import com.github.joaoh4547.taskmanager.utils.Bundler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * A class that defines the initialization process for the application. It
 * implements the Initializer interface, providing a method to initialize the
 * application components.
 */
public class AbstractInitializer
  implements Initializer {

  private static final Logger LOG = LoggerFactory
      .getLogger(AbstractInitializer.class);

  /**
   * Represents the bundle key used for obtaining database configuration values.
   */
  private static final String DATABASE_BUNDLE = "database";

  @Override
  public void onInitialize() {
    initCache();
    initDatabase();
    initJPA();
    if (isRunMigrations()) {
      new MigratorManager().runMigrations();
    }
  }

  /**
   * Initializes the cache by creating a cache configurator, applying the
   * configuration, and then initializing the cache manager.
   * <p>
   * This method performs the following steps: - Creates an instance of
   * `CacheConfigurator` using `createCacheConfigurator()`. - Configures the
   * cache with `createCacheConfig()` using the `configure()` method of
   * `CacheConfigurator`. - Initializes the cache manager using the `init()`
   * method of `CacheConfigurator`.
   * </p>
   */
  void initCache() {
    var config = createCacheConfigurator();
    config.configure(createCacheConfig());
    config.init();
  }

  /**
   * Creates and returns an instance of CacheConfigurator.
   *
   * @return a new instance of CacheConfigurator to configure and initialize the
   *         caching mechanism.
   */
  private CacheConfigurator createCacheConfigurator() {
    return new CacheConfigurator();
  }

  /**
   * Creates and returns a DistributedCacheConfig with predefined settings.
   *
   * @return a DistributedCacheConfig object initialized with default host
   *         "localhost" on port 9510, and the cache name retrieved from
   *         getCacheName().
   */
  protected DistributedCacheConfig createCacheConfig() {
    return new DistributedCacheConfig("localhost", 9510, getCacheName());
  }

  /**
   * Retrieves the name of the cache to be used.
   *
   * @return the name of the cache, which is "task-manager"
   */
  protected String getCacheName() {
    return "task-manager";
  }

  private void initJPA() {
    LOG.info("Initializing JPA Manager");
    JpaManager.getInstance().init();
  }

  /**
   * Initializes the database connection using the configuration values from the
   * specified bundle. The JDBC URL, username, and password are retrieved from
   * the "database" bundle. Sets the initialized DataSource in the
   * DataBaseContext for future database operations.
   */
  protected void initDatabase() {
    try {

      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(Bundler.getValue("database.url", DATABASE_BUNDLE));
      config
          .setUsername(Bundler.getValue("database.user", DATABASE_BUNDLE));
      config.setPassword(Bundler.getValue("database.password",
                                          DATABASE_BUNDLE));
      config.setMaximumPoolSize(30);
      config.addDataSourceProperty("dataSource.logLevel", "INFO");
      config.setMinimumIdle(10);
      config.setConnectionTimeout(30000);
      config.setIdleTimeout(600000);
      config.setLeakDetectionThreshold(2000);
      config.setConnectionTestQuery("SELECT 1 from dual");

      HikariDataSource ds = new HikariDataSource(config);

      DataBaseContext.setDataSource(ds);
      try (Connection con = ds.getConnection()) {
        if (con.isValid(2)) { // Testa a conexão por 2 segundos
          LOG.info("Database connection established");
        } else {
          LOG.info("Database connection failed");
        }
      } catch (Exception e) {
        ds.close();
        LOG.error("Database connection failed", e);
      }

    } catch (Exception e) {
      LOG.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks if the migrations should be run during initialization.
   *
   * @return true if migrations should be run, false otherwise
   */
  protected boolean isRunMigrations() {
    return true;
  }
}
