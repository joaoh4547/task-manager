package com.github.joaoh4547.taskmanager.core.config;

import com.github.joaoh4547.taskmanager.cache.EhcacheManager;
import com.github.joaoh4547.taskmanager.config.ApplicationContext;

public class CacheConfigurator {

  public void configure(DistributedCacheConfig config) {
    StringBuilder sb = new StringBuilder();
    sb.append("terracotta://");
    sb.append(config.host());
    if (config.port() != null) {
      sb.append(":");
      sb.append(config.port());
    }
    sb.append("/");
    sb.append(config.name());
    ApplicationContext.setTerracotaUrl(sb.toString());
  }

  public void init() {
    EhcacheManager.getInstance().init();
  }

}
