package com.github.joaoh4547.taskmanager.core.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record DistributedCacheConfig( String host,Integer port,
                                     String name) {

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("port", port)
        .append("host", host).append("name", name).toString();
  }
}
