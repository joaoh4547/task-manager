package com.github.joaoh4547.taskmanager.injection;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.joaoh4547.taskmanager.utils.ReflectionUtils;

public class CDIManager {

  private static CDIManager instance;
  private Map<Class<?>, Object> OBJECT_STORE = Collections
      .synchronizedMap(new HashMap<>());

  public static CDIManager getInstance() {
    if (instance == null) {
      instance = new CDIManager();
    }

    return instance;
  }

  public synchronized <T> T get(Class<T> clazz, Field field) {
    Injectable injectable = field.getAnnotation(Injectable.class);
    if (injectable.mode() == InjectionMode.NEW_INSTANCE) {
      return ReflectionUtils.newInstance(clazz);
    } else if (injectable.mode() == InjectionMode.SINGLETON) {
      if (OBJECT_STORE.containsKey(clazz)) {
        return clazz.cast(OBJECT_STORE.get(clazz));
      } else {
        T instance = ReflectionUtils.newInstance(clazz);
        OBJECT_STORE.put(clazz, instance);
        return instance;
      }
    }
    return null;
  }

}
