package com.github.joaoh4547.taskmanager.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {

  InjectionMode mode() default InjectionMode.SINGLETON;

}
