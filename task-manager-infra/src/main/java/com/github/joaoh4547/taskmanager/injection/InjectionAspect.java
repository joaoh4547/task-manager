package com.github.joaoh4547.taskmanager.injection;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class InjectionAspect {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(InjectionAspect.class);

  @After("execution(*.new(..)) && within(@Injectable *)")
  public void setUp(JoinPoint point) {
    Object alvo = point.getTarget();
    for (Field campo : alvo.getClass().getDeclaredFields()) {
      if (campo.isAnnotationPresent(Injectable.class)) {
        campo.setAccessible(true);
        try {
          campo.set(alvo,
                    CDIManager.getInstance().get(campo.getType(), campo));
        } catch (Exception e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    }
  }
}
