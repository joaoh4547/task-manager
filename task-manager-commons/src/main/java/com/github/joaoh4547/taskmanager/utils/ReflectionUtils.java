package com.github.joaoh4547.taskmanager.utils;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);


    private static final Reflections reflections =
            new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));


    @SafeVarargs
    public static Collection<Class<?>> getAllClassWithAnnotation(Class<? extends Annotation>... annotations) {
        Collection<Class<?>> clazz = new ArrayList<>();
        for (Class<? extends Annotation> annotation :
                annotations) {
            clazz.addAll(getClassWithAnnotation(annotation));
        }
        return clazz;
    }


    public static Collection<Class<?>> getClassWithAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }


    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        }
        catch (Exception e) {
            LOG.error("Error creating instance of {}", clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstanceWithArgs(Class<T> clazz, Object... args) {
        try {
            return clazz.getDeclaredConstructor(args.getClass()).newInstance(args);
        }
        catch (Exception e) {
            LOG.error("Error creating instance of {} with args {}", clazz.getName(), args, e);
            throw new RuntimeException(e);
        }
    }

    public static <T, R> boolean isSubtypeOf(Class<T> clazz, Class<R> clazz2) {
        return clazz2.isAssignableFrom(clazz);
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            LOG.error("No method {} found in {}", methodName, clazz.getName(), e);
            return null;
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        }
        catch (Exception e) {
            LOG.error("Error invoking method {} on object {}", method.getName(), obj, e);
            throw new RuntimeException(e);
        }
    }

    private static <T> Collection<Class<? extends T>> getSubclasses(Class<T> targetClass) {
        return reflections.getSubTypesOf(targetClass);
    }

    public static <T> Collection<Class<? extends T>> getSubclasses(Class<T> targetClass, boolean includeAbstract) {
        Collection<Class<? extends T>> classes = getSubclasses(targetClass);
        if (!includeAbstract) {
            classes.removeIf(c -> Modifier.isAbstract(c.getModifiers()));
        }
        return classes;
    }

    public static void test() {
//        Resources.getResource("")
    }


}
