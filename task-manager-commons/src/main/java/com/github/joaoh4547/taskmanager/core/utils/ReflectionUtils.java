package com.github.joaoh4547.taskmanager.core.utils;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);
    private static Collection<Class<?>> ALL_CLASS;

    static {
        init();
    }

    private static void init() {
        initAllClass();
    }

    private static void initAllClass() {
        Collection<Class<?>> allClass = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Collection<ClassPath.ClassInfo> infos = ClassPath.from(classLoader)
                    .getTopLevelClasses();
            for (ClassPath.ClassInfo classInfo : infos) {
                try {
                    Class<?> clazz = classInfo.load();
                    allClass.add(clazz);
                } catch (Throwable ignore) {
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        ALL_CLASS = allClass;
    }


    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            LOG.error("Error creating instance of {}", clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstanceWithArgs(Class<T> clazz, Object... args) {
        try {
            return clazz.getDeclaredConstructor(args.getClass()).newInstance(args);
        } catch (Exception e) {
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
        } catch (NoSuchMethodException e) {
            LOG.error("No method {} found in {}", methodName, clazz.getName(), e);
            return null;
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            LOG.error("Error invoking method {} on object {}", method.getName(), obj, e);
            throw new RuntimeException(e);
        }
    }

    private static <T> Collection<Class<T>> getSubclasses(Class<T> targetClass) {
        Collection<Class<T>> subclasses = new ArrayList<>();
        for (Class<?> clazz : ALL_CLASS) {
            if (isSubtypeOf(clazz, targetClass)) {
                subclasses.add((Class<T>) clazz);
            }
        }
        return subclasses;
    }

    public static <T> Collection<Class<T>> getSubclasses(Class<T> targetClass, boolean includeAbstract) {
        Collection<Class<T>> classes = getSubclasses(targetClass);
        if (!includeAbstract) {
            classes.removeIf(c -> Modifier.isAbstract(c.getModifiers()));
        }
        return classes;
    }


}
