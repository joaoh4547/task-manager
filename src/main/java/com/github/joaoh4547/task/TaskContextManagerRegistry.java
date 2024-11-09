package com.github.joaoh4547.task;

public class TaskContextManagerRegistry {

    private static final ThreadLocal<TaskContextManager> contextManagerThreadLocal = new ThreadLocal<>();

    public static <T extends  TaskContextManager>  void setContextManager(T contextManager) {
        contextManagerThreadLocal.set(contextManager);
    }

    public static ThreadLocal<TaskContextManager> getContextManagerThreadLocal() {
        return contextManagerThreadLocal;
    }
}
