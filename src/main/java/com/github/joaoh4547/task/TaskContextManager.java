package com.github.joaoh4547.task;

import java.util.HashSet;
import java.util.Set;

public class TaskContextManager {

    private static final Set<TaskContext> contexts = new HashSet<>();

    public static final TaskContext GENERAL = TaskContext.create("GENERAL", 5);

    static {
        addContext(GENERAL);
    }


    public static void addContext(TaskContext context) {
        contexts.add(context);
    }


    public static void addContext(String processName, Integer workerCount) {
        TaskContext context = TaskContext.create(processName, workerCount);
        contexts.add(context);
    }

    public static void addContext(String processName) {
        TaskContext context = TaskContext.create(processName);
        contexts.add(context);
    }

    public static Set<TaskContext> getContexts() {
        return contexts;
    }

}
