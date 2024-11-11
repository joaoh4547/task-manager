package com.github.joaoh4547.taskmanager.core.task;

import com.github.joaoh4547.taskmanager.core.task.event.TaskEventEmitter;

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


    public static TaskContext createContext(String processName, Integer workerCount) {
        TaskContext context = TaskContext.create(processName, workerCount);
        contexts.add(context);
        return context;
    }


    public static TaskContext createContext(String processName, Integer workerCount, TaskEventEmitter eventEmitter) {
        TaskContext context = TaskContext.create(processName, workerCount, eventEmitter);
        contexts.add(context);
        return context;
    }

    public static TaskContext createContext(String processName) {
        TaskContext context = TaskContext.create(processName);
        contexts.add(context);
        return context;
    }

    public static TaskContext createContext(String processName, TaskEventEmitter eventEmitter) {
        TaskContext context = TaskContext.create(processName, eventEmitter);
        contexts.add(context);
        return context;
    }


    public static Set<TaskContext> getContexts() {
        return contexts;
    }

}
