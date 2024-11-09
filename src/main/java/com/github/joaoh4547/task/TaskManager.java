package com.github.joaoh4547.task;

import com.github.joaoh4547.Bundler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class TaskManager {
    private static final Map<TaskContext, Collection<TaskWorker>> taskByContext = new ConcurrentHashMap<>();

    static {
        initWorkers();
    }

    private TaskManager() {
    }


    private static void initWorkers() {
        for (TaskContext context : TaskContextManager.getContexts()) {
            createWorker(context);
        }
    }

    private static void createWorker(TaskContext context) {
        long workersCount = context.getWorkerCount();
        if (workersCount == -1) {
            workersCount = Bundler.getInt("task-default.worker.number");
        }
        for (int i = 0; i < workersCount; i++) {
            TaskWorker worker = TaskWorker.create(getWorkerCountByContext(context), context);
            taskByContext.computeIfAbsent(context, k -> new ArrayList<>()).add(worker);
        }
    }

    private static int getWorkerCountByContext(TaskContext context) {
        return taskByContext.getOrDefault(context, Collections.emptyList()).size() + 1;
    }


    private static TaskWorker getWorker(TaskContext context) {
        Collection<TaskWorker> taskWorkers = taskByContext.get(context);
        if (taskWorkers == null || taskWorkers.isEmpty()) {
            throw new IllegalStateException(getNoWorkersFoundMessage(context));
        }
        Optional<TaskWorker> workerOptional = taskWorkers.stream().min(Comparator.comparingInt(TaskWorker::getTaskCount));
        return workerOptional.orElseThrow(() -> new IllegalStateException(getNoWorkersFoundMessage(context)));
    }

    public static <T> void addTask(TaskContext context, Task<T> task) {
        TaskWorker worker = getWorker(context);
        task.setContext(context);
        worker.addTask(task);
    }

    public static <T> void addTask(Task<T> task) {
        addTask(TaskContextManager.GENERAL, task);
    }


    public static <T> void addTask(TaskContext context, Task<T> task, TaskExecutionListener<T> listener) {
        TaskWorker worker = getWorker(context);
        task.setContext(context);
        worker.addTask(listener, task);
    }

    public static <T> void addTask(Task<T> task, TaskExecutionListener<T> listener) {
        addTask(TaskContextManager.GENERAL, task, listener);
    }

    public static <T> void addTask(TaskContext context, Collection<Task<T>> tasks) {
        TaskWorker worker = getWorker(context);
        configContextForTasks(context, tasks.toArray(new Task[0]));
        worker.addTask(tasks);
    }

    public static <T> void addTask(Collection<Task<T>> tasks) {
        addTask(TaskContextManager.GENERAL, tasks);
    }

    @SafeVarargs
    public static <T> void addTask(TaskContext context, Task<T>... tasks) {
        TaskWorker worker = getWorker(context);
        configContextForTasks(context, tasks);
        worker.addTask(tasks);
    }

    @SafeVarargs
    private static <T> void configContextForTasks(TaskContext context, Task<T>... tasks) {
        Stream.of(tasks).forEach(task -> task.setContext(context));
    }

    @SafeVarargs
    public static <T> void addTask(Task<T>... tasks) {
        addTask(TaskContextManager.GENERAL, tasks);
    }

    @SafeVarargs
    public static <T> void addTask(TaskExecutionListener<T> listener, Task<T>... tasks) {
        TaskWorker worker = getWorker(TaskContextManager.GENERAL);
        configContextForTasks(TaskContextManager.GENERAL, tasks);
        worker.addTask(listener, tasks);
    }


    private static String getNoWorkersFoundMessage(TaskContext context) {
        return "No workers available for context: " + context;
    }

}
