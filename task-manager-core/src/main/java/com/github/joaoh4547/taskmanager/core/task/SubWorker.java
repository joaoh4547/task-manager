package com.github.joaoh4547.taskmanager.core.task;

import java.util.ArrayList;
import java.util.Collection;

public class SubWorker<T> {

    private final TaskRegistry<T> registry;

    public SubWorker(TaskRegistry<T> registry) {
        this.registry = registry;
    }

    public void doWork() {
        Collection<TaskResult<T>> results = new ArrayList<>();

        for (Task<T> task : registry.getTasks()) {
            System.out.println("Before executing task  " + task.getTaskId());
            results.add(task.execute());
            System.out.println("After executing task " + task.getTaskId());
        }
        registry.getListeners().forEach(listener -> listener.execute(results));
    }

}
