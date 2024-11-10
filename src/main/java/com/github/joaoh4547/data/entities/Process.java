package com.github.joaoh4547.data.entities;

import com.github.joaoh4547.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class Process<T> {

    private UUID id;
    private Task<T> task;
    private final Collection<ProcessLog> logs = Collections.synchronizedCollection(new ArrayList<>());

    public Process() {
    }

    public void addLog(ProcessLog log) {
        logs.add(log);
    }

    public Collection<ProcessLog> getLogs() {
        return Collections.unmodifiableCollection(logs);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Task<?> getTask() {
        return task;
    }

    public void setTask(Task<T> task) {
        this.task = task;
    }

    public static <T> Process<T> newInstance() {
        return new Process<>();
    }
}
