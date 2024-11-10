package com.github.joaoh4547.data.entities;

import com.github.joaoh4547.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a process instance that contains a task, logs, and an id.
 *
 * @param <T> the type of task associated with the process
 */
public class Process<T> {

    /**
     * Represents the unique identifier of an entity.
     */
    private UUID id;

    /**
     * Represents a task associated with a process, which can be executed to produce a result or an error.
     */
    private Task<T> task;

    /**
     * Represents a synchronized collection of ProcessLog instances associated with a Process.
     */
    private final Collection<ProcessLog> logs = Collections.synchronizedCollection(new ArrayList<>());

    public Process() {
    }

    /**
     * Adds a ProcessLog entry to the collection of logs associated with this Process.
     *
     * @param log the ProcessLog to add to the logs collection
     */
    public void addLog(ProcessLog log) {
        logs.add(log);
    }

    /**
     * Retrieves an unmodifiable collection of ProcessLog instances associated with this Process instance.
     * This method allows read-only access to the {@link #logs} collection.
     *
     * @return an unmodifiable collection of ProcessLog instances
     */
    public Collection<ProcessLog> getLogs() {
        return Collections.unmodifiableCollection(logs);
    }

    /**
     * Retrieves the unique identifier of this process instance.
     *
     * @return the UUID identifier of this process instance
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this process instance.
     *
     * @param id the UUID identifier to set for this process instance
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the task associated with a process instance.
     *
     * @return the task associated with the process instance
     */
    public Task<?> getTask() {
        return task;
    }

    /**
     * Sets the task associated with this process instance.
     *
     * @param task the task to set for this process instance
     */
    public void setTask(Task<T> task) {
        this.task = task;
    }

    /**
     * Creates a new instance of Process.
     *
     * @param <T> the type of task associated with the process
     * @return a new Process instance
     */
    public static <T> Process<T> newInstance() {
        return new Process<>();
    }
}
