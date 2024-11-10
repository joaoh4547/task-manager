package com.github.joaoh4547.task;

import com.github.joaoh4547.task.event.TaskEvent;
import com.github.joaoh4547.task.event.TaskEventEmitter;
import com.github.joaoh4547.task.event.TaskEventType;

import java.util.ArrayList;
import java.util.Collection;

public class TaskRegistry<T> {

    private final Collection<Task<T>> tasks = new ArrayList<>();

    private final Collection<TaskExecutionListener<T>> listeners = new ArrayList<>();


    public void addTask(Task<T> task) {
        tasks.add(task);
        TaskEventEmitter.getInstance().fireEvent(TaskEvent.createTaskEvent(TaskEventType.QUEUED, task));
    }


    public void addEndListener(TaskExecutionListener<T> listener) {
        this.listeners.add(listener);
    }

    public static <T> TaskRegistry<T> create() {
        return new TaskRegistry<>();
    }


    public Collection<Task<T>> getTasks() {
        return tasks;
    }

    public Collection<TaskExecutionListener<T>> getListeners() {
        return listeners;
    }
}
