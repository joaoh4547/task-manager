package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.Task;

public abstract class AbstractTaskNotification implements TaskNotification {

    private final Task<?> task;

    protected final StringBuilder message = new StringBuilder();

    public AbstractTaskNotification(Task<?> task) {
        this.task = task;
    }

    @Override
    public Task<?> getTask() {
        return task;
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
