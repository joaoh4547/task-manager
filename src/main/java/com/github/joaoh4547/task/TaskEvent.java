package com.github.joaoh4547.task;

import com.github.joaoh4547.task.notification.NotificationListener;

public record TaskEvent(Task<?> task, com.github.joaoh4547.task.TaskEvent.TakStep step, NotificationListener listener) {

    public static TaskEvent create(Task<?> task, TakStep step, NotificationListener listener) {
        return new TaskEvent(task, step, listener);
    }

    public static TaskEvent create(Task<?> task, TakStep step) {
        return create(task, step, null);
    }

    public enum TakStep {
        STARTED,
        AFTER_RUNNING,
        BEFORE_RUNNING,
        RUNNING,
        SLEEPING,
        COMPLETED,
        FAILED
    }


}
