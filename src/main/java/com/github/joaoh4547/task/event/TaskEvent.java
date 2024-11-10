package com.github.joaoh4547.task.event;

//import com.github.joaoh4547.task.notification.NotificationListener;

import com.github.joaoh4547.task.Task;

public record TaskEvent(TaskEventType type, Task<?> task) {


    public static TaskEvent createTaskEvent(TaskEventType type, Task<?> task) {
        return new TaskEvent(type, task);
    }
}
