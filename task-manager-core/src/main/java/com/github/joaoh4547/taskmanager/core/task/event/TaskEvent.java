package com.github.joaoh4547.taskmanager.core.task.event;


import com.github.joaoh4547.taskmanager.core.task.Task;

/**
 * Represents an event related to a task, such as its type and the task itself.
 */
public record TaskEvent(TaskEventType type, Task<?> task) {


    public static TaskEvent createTaskEvent(TaskEventType type, Task<?> task) {
        return new TaskEvent(type, task);
    }
}
