package com.github.joaoh4547.data.entities;

import com.github.joaoh4547.task.Task;

/**
 * Represents a log entry for a process task.
 *
 * @param task the task associated with the log entry
 * @param type the type of log (INFO, WARN, ERROR)
 * @param message the log message
 */
public record ProcessLog(Task<?> task, LogType type, String message) {

    public String toString() {
        return "TaskLog{" +
                "task=" + task +
                ", type=" + type +
                ", message='" + message + '\'' +
                '}';
    }

}
