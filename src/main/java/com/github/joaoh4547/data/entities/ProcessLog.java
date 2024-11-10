package com.github.joaoh4547.data.entities;

import com.github.joaoh4547.task.Task;

public record ProcessLog(Task<?> task, LogType type, String message) {

    public String toString() {
        return "TaskLog{" +
                "task=" + task +
                ", type=" + type +
                ", message='" + message + '\'' +
                '}';
    }

}
