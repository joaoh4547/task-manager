package com.github.joaoh4547.taskmanager.core.task.log;

import com.github.joaoh4547.taskmanager.core.process.LogType;
import com.github.joaoh4547.taskmanager.core.process.ProcessLog;
import com.github.joaoh4547.taskmanager.core.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TaskLogger<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogger.class);

    private final Task<T> task;

    public static <T> TaskLogger<T> getInstance(Task<T> task) {
        return new TaskLogger<>(task);
    }

    private TaskLogger(Task<T> task) {
        this.task = task;
    }

    public Task<T> getTask() {
        return task;
    }

    public void log(LogType logType, String message) {
        ProcessLog log = new ProcessLog(message, logType, task.getProcess());
        task.getProcess().addLog(log);
    }

    public void log(LogType logType, String message, boolean sendNotify) {
        log(logType, message);
    }
}
