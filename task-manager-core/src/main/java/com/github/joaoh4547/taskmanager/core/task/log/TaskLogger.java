package com.github.joaoh4547.taskmanager.core.task.log;

import com.github.joaoh4547.taskmanager.core.entities.LogType;
import com.github.joaoh4547.taskmanager.core.entities.ProcessLog;
import com.github.joaoh4547.taskmanager.core.task.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class TaskLogger<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogger.class);

    private final Collection<ProcessLog> logs = Collections.synchronizedCollection(new ArrayList<>());

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
        ProcessLog log = new ProcessLog(task, logType, message);
        logs.add(log);

    }

    public void log(LogType logType, String message, boolean sendNotify) {
        ProcessLog log = new ProcessLog(task, logType, message);
        logs.add(log);

    }
}
