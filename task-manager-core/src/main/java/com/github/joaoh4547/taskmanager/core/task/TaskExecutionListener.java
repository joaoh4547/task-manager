package com.github.joaoh4547.taskmanager.core.task;

import java.util.Collection;

@FunctionalInterface
public interface TaskExecutionListener<T> {

    void execute(Collection<TaskResult<T>> results);

}
