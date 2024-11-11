package com.github.joaoh4547.taskmanager.core.task;

@FunctionalInterface
public interface TaskAction<T> {

    TaskResult<T> execute();

}
