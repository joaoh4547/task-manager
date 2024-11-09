package com.github.joaoh4547.task;

@FunctionalInterface
public interface TaskAction<T> {

    TaskResult<T> execute();

}
