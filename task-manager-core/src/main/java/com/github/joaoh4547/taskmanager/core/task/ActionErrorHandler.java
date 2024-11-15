package com.github.joaoh4547.taskmanager.core.task;

@FunctionalInterface
public interface ActionErrorHandler {
    TaskError handleError(Throwable e);
}
