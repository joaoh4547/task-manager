package com.github.joaoh4547.task;

@FunctionalInterface
public interface ActionErrorHandler {
    TaskError handleError(Throwable e);
}
