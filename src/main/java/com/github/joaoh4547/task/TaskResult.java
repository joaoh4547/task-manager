package com.github.joaoh4547.task;

public class TaskResult<T> {

    private final T result;

    private final TaskError error;


    TaskResult(T result, TaskError error) {
        this.result = result;
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public TaskError getError() {
        return error;
    }

    public static <T> TaskResult<T> of(T result, TaskError error) {
        return new TaskResult<>(result, error);
    }

    public static <T> TaskResult<T> of(T result) {
        return of(result, null);
    }
}
