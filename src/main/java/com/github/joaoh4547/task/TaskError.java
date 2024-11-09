package com.github.joaoh4547.task;

public class TaskError {

    private final Throwable cause;

    private final String message;

    public TaskError(Throwable cause, String message) {
        this.cause = cause;
        this.message = message;
    }

    public TaskError(String message) {
        this(null, message);
    }

    public TaskError(Throwable cause) {
        this(cause, null);
    }


    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }
}
