package com.github.joaoh4547.data.migration;

/**
 * Custom RuntimeException used to represent exceptions that occur during database migrations.
 */
public class MigrationException extends RuntimeException {

    /**
     * Represents a custom RuntimeException used to signal exceptions that occur during database migrations.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param e the cause (which is saved for later retrieval by the getCause() method)
     */
    MigrationException(String message, Throwable e) {
        super(message, e);
    }
}
