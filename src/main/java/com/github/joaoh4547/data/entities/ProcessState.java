package com.github.joaoh4547.data.entities;

/**
 * Represents the current state of a process.
 */
public enum ProcessState {

    /**
     * Represents the initial state of a process, indicating that the process is queued and waiting to be executed.
     */
    QUEUED,

    /**
     * Represents the state of a process where the process is currently being executed.
     */
    RUNNING,

    /**
     * Represents an error state in a process, indicating that an unexpected issue occurred.
     */
    ERROR,

    /**
     * Represents the state indicating that a process has successfully finished its execution.
     */
    FINISHED
}
