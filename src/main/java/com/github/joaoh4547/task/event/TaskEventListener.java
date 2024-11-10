package com.github.joaoh4547.task.event;

/**
 * A functional interface for handling task events.
 */
@FunctionalInterface
public interface TaskEventListener {


    /**
     * Handles a task event.
     *
     * @param event The TaskEvent representing the event related to a task.
     */
    void onTaskEvent(TaskEvent event);
}
