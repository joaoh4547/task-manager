package com.github.joaoh4547.taskmanager.core.task.event;

import com.github.joaoh4547.taskmanager.core.task.Task;

/**
 * Represents the context for task events, such as emitting events and subscribing to event listeners.
 */
public class TaskEventContext {


    /**
     * Represents an event emitter responsible for firing task events and notifying event listeners.
     */
    private final TaskEventEmitter eventEmitter;

    /**
     * Represents a subscriber for task events. Responsible for managing event listeners associated with specific task events.
     */
    private final TaskEventSubscriber eventSubscriber;

    /**
     * Represents task The task associated with the context.
     */
    private final Task<?> task;

    /**
     * Constructor for TaskEventContext.
     *
     * @param task         The task associated with the context.
     * @param eventEmitter The event emitter responsible for firing task events.
     */
    public TaskEventContext(Task<?> task, TaskEventEmitter eventEmitter) {
        this.task = task;
        this.eventEmitter = eventEmitter;
        this.eventSubscriber = TaskEventSubscriber.getInstance();
    }

    /**
     * Fires the given TaskEvent by delegating the event firing to the TaskEventEmitter.
     *
     * @param event The TaskEvent to be fired.
     */
    public void fireEvent(TaskEvent event) {
        eventEmitter.fireEvent(event);
    }

    /**
     * Subscribes the provided TaskEventListener to the specified TaskEventType.
     *
     * @param type The type of task event to subscribe to.
     * @param listener The listener to be subscribed.
     */
    public void subscribe(TaskEventType type, TaskEventListener listener) {
        eventSubscriber.subscribe(type, task, listener);
    }


}
