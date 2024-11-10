package com.github.joaoh4547.task.event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a store for task event listeners based on their event types.
 */
public class TaskEventListenerStore {

    /**
     * Represents a singleton instance of TaskEventListenerStore for managing task event listeners.
     * Each task event listener can be added to the store based on its event type.
     * The store uses a ConcurrentHashMap to store listeners by their event type.
     */
    private static final TaskEventListenerStore INSTANCE = new TaskEventListenerStore();

    /**
     * Represents a mapping of task event listeners based on their corresponding event types.
     * The listeners map stores a collection of listeners for each specific event type.
     */
    private final Map<TaskEventType, Collection<TaskEventListener>> listeners = new ConcurrentHashMap<>();


    /**
     * Retrieves the singleton instance of TaskEventListenerStore.
     *
     * @return The singleton instance of TaskEventListenerStore for managing task event listeners.
     */
    public static TaskEventListenerStore getInstance() {
        return INSTANCE;
    }

    /**
     * Adds a task event listener for the specified task event type.
     *
     * @param eventType The type of task event to listen for.
     * @param listener The TaskEventListener to be added for the specified event type.
     */
    public void addTaskEventListener(TaskEventType eventType, TaskEventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }


}
