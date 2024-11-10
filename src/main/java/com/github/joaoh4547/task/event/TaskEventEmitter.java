package com.github.joaoh4547.task.event;

/**
 * TaskEventEmitter class is responsible for emitting task events to subscribers.
 * It follows the singleton pattern with a private instance variable accessible through getInstance() method.
 * The fireEvent() method allows firing a TaskEvent to all registered listeners using TaskEventSubscriber.
 */
public class TaskEventEmitter {

    /**
     * Represents a singleton instance of TaskEventEmitter responsible for emitting task events to subscribers.
     */
    private static final TaskEventEmitter instance = new TaskEventEmitter();

    /**
     * Retrieve the singleton instance of TaskEventEmitter.
     *
     * @return The singleton instance of TaskEventEmitter.
     */
    public static TaskEventEmitter getInstance() {
        return instance;
    }

    /**
     * Fires the given TaskEvent to all registered listeners.
     *
     * @param event The TaskEvent to be fired.
     */
    public void fireEvent(TaskEvent event) {
        TaskEventSubscriber subscriber = TaskEventSubscriber.getInstance();
        subscriber.getListeners(event).forEach(x -> x.onTaskEvent(event));
    }

}
