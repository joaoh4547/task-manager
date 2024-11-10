package com.github.joaoh4547.task.event;

public class TaskEventEmitter {

    private static final TaskEventEmitter instance = new TaskEventEmitter();

    public static TaskEventEmitter getInstance() {
        return instance;
    }

    public void fireEvent(TaskEvent event) {
        TaskEventSubscriber subscriber = TaskEventSubscriber.getInstance();
        subscriber.getListeners(event).forEach(x -> x.onTaskEvent(event));
    }

}
