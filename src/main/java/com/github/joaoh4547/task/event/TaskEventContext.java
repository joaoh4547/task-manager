package com.github.joaoh4547.task.event;

import com.github.joaoh4547.task.Task;

public class TaskEventContext {



    private final TaskEventEmitter eventEmitter;
    private final TaskEventSubscriber eventSubscriber;

    private final Task<?> task;

    public TaskEventContext(Task<?> task, TaskEventEmitter eventEmitter) {
        this.task = task;
        this.eventEmitter = eventEmitter;
        this.eventSubscriber = TaskEventSubscriber.getInstance();
    }

    public void fireEvent(TaskEvent event) {
        eventEmitter.fireEvent(event);
    }

    public void subscribe(TaskEventType type, TaskEventListener listener) {
        eventSubscriber.subscribe(type, task, listener);
    }



}
