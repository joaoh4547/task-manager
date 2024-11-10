package com.github.joaoh4547.task.event;

@FunctionalInterface
public interface TaskEventListener {
    void onTaskEvent(TaskEvent event);
}
