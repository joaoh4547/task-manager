package com.github.joaoh4547.task.event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskEventListenerStore {

    private static final TaskEventListenerStore INSTANCE = new TaskEventListenerStore();

    private final Map<TaskEventType, Collection<TaskEventListener>> listeners = new ConcurrentHashMap<>();


    public static TaskEventListenerStore getInstance() {
        return INSTANCE;
    }

    public void addTaskEventListener(TaskEventType eventType, TaskEventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }


}
