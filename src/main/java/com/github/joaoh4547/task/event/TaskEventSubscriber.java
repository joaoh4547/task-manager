package com.github.joaoh4547.task.event;

import com.github.joaoh4547.task.Task;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskEventSubscriber {

    private final Map<Pair<TaskEventType, Task<?>>, Collection<TaskEventListener>> listeners = new ConcurrentHashMap<>();

    private static final TaskEventSubscriber instance = new TaskEventSubscriber();

    private TaskEventSubscriber() {
    }

    public static TaskEventSubscriber getInstance() {
        return instance;
    }



    public void subscribe(TaskEventType eventType, Task<?> task, TaskEventListener listener) {
        Pair<TaskEventType, Task<?>> pair = createPair(eventType, task);
        listeners.computeIfAbsent(pair, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    private static Pair<TaskEventType, Task<?>> createPair(TaskEventType eventType, Task<?> task) {
        return new ImmutablePair<>(eventType, task);
    }

    public Collection<TaskEventListener> getListeners(TaskEvent event) {
        Pair<TaskEventType, Task<?>> pair = createPair(event.type(), event.task());
        return listeners.getOrDefault(pair, Collections.emptyList());
    }


    public Collection<TaskEventListener> getListeners(TaskEventType eventType) {
        return listeners.entrySet().stream()
                .filter(entry -> entry.getKey().getLeft() == eventType)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .toList();
    }


    public Collection<TaskEventListener> getListeners(TaskEventType eventType, Task<?> task) {
        return listeners.getOrDefault(createPair(eventType, task), Collections.emptyList());
    }

}
