package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.TaskEvent;
import com.github.joaoh4547.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskNotificator {

    private static final Logger LOG = LoggerFactory.getLogger(TaskNotificator.class);

    private static final List<TaskNotification> NOTIFICATIONS = new ArrayList<>();

    private static final TaskNotificator INSTANCE = new TaskNotificator();

    public static TaskNotificator getInstance() {
        return INSTANCE;
    }

    private TaskNotificator() {
    }


    public void addNotification(TaskNotification notification) {
        NOTIFICATIONS.add(notification);
    }

    public void notifyTaskFinished(Task<?> task) {
        Optional<TaskNotification> notification = getTaskNotification(task);
        notification.ifPresent(taskNotification -> taskNotification.onFinish(createEvent(task, TaskEvent.TakStep.COMPLETED)));
    }

    public void notifyTaskFailed(Task<?> task, Throwable error) {
        Optional<TaskNotification> notification = getTaskNotification(task);
        notification.ifPresent(taskNotification -> taskNotification.onException(createEvent(task, TaskEvent.TakStep.FAILED)));
        LOG.error("Task failed: {}", task, error);
    }

    public void notifyTaskStep(Task<?> task, TaskEvent.TakStep step) {
        Optional<TaskNotification> notification = getTaskNotification(task);
        notification.ifPresent(taskNotification -> taskNotification.onStep(createEvent(task, step)));
    }

    private void notifyTaskStarted(Task<?> task) {
        Optional<TaskNotification> notification = getTaskNotification(task);
        notification.ifPresent(taskNotification -> taskNotification.onStart(createEvent(task, TaskEvent.TakStep.STARTED)));
    }


    private TaskEvent createEvent(Task<?> task, TaskEvent.TakStep step) {
        return TaskEvent.create(task, step);
    }

    private Optional<TaskNotification> getTaskNotification(Task<?> task) {
        return NOTIFICATIONS.stream()
                .filter(n -> n.getTask().equals(task))
                .findFirst();

    }


}
