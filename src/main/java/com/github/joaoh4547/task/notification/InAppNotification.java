package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.Task;
import com.github.joaoh4547.task.TaskEvent;

import java.util.Collection;
import java.util.List;

public class InAppNotification extends AbstractTaskNotification {

    public InAppNotification(Task<?> task) {
        super(task);
    }

    @Override
    public void onStart(TaskEvent e) {

    }

    @Override
    public void onStep(TaskEvent e) {

    }

    @Override
    public void onFinish(TaskEvent e) {

    }

    @Override
    public void onException(TaskEvent e) {

    }

    @Override
    public void addContent(String content) {

    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String getDestination() {
        return "";
    }

    @Override
    public Collection<NotificationAttachment> getAttachments() {
        return List.of();
    }
}
