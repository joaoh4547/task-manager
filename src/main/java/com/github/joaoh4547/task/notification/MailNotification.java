package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.Task;
import com.github.joaoh4547.task.TaskEvent;
import com.github.joaoh4547.task.notification.mail.MailSender;

import java.util.Collection;
import java.util.List;

public class MailNotification extends AbstractTaskNotification {

    private static final MailSender sender = new MailSender();



    public MailNotification(Task<?> task) {
        super(task);
    }

    @Override
    public void onStart(TaskEvent e) {
        message.append("Task started: ").append(e.task().getTaskId()).append("\n");
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
