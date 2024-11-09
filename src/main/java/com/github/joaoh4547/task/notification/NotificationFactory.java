package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationFactory {


    private static final Logger LOG = LoggerFactory.getLogger(NotificationFactory.class);

    public  static Notification createNotification(NotificationMode mode, Task<?> task){
        return switch (mode) {
            case IN_APP -> new InAppNotification(task);
            case MAIL -> new MailNotification(task);
            default -> throw new IllegalArgumentException("Unsupported notification mode: " + mode);
        };
    }

    public enum NotificationMode{
        IN_APP,
        MAIL
    }

}
