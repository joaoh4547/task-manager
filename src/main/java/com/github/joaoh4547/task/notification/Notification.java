package com.github.joaoh4547.task.notification;

import java.util.Collection;

public interface Notification {

    String getDescription();

    String getMessage();

    String getDestination();

    Collection<NotificationAttachment> getAttachments();
}
