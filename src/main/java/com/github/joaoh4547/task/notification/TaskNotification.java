package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.data.entities.Process;

public interface TaskNotification<T> {

    void notify(Process<T> task);

}
