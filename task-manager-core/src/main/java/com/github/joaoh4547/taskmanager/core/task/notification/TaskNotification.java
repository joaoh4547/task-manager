package com.github.joaoh4547.taskmanager.core.task.notification;


import com.github.joaoh4547.taskmanager.core.entities.Process;

public interface TaskNotification<T> {

    void notify(Process<T> task);

}
