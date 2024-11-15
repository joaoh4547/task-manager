package com.github.joaoh4547.taskmanager.core.task.notification;


import com.github.joaoh4547.taskmanager.core.process.Process;

public interface TaskNotification<T> {

    void notify(Process task);

}
