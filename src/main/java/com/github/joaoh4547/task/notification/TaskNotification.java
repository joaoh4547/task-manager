package com.github.joaoh4547.task.notification;

import com.github.joaoh4547.task.Task;
import com.github.joaoh4547.task.TaskEvent;

public interface TaskNotification extends Notification {

    Task<?> getTask();

    void onStart(TaskEvent e);

    void onStep(TaskEvent e);

    void onFinish(TaskEvent e);

    void onException(TaskEvent e);

    void addContent(String content);

}
