package com.github.joaoh4547.task;

import com.github.joaoh4547.task.notification.TaskNotificator;

import java.util.ArrayList;
import java.util.List;

public class Task<T> {

    private final Integer taskId;

    private final List<Action> beforeActions = new ArrayList<>();
    private final List<Action> afterActions = new ArrayList<>();
    private final ActionErrorHandler errorHandler = TaskError::new;

    private TaskContext context;

    private final TaskAction<T> action;

    public Task(TaskAction<T> action) {
        this.taskId = TaskKeyGenerator.getTaskId();
        this.action = action;
    }

    public void addBeforeAction(Action action) {
        beforeActions.add(action);
    }

    public void addAfterAction(Action action) {
        afterActions.add(action);
    }


    public TaskResult<T> execute() {
        TaskResult<T> result = null;
        try {
            for (Action beforeAction : beforeActions) {
//                getTaskNotificator().
                beforeAction.execute();
            }
            result = action.execute();
            for (Action afterAction : afterActions) {
                afterAction.execute();
            }
        } catch (Exception e) {
            TaskError error = errorHandler.handleError(e);
            result = TaskResult.of(null, error);
            System.out.println(e.getMessage());
        }
        return result;

    }

    public TaskContext getContext() {
        return context;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    public Integer getTaskId() {
        return taskId;
    }

    private TaskNotificator getTaskNotificator() {
        return TaskNotificator.getInstance();
    }

    @Override
    public String toString() {
        return "Task{" + context + "/ Task ID " + taskId + "}";
    }
}
